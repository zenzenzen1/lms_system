
package servlet;

import com.mysql.cj.util.StringUtils;
import constants.LmsConstants;
import dao.SettingDetailsDAO;
import dao.UsersDAO;
import dto.UserPayload;
import entity.SettingDetails;
import entity.Users;
import utils.BeanFactory;
import utils.JwtUtils;
import utils.PasswordUtils;
import utils.RedisUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.RedisUtil.generateKeyForRedis;

public class LoginServlet extends HttpServlet {

    private UsersDAO userDao;
    private SettingDetailsDAO settingDetailsDao;
    private RedisUtil redisUtil;

    @Override
    public void init() {
        userDao = BeanFactory.getUserDao();
        settingDetailsDao = BeanFactory.getSettingDetailsDao();
        redisUtil = BeanFactory.getRedisUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = request.getParameter("errorMessage");
        request.setAttribute("errorMessage", errorMessage);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                LmsConstants.VIEW_BASE_PATH + "user/login.jsp"
        );
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMeTxt = request.getParameter("rememberMe");
        boolean rememberMe = false;
        if(StringUtils.isNullOrEmpty(rememberMeTxt)){
            try{
                rememberMe = Boolean.parseBoolean(rememberMeTxt);

            }catch (Exception ignored){}
        }
        String token = this.login(userName, password, rememberMe, request, response);
        if(token != null){
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setMaxAge(10 * 60 * 60);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            // add cookie to response
            response.addCookie(jwtCookie);
            response.sendRedirect(request.getContextPath() + "/home");
        }else{
            response.sendRedirect(request.getContextPath() + "/home");
        }

    }

    private String login(String username, String password, boolean rememberMe, HttpServletRequest request,
                         HttpServletResponse response) {

        String hashPassword = PasswordUtils.createHashPassword(password);
        Users user = userDao.findByUsername(username);
        if (user != null && PasswordUtils.verifyPasswordHash(password, hashPassword)) {
            try {
                List<UserPayload.SimpleGrantedAuthority> permission = mapRolesToPermission(user, settingDetailsDao);
                UserPayload userPayload = new UserPayload(username, password, permission, rememberMe);
                String keySessionId = generateKeyForRedis(userPayload.getSessionId());

                userPayload.setAccountNonLocked(user.getIsActive());
                userPayload.setAccountNonExpired(user.getIsActive());
                userPayload.setCredentialsNonExpired(user.getIsActive());
                userPayload.setRememberMe(rememberMe);
                String token = JwtUtils.generateToken(userPayload);
                try{
                    redisUtil.setExpire(keySessionId, 30 * 60);
                }catch (Exception ignored) {}
                return token;
            } catch (SQLException e) {
                try {
                    redirectToLoginPage(request, response, "User doesn't have assigned role");
                } catch (IOException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else {
            try {
                redirectToLoginPage(request, response, "Username or password incorrect.");
            } catch (IOException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    static List<UserPayload.SimpleGrantedAuthority> mapRolesToPermission(Users user, SettingDetailsDAO settingDetailsDao) throws SQLException {
        SettingDetails role = settingDetailsDao.findById(user.getRoleId());
        List<UserPayload.SimpleGrantedAuthority> permission = new ArrayList<>();
        for(SettingDetails setting: settingDetailsDao.findAllPermissionOfRoleName(role.getSettingValue(), role.getType().toString())){
            permission.add(new UserPayload.SimpleGrantedAuthority(setting.getSettingValue()));
        }
        return permission;
    }

    private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws IOException {
        request.setAttribute("alert","Danger");
        request.setAttribute("message",errorMessage);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                LmsConstants.VIEW_BASE_PATH + "user/login.jsp"
        );
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
