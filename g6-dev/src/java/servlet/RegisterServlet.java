package servlet;

import constants.LmsConstants;
import static constants.LmsConstants.Regex.EMAIL_REGEX;
import static servlet.LoginServlet.mapRolesToPermission;

import dao.SettingDetailsDAO;
import dao.UsersDAO;
import dto.UserPayload;
import entity.SettingDetails;
import entity.Users;
import enums.UserTypeEnums;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.BeanFactory;
import utils.JwtUtils;
import utils.PasswordUtils;

/**
 *
 * @author TRUNGPC
 */
public class RegisterServlet extends HttpServlet {

    private UsersDAO userDao;
    private SettingDetailsDAO settingDetailsDao;

    @Override
    public void init() {
        userDao = BeanFactory.getUserDao();
        settingDetailsDao = BeanFactory.getSettingDetailsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserTypeEnums type = UserTypeEnums.valueOf(request.getParameter("type"));
        getRegister(type, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        System.out.printf("email: " + email);
        if (!email.matches(EMAIL_REGEX)) {
            errors.add("Email doesn't match format");
        }
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        if("".equals(fullname)){
            errors.add("Fullname is not null or empty");
        }
        String typeStr = request.getParameter("type");
        UserTypeEnums type = UserTypeEnums.valueOf(typeStr);
        switch (type) {
            case STUDENT:
                request.setAttribute("type", UserTypeEnums.STUDENT);

                break;
            case TEACHER:
                request.setAttribute("type", UserTypeEnums.TEACHER);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "home.jsp");
                dispatcher.forward(request, response);
        }

        if (userDao.isEmailOrUserExisted(username, email)) {
            errors.add("Email or username is already registered in system");
        }

        if(!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "user/register.jsp");
            dispatcher.forward(request, response);
        } else {
            String passwordHash = PasswordUtils.createHashPassword(password);
            Users user = new Users(username, fullname, passwordHash, email, phone);
            List<SettingDetails> settingDetailsList = settingDetailsDao.findAllPermissionBySettingName(typeStr, "ROLE");
            user.setRoleId(settingDetailsList.get(0).getId());
            user.setLmsRolesByRoleId(settingDetailsList.get(0));
            userDao.upsertUser(user);
            String token = this.login(username, password, response);
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setMaxAge(10 * 60 * 60);
            jwtCookie.setSecure(true);

            // add cookie to response
            response.addCookie(jwtCookie);
            response.sendRedirect(request.getContextPath() + "/home");
        }

    }
    
    private String login(String username, String password, HttpServletResponse response) {
        UserPayload userPayload = new UserPayload();
        userPayload.setUsername(username);
        String hashPassword = PasswordUtils.createHashPassword(password);
        Users user = userDao.findByUsername(username);
        if (PasswordUtils.verifyPasswordHash(password, hashPassword)) {
            try {
                List<UserPayload.SimpleGrantedAuthority> permission = mapRolesToPermission(user, settingDetailsDao);
                userPayload.setPassword(password);
                userPayload.setAuthorities(permission);
                userPayload.setAccountNonLocked(user.getIsActive());
                userPayload.setAccountNonExpired(user.getIsActive());
                userPayload.setCredentialsNonExpired(user.getIsActive());
                return JwtUtils.generateToken(userPayload);
            } catch (SQLException e) {
                try {
                    redirectToLoginPage(response, "User doesn't have assigned role");
                } catch (IOException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    private void redirectToLoginPage(HttpServletResponse response, String errorMessage) throws IOException {
        String encodedMessage = URLEncoder.encode(errorMessage, "UTF-8");
        String htmlResponse = "<script type=\"text/javascript\">"
                + "window.location.href = \"/login?error=" + encodedMessage + "\";"
                + "</script>";

        response.setContentType("text/html");
        response.getWriter().write(htmlResponse);
    }

    private void getRegister(UserTypeEnums type, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (type) {
            case STUDENT:
                request.setAttribute("type", UserTypeEnums.STUDENT);
                break;
            case TEACHER:
                request.setAttribute("type", UserTypeEnums.TEACHER);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "home.jsp");
                dispatcher.forward(request, response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "user/register.jsp");
        dispatcher.forward(request, response);
    }
}
