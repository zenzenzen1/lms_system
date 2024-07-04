package servlet;

import com.mysql.cj.util.StringUtils;
import constants.LmsConstants;
import dao.UsersDAO;
import dto.response.SessionResponse;
import entity.Users;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import enums.DataState;
import enums.SettingTypeEnums;
import utils.BeanFactory;
import utils.PasswordUtils;

/**
 *
 * @author macbook
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UsersDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = BeanFactory.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
//        if (action == null || action.equals("/all")) {
//            getAllUsers(request, response);
//        } else if (action.startsWith("/details")){
//            String id = request.getParameter("id");
//            getUser(id, request, response);
//        }
        int page = parsePageFromRequest(request);
        int pageSize = parsePageSizeFromRequest(request);
        switch (action) {
            case "/details":
                String id = request.getParameter("id");
                getUser(id, request, response);
                break;
            case "/search":
                Map<String, String> filters = new HashMap<>();
                filters.put("username", request.getParameter("inputUsername"));
                filters.put("name", request.getParameter("inputFullName"));
                filters.put("email", request.getParameter("inputEmail"));
                filters.put("phone", request.getParameter("inputPhone"));
                filters.put("role_id", request.getParameter("inputRole"));
                filters.put("is_active", request.getParameter("inputStatus"));
                getAllUserWithPagination(page, pageSize, filters, request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/insert":
                insertUser(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateUser(request, response);
                break;
            default:
                getAllUserWithPagination(page, pageSize, null, request, response);
                break;
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.valueOf(request.getParameter("id"));
        int result = userDAO.delete(userId);
        HttpSession session = request.getSession();
        if (result > 0){
            alert(session, "success", "Remove user "+userId+ " is success.");
        }else{
            alert(session, "danger", "Remove user "+userId+ " is failure.");
        }
        response.sendRedirect("users/details");
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) {
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            upsertUser(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        upsertUser(request, response);
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Users> users = userDAO.findAll();
        request.setAttribute("users", users);

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "user/userList.jsp");
        dispatcher.forward(request, response);
    }

    private void getAllUserWithPagination(int page, int pageSize, Map<String, String> filters,
                                          HttpServletRequest request,
                                          HttpServletResponse response)
            throws ServletException, IOException {

        int totalPages = calculateTotalPages(pageSize, filters);
        List<Users> users = userDAO.findAll(page, pageSize,filters);
        setPaginationAttributes(request, page, pageSize, totalPages);
        request.setAttribute("users", users);

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "user/userList.jsp");
        dispatcher.forward(request, response);
    }

    private void getUser(String id, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Users user = userDAO.findUserById(Long.parseLong(id));
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "user/userDetail.jsp");
        dispatcher.forward(request, response);
    }

    private void upsertUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");

        Users user = new Users();
        if (splits.length == 2) {
            Long id = Long.valueOf(splits[1]);
            user.setId(id);
        }
        Map<String, String> errors = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        String isActive = request.getParameter("isActive");

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        } else {
            errors.put("username", "Username can not be empty");
        }

        if (password != null && !password.isEmpty()) {
            String passwordHash
                    = PasswordUtils.createHashPassword(password);
            user.setPasswordHash(passwordHash);
        } else {
            errors.put("password", "Password can not be empty");
        }

        user.setName(name);

        try {
            user.setDateOfBirth(Date.valueOf(dateOfBirth));
        } catch (IllegalArgumentException e) {
            errors.put("dob", "The date of birth format is incorrect");
        }
        if (userDAO.isEmailOrUserExisted(username, email)) {
            errors.put("existed", "Username or Email already existed");
        }
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setDescription(description);

        if (isActive != null && !isActive.isEmpty()) {
            user.setIsActive(Boolean.parseBoolean(isActive));
        } else {
            errors.put("state", "Is Active field can not be empty");
        }
        if (errors.isEmpty()) {
            // No errors, proceed
            Long userId = userDAO.upsertUser(user);
            if (userId != -1L) {
                HttpSession session = request.getSession();
                session.setAttribute("message", "User added successfully");
                response.sendRedirect(request.getContextPath() + "/user/" + userId);
            }
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "user/userDetail.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void setPaginationAttributes(HttpServletRequest request, int page, int pageSize, int totalPages) {
        request.setAttribute("servletPath", request.getRequestURI());
        request.setAttribute("currentPage", page);
        request.setAttribute("size", pageSize);
        request.setAttribute("totalPages", totalPages);
    }

    private int parsePageFromRequest(HttpServletRequest request) {
        String pageStr = request.getParameter("page");
        try {
            return (pageStr != null)
                    ? Math.max(Integer.parseInt(pageStr), 1)
                    : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private int parsePageSizeFromRequest(HttpServletRequest request) {
        String pageSizeStr = request.getParameter("pageSize");
        try {
            return (pageSizeStr != null)
                    ? Integer.parseInt(pageSizeStr) < 1 ? 5 : Integer.parseInt(pageSizeStr)
                    : 5;
        } catch (NumberFormatException e) {
            return 5;
        }
    }

    private void alert(HttpSession sessions, String alert, String message) throws IOException {
        sessions.setAttribute("alert",alert);
        sessions.setAttribute("message",message);
    }

    private int calculateTotalPages(int pageSize, Map<String, String> queryParam) {
        int totalRecords = getTotalRecordCount(queryParam);
        return (int) Math.max(Math.ceil((double) totalRecords / pageSize), 1);
    }

    private int getTotalRecordCount(Map<String, String> queryParam) {
        return userDAO.count(queryParam);
    }
}
