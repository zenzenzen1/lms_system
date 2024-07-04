package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import constants.LmsConstants;
import dao.SessionsDAO;
import dao.SubjectsDAO;
import dto.response.SessionResponse;
import entity.Sessions;
import entity.Subjects;
import enums.DataState;
import enums.SettingTypeEnums;
import error.CommonErrorDispatch;
import utils.BeanFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SessionsServlet extends HttpServlet {

    private static SessionsDAO sessionsDAO;
    private static SubjectsDAO subjectsDAO;
    private static ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
        sessionsDAO = BeanFactory.getSessionsDao();
        subjectsDAO = BeanFactory.getSubjectDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            int page = parsePageFromRequest(request);
            int pageSize = parsePageSizeFromRequest(request);
            if (isPathInfoAll(pathInfo)) {
                DataState state = getDataState(request);
                getAllSessions(request, response, state);
            } else {
                String[] splits = pathInfo.split("/");
                if (splits.length == 2) {
                    if (splits[1].equals("search")) {
                        Date to = null, from = null;

                        DataState state = getDataState(request);
                        getAllSessionsBySearch(page, pageSize, state, request, response);

                    } else if (splits[1].equals("add")) {
                        getSettingAdd(response);
                    } else {
                        getSettingDetails(splits[1], response);
                    }
                }
            }

        } catch (SQLException ignored) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            Sessions sessions = generateObjectFromRequest(request);
            sessionsDAO.upsertSession(sessions);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");

        if (!splits[1].matches("^[0-9]*$")) {
            CommonErrorDispatch.ErrorDispatch(Collections.singletonList("id needs to be number"), request, response);
        }
        Long id = Long.valueOf(splits[1]);
        Sessions sessions = generateObjectFromRequest(request);
        sessions.setId(id);
        sessionsDAO.upsertSession(sessions);
    }

    private boolean isPathInfoAll(String pathInfo) {
        return pathInfo == null || pathInfo.equals("/all");
    }

    private void getAllSessions(HttpServletRequest request, HttpServletResponse response, DataState state)
            throws ServletException, IOException, SQLException {
        List<Sessions> sessionList = sessionsDAO.findAll(state.getValue());
        request.setAttribute("sessions", sessionList);
        request.setAttribute("states", DataState.getValues());

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "sessions/sessionList.jsp");
        dispatcher.forward(request, response);
    }

    public static DataState getDataState(HttpServletRequest request) {
        String name = request.getParameter("state");
        if (name == null) {
            return DataState.ALL;
        }
        return DataState.valueOf(name.toUpperCase());
    }

    private void getSettingDetails(String id, HttpServletResponse response)
            throws IOException, SQLException {

        SessionResponse res = new SessionResponse();
        Sessions sessions = sessionsDAO.findById(Long.valueOf(id));
        generateResponseForDetails(response, res, sessions);
    }

    private void generateResponseForDetails(HttpServletResponse response, SessionResponse res, Sessions sessions) throws IOException {
        List<Subjects> allSubject = subjectsDAO.findAll();
        res.setIsActive(sessions.getIsActive());
        res.setId(sessions.getId());
        res.setDuration(sessions.getDuration());
        res.setSubjects(sessions.getSubjects());
        res.setTopicName(sessions.getTopicName());

        res.setSubjectsList(allSubject);

        Gson gson = new Gson();
        String jsonSettingResponse = gson.toJson(res);
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonSettingResponse);
        out.flush();
    }

    private Sessions generateObjectFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try ( BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(' ');
            }
        }
        String payload = sb.toString();
        return objectMapper.readValue(payload, Sessions.class);
    }

    private void getAllSessionsBySearch(int page, int pageSize, DataState state, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Sessions> sessions;

        sessions = sessionsDAO.getAllSessions(page, pageSize);
        
        request.setAttribute("sessions", sessions);
        request.setAttribute("states", DataState.getValues());
        request.setAttribute("state", state.getName());

        int totalPages = calculateTotalPages(pageSize);
        setPaginationAttributes(request, page, pageSize, totalPages);
        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "sessions/sessionList.jsp");
        dispatcher.forward(request, response);
    }

    private void getSettingAdd(HttpServletResponse response)
            throws IOException, SQLException {

        SessionResponse res = new SessionResponse();
        Sessions sessions = new Sessions();
        generateResponseForDetails(response, res, sessions);
    }

    private void setPaginationAttributes(HttpServletRequest request, int page, int pageSize, int totalPages) {
        request.setAttribute("servletPath", request.getRequestURI());
        request.setAttribute("currentPage", page);
        request.setAttribute("size", pageSize);
        request.setAttribute("totalPages", totalPages);
    }

    private SettingTypeEnums parseTypeFromRequest(HttpServletRequest request) {
        String typeStr = request.getParameter("type");
        if (typeStr == null) {
            return SettingTypeEnums.ALL;
        } else {
            return SettingTypeEnums.valueOf(typeStr);
        }
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

    private int calculateTotalPages(int pageSize) {
        int totalRecords = getTotalRecordCount();
        return (int) Math.max(Math.ceil((double) totalRecords / pageSize), 1);
    }

    private int getTotalRecordCount() {
        return sessionsDAO.countSessions();
    }
}
