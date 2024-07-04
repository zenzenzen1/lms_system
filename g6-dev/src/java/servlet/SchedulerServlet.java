package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import constants.LmsConstants;
import dao.*;
import dto.response.SchedulerResponse;
import entity.*;
import enums.DataState;
import enums.SettingTypeEnums;
import error.CommonErrorDispatch;
import utils.BeanFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static servlet.SessionsServlet.getDataState;

public class SchedulerServlet extends HttpServlet {
    private static SchedulerDAO schedulerDAO;
    private static SettingDetailsDAO settingDetailsDAO;
    private static SessionsDAO sessionsDAO;
    private static CourseDAO courseDAO;
    private static UsersDAO usersDAO;
    private static ObjectMapper objectMapper;

    @Override
    public void init() {
        schedulerDAO = BeanFactory.getSchedulerDao();
        settingDetailsDAO = BeanFactory.getSettingDetailsDao();
        sessionsDAO = BeanFactory.getSessionsDao();
        courseDAO = BeanFactory.getCourseDao();
        usersDAO = BeanFactory.getUserDao();
        objectMapper = new ObjectMapper();
    }

    private boolean isPathInfoAll(String pathInfo) {
        return pathInfo == null || pathInfo.equals("/all");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (isPathInfoAll(pathInfo)) {
            DataState state = getDataState(request);
            getAllScheduler(request, response, state);
        } else {
            String[] splits = pathInfo.split("/");
            if (splits.length == 2) {
                if (splits[1].equals("search")) {
                    String sort = request.getParameter("sort");
                    String direction = request.getParameter("direction");
                    DataState state = getDataState(request);
                    getAllSchedulerBySearch(sort, direction, state, request, response);
                } else if (splits[1].equals("add")) {
                    try {
                        getSchedulerAdd(response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        getSettingDetails(splits[1], response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void getSettingDetails(String id, HttpServletResponse response)
            throws IOException, SQLException {

        SchedulerResponse res = new SchedulerResponse();
        Scheduler scheduler = schedulerDAO.findById(Long.valueOf(id));
        generateResponseForDetails(response, res, scheduler);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/save")) {
            try {
                Scheduler newScheduler = generateObjectFromRequest(request);
                schedulerDAO.upsertScheduler(newScheduler);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse scheduler from request");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown endpoint");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        Scheduler newScheduler = generateObjectFromRequest(request);
        if (!splits[1].matches("^[0-9]*$")) {
            CommonErrorDispatch.ErrorDispatch(Collections.singletonList("id needs to be number"), request, response);
        }
        Long id = Long.valueOf(splits[1]);
        newScheduler.setId(id);
        schedulerDAO.upsertScheduler(newScheduler);
    }

    private void getAllScheduler(HttpServletRequest request, HttpServletResponse response, DataState state)
            throws ServletException, IOException {
        List<Scheduler> allSchedules = schedulerDAO.getAllSchedulers(null, null);
        request.setAttribute("schedulers", allSchedules);
        request.setAttribute("states", DataState.getValues());

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "scheduler/schedulerList.jsp");
        dispatcher.forward(request, response);
    }

    private void generateResponseForDetails(HttpServletResponse response, SchedulerResponse res, Scheduler scheduler) throws IOException, SQLException {
        res = SchedulerResponse.mapSchedulerToSchedulerResponse(scheduler);
        List<Sessions> sessionsList = sessionsDAO.findAll(1);
        List<Course> coursesList = courseDAO.findAll();
        List<SettingDetails> slotList = settingDetailsDAO.findAllSettingsByType(SettingTypeEnums.SLOT.name());
        List<SettingDetails> roomList = settingDetailsDAO.findAllSettingsByType(SettingTypeEnums.ROOM.name());
        List<Users> usersList = usersDAO.findAll();
        res.setCourseList(coursesList);
        res.setSlotList(slotList);
        res.setRoomList(roomList);
        res.setSessionsList(sessionsList);
        res.setTeacherList(usersList);

        Gson gson = new Gson();
        String jsonSettingResponse = gson.toJson(res);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonSettingResponse);
        out.flush();
    }

    private Scheduler generateObjectFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(' ');
            }
        }
        String payload = sb.toString();
        return objectMapper.readValue(payload, Scheduler.class);
    }

    private void getAllSchedulerBySearch(String order, String direction, DataState state, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Scheduler> schedulerList;

        schedulerList = schedulerDAO.getAllSchedulers(order, direction);

        request.setAttribute("schedulers", schedulerList);
        request.setAttribute("states", DataState.getValues());
        request.setAttribute("state", state.getName());

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "sessions/sessionList.jsp");
        dispatcher.forward(request, response);
    }

    private void getSchedulerAdd(HttpServletResponse response)
            throws IOException, SQLException {

        SchedulerResponse res = new SchedulerResponse();
        Scheduler sessions = new Scheduler();
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
        return schedulerDAO.countScheduler();
    }
}