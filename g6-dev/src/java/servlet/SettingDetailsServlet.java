/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
import constants.LmsConstants;
import context.LmsContextHolder;
import dto.request.SettingRequest;
import utils.BeanFactory;
import dao.SettingDetailsDAO;
import dao.UsersDAO;
import dto.response.SettingResponse;
import entity.SettingDetails;
import enums.DataState;
import enums.SettingTypeEnums;
import error.CommonErrorDispatch;
import utils.LmsMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author macbook
 */
public class SettingDetailsServlet extends HttpServlet {

    private SettingDetailsDAO settingsDetailsDao;

    private ObjectMapper objectMapper;

    private UsersDAO userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        settingsDetailsDao = BeanFactory.getSettingDetailsDao();
        objectMapper = new ObjectMapper();
        userDao = BeanFactory.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            int page = parsePageFromRequest(request);
            int pageSize = parsePageSizeFromRequest(request);
            if (isPathInfoAll(pathInfo)) {
                SettingTypeEnums type = parseTypeFromRequest(request);
                int totalPages = calculateTotalPages(pageSize, type, null);

                DataState state = getDataState(request);

                setPaginationAttributes(request, page, pageSize, totalPages);

                getAllSettingDetailsByEnums(page, pageSize, type, state, request, response);
            } else {
                String[] splits = pathInfo.split("/");
                if (splits.length == 2) {
                    if (splits[1].equals("search")) {
                        String typeStr = request.getParameter("type");
                        SettingTypeEnums type = typeStr != null
                                ? SettingTypeEnums.valueOf(request.getParameter("type"))
                                : SettingTypeEnums.ALL;
                        String query = request.getParameter("query");
                        String sort = request.getParameter("sort");
                        String direction = request.getParameter("direction");
                        DataState state = getDataState(request);
                        getAllSettingDetailsBySearch(sort, direction,page, pageSize, query, type, state, request, response);
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

    static DataState getDataState(HttpServletRequest request) {
        String stateStr = request.getParameter("state");
        DataState state;
        if(StringUtils.isNullOrEmpty(stateStr)){
            state = DataState.ALL;
        }else{
            state = DataState.valueFromName(stateStr);
        }
        return state;
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        SettingDetails details = generateObjectFromRequest(request);

        if (!splits[1].matches("^[0-9]*$")) {
            CommonErrorDispatch.ErrorDispatch(Collections.singletonList("id needs to be number"), request, response);
        }
        Long id = Long.valueOf(splits[1]);

        if (userDao.existUserWithAssignRole(id)) {
            CommonErrorDispatch.ErrorDispatch(Collections.singletonList("The record you are editting is related to active user"), request, response);
        }
        details.setEditedBy(LmsContextHolder.getContext().getUserId());
        details.setId(id);
        settingsDetailsDao.upSert(details);
    }

    private SettingDetails generateObjectFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(' ');
            }
        }
        String payload = sb.toString();
        return objectMapper.readValue(payload, SettingDetails.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            SettingDetails details = generateObjectFromRequest(request);
            details.setCreatedBy(LmsContextHolder.getContext().getUserId());

            settingsDetailsDao.upSert(details);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        Long id = Long.valueOf(splits[1]);
        settingsDetailsDao.delete(id);
    }

    private void getAllSettingDetailsByEnums(int page, int pageSize, SettingTypeEnums enums, DataState state, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SettingDetails> settingDetails;
        if (enums.equals(SettingTypeEnums.ALL)) {
            settingDetails = settingsDetailsDao.findAllIsActive(page, pageSize);
        } else {
            settingDetails = settingsDetailsDao.findAllByTypeAndIsActive(enums.name(), page, pageSize);
        }
        request.setAttribute("settings", settingDetails);
        request.setAttribute("settingsList", SettingTypeEnums.values());
        request.setAttribute("states", DataState.getValues());
        request.setAttribute("state", state.getName());
        request.setAttribute("type", enums.name());

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "settings/settingList.jsp");
        dispatcher.forward(request, response);
    }

    private void getAllSettingDetailsBySearch(String sort, String direction, int page, int pageSize, String query, SettingTypeEnums enums, DataState state, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SettingDetails> settingDetails;
        if (enums == null || enums.equals(SettingTypeEnums.ALL)) {
            settingDetails = settingsDetailsDao.findAllByIsActiveSearch(sort, direction, page, pageSize, state.getValue(), query);
        } else {
            settingDetails = settingsDetailsDao.findAllByTypeAndIsActiveSearch(sort, direction, page, pageSize, state.getValue(), query, enums.name());
        }
        request.setAttribute("settings", settingDetails);
        request.setAttribute("settingsList", SettingTypeEnums.values());
        request.setAttribute("states", DataState.getValues());
        request.setAttribute("state", state.getName());
        request.setAttribute("type", enums == null ? SettingTypeEnums.ALL.name() : enums.name());
        request.setAttribute("query", query);
        int totalPages = calculateTotalPages(pageSize, enums, query);
        setPaginationAttributes(request, page, pageSize, totalPages);
        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "settings/settingList.jsp");
        dispatcher.forward(request, response);
    }

    private void getSettingDetails(String id, HttpServletResponse response)
            throws IOException, SQLException {

        SettingResponse res = new SettingResponse();

        SettingDetails setting = settingsDetailsDao.findById(Long.parseLong(id));
        if(setting.getType().equals(SettingTypeEnums.ROLE)){
            setting.setSettingName(setting.getSettingValue());
            List<SettingDetails> permissions = settingsDetailsDao.findAllPermissionOfRoleName(setting.getSettingName(), setting.getType().name());
            setting.setPermissions(permissions);
        }
        generateResponseForDetails(response, res, setting);
    }

    private void generateResponseForDetails(HttpServletResponse response, SettingResponse res, SettingDetails setting) throws IOException {
        List<String> settingTypes = SettingTypeEnums.getValues();
        settingTypes.remove(SettingTypeEnums.ALL.name());
        SettingRequest settingRequest = LmsMapper.mapToSettingRequest(setting);
        List<SettingDetails> allDefaultPermission = settingsDetailsDao.findAllSettingsByType(SettingTypeEnums.PERMISSION.name());
        List<SettingDetails> allRoles = settingsDetailsDao.findAllSettingsByType(SettingTypeEnums.ROLE.name());
        settingRequest.setRoles(allRoles);
        settingRequest.setPermissions(generateSettingsMap(allDefaultPermission, setting.getPermissions()));
        res.setSetting(settingRequest);
        res.setSettingTypes(settingTypes);

        Gson gson = new Gson();
        String jsonSettingResponse = gson.toJson(res);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonSettingResponse);
        out.flush();
    }

    public Map<String, Boolean> generateSettingsMap(List<SettingDetails> allDefaultPermission, List<SettingDetails> settingsPermissions) {
        ObjectMapper mapper = new ObjectMapper();
        if(settingsPermissions == null ){
            settingsPermissions = new ArrayList<>();
        }
        Set<SettingDetails> settingsSet = new HashSet<>(settingsPermissions);

        return allDefaultPermission.stream()
                .collect(Collectors.toMap(s -> {
                    try {
                        // convert SettingDetails to JSON String
                        return mapper.writeValueAsString(s);
                    } catch (Exception e) {
                        // handle the exception
                        return "{}";
                    }
                }, settingsSet::contains));
    }

    private void getSettingAdd(HttpServletResponse response)
            throws IOException, SQLException {

        SettingResponse res = new SettingResponse();
        SettingDetails setting = new SettingDetails();
        generateResponseForDetails(response, res, setting);
    }

    private void setPaginationAttributes(HttpServletRequest request, int page, int pageSize, int totalPages) {
        request.setAttribute("servletPath", request.getRequestURI());
        request.setAttribute("currentPage", page);
        request.setAttribute("size", pageSize);
        request.setAttribute("totalPages", totalPages);
    }

    private boolean isPathInfoAll(String pathInfo) {
        return pathInfo == null || pathInfo.equals("/all");
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

    private int calculateTotalPages(int pageSize, SettingTypeEnums enums, String query) {
        int totalRecords = getTotalRecordCount(enums, query);
        return (int) Math.max(Math.ceil((double) totalRecords / pageSize), 1);
    }

    private int getTotalRecordCount(SettingTypeEnums enums, String query) {
        return settingsDetailsDao.countSetting(enums, query);
    }

}
