/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import constants.LmsConstants;
import dao.SubjectsDAO;
import entity.Subjects;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.BeanFactory;

/**
 *
 * @author macbook
 */
public class SubjectsServlet extends HttpServlet {

    private SubjectsDAO subjectDao;
    
    @Override
    public void init() throws ServletException {
        super.init();
        subjectDao = BeanFactory.getSubjectDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/all")) {
            getAllSubjects(request, response);
        } else if (pathInfo.startsWith("/details")){
            String id = request.getParameter("id");
            getSubjectById(id, request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    private void getAllSubjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Subjects> subjects = subjectDao.findAll();
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "subject/subjectList.jsp").forward(request, response);
    }

    private void getSubjectById(String id, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {      
        Subjects subject = subjectDao.findById(Long.parseLong(id));
        request.setAttribute("subject", subject);

        RequestDispatcher dispatcher = request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "subject/subjectDetails.jsp");
        dispatcher.forward(request, response);
    }

    
}
    