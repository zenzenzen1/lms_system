/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package servlet;

import constants.LmsConstants;
import dao.CourseDAO;
import dao.SubjectsDAO;
import dao.UsersDAO;
import entity.Course;
import entity.CourseStudent;
import entity.Subjects;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.BeanFactory;

/**
 *
 * @author Hayashi
 */
public class CourseServlet extends HttpServlet {
    
    private CourseDAO courseDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        courseDAO = BeanFactory.getCourseDAO();
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/all")) {
            List<Course> courseList = courseDAO.findAll();
            request.setAttribute("courseList", courseList);
            SubjectsDAO subjectDAO = new SubjectsDAO();
            List<Subjects> subjectList = subjectDAO.findAll();
            request.setAttribute("subjectList", subjectList);
            UsersDAO userDAO = new UsersDAO();
            List<Users> teacherList = userDAO.findUserByRoleName("Teacher");
            request.setAttribute("teacherList", teacherList);
            request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "course/courseList.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/details")) {
            long id = Long.parseLong(request.getParameter("id"));
            Course course = courseDAO.findByID(id);
            request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "course/courseDetails.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/changeStatus")) {
            
        } else if (pathInfo.equals("/add")) {
            SubjectsDAO subjectDAO = new SubjectsDAO();
            List<Subjects> subjectList = subjectDAO.findAll();
            request.setAttribute("subjectList", subjectList);
            UsersDAO userDAO = new UsersDAO();
            List<Users> teacherList = userDAO.findUserByRoleName("Teacher");
            request.setAttribute("teacherList", teacherList);
            request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "course/addCourse.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit")) {
            long id = Long.parseLong(request.getParameter("id"));
            Course course = courseDAO.findByID(id);
            request.setAttribute("course", course);
            SubjectsDAO subjectDAO = new SubjectsDAO();
            List<Subjects> subjectList = subjectDAO.findAll();
            request.setAttribute("subjectList", subjectList);
            UsersDAO userDAO = new UsersDAO();
            List<Users> teacherList = userDAO.findUserByRoleName("Teacher");
            request.setAttribute("teacherList", teacherList);
            request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "course/editCourse.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/search")) {
            Course course = new Course();
            course.setCode(request.getParameter("code"));
            if (!request.getParameter("subject").isEmpty() && request.getParameter("subject") != null) {
                course.setSubjectId(Long.parseLong(request.getParameter("subject")));
            }
            if (!request.getParameter("teacher").isEmpty() && request.getParameter("teacher") != null) {
                course.setTeacherId(Long.parseLong(request.getParameter("teacher")));
            }
            if (!request.getParameter("status").isEmpty() && request.getParameter("status") != null) {
                course.setIsActive(request.getParameter("status").equals("true"));
            }
            List<Course> courseList = courseDAO.searchCourseWithFilters(course);
            request.setAttribute("courseList", courseList);
            request.getRequestDispatcher(LmsConstants.VIEW_BASE_PATH + "course/courseList.jsp").forward(request, response);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo.startsWith("/changeStatus")) {
            long id = Long.parseLong(request.getParameter("id"));
            Course course = courseDAO.findByID(id);
            courseDAO.softDelete(id, !course.isIsActive());
        } else if (pathInfo.equals("/add")) {
            long subjectId = Long.parseLong(request.getParameter("subject"));
            long teacherId = Long.parseLong(request.getParameter("teacher"));
            String code = request.getParameter("code");
            boolean status = request.getParameter("status").equals("active");
            Course course = new Course();
            course.setTeacherId(teacherId);
            course.setSubjectId(subjectId);
            course.setSemester(null);
            course.setCode(code);
            course.setIsActive(status);
            courseDAO.insert(course);
            response.sendRedirect("/all");
        } else if (pathInfo.startsWith("/edit")) {
            long id = Long.parseLong(request.getParameter("id"));
            long subjectId = Long.parseLong(request.getParameter("subject"));
            long teacherId = Long.parseLong(request.getParameter("teacher"));
            String code = request.getParameter("code");
            boolean status = request.getParameter("status").equals("active");
            Course course = new Course();
            course.setId(id);
            course.setTeacherId(teacherId);
            course.setSubjectId(subjectId);
            course.setSemester(null);
            course.setCode(code);
            course.setIsActive(status);
            courseDAO.update(course);
            response.sendRedirect("/details?id=" + id);
        }
    }

}
