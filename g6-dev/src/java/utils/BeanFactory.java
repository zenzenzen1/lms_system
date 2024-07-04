/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import dao.*;

/**
 *
 * @author macbook
 */
public class BeanFactory {
    private static final UsersDAO userDao;
    private static final SubjectsDAO subjectDao;
    private static final FilesDAO filesDao;
    private static final CourseDAO ASSIGNS_DAO;
    private static final SettingDetailsDAO settingDetailsDao;
    private static final RedisUtil redis;
    private static final CourseDAO courseDAO;
    private static final SessionsDAO sessionsDao;
    private static final SchedulerDAO schedulerDao;
    private static final CourseDAO courseDao;

    static {
        // Here, insert the initialization of your actual DAOs.
        userDao = new UsersDAO();
        subjectDao = new SubjectsDAO();
        filesDao = new FilesDAO();
        ASSIGNS_DAO = new CourseDAO();
        settingDetailsDao = new SettingDetailsDAO();
        redis = new RedisUtil();
        courseDAO = new CourseDAO();
        sessionsDao = new SessionsDAO();
        schedulerDao = new SchedulerDAO();
        courseDao = new CourseDAO();
    }

    public static UsersDAO getUserDao() {
        return userDao;
    }

    public static SubjectsDAO getSubjectDao() {
        return subjectDao;
    }

    public static FilesDAO getFilesDao() {
        return filesDao;
    }

    public static CourseDAO getAssignDao() {
        return ASSIGNS_DAO;
    }

    public static SettingDetailsDAO getSettingDetailsDao() {
        return settingDetailsDao;
    }

    public static CourseDAO getCourseDAO() {
        return courseDAO;
    }

    public static CourseDAO getCourseDao() {
        return courseDao;
    }

    public static RedisUtil getRedisUtil() {return redis;}
    
    public static SessionsDAO getSessionsDao() {return sessionsDao;}

    public static SchedulerDAO getSchedulerDao() {return schedulerDao;}

}
