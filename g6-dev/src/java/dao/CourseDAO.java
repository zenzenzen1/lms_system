package dao;

import entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends BaseDAO {

    private PreparedStatement prepareFindByIdStatement(Connection connection, Long id) throws SQLException {
        String sql = "SELECT * FROM lms_course WHERE course_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public static Course extractCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("course_id"));
        course.setCode(rs.getString("code"));
        course.setSubjectId(rs.getLong("subject_id"));
        course.setSemesterId(rs.getLong("semester_id"));
        course.setTeacherId(rs.getLong("teacher_id"));
        course.setIsActive(rs.getBoolean("is_active"));
        course.setSemester(SettingDetailsDAO.extractSettingDetails(rs));
        course.setTeacher(UsersDAO.extractUser(rs));
        course.setSubjects(SubjectsDAO.extractSubjects(rs));
        return course;
    }

    public Course findByID(Long id)  {
        Course course = null;
        try (Connection connection = getConnection(); PreparedStatement ps = prepareFindByIdStatement(connection, id); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                course = extractCourse(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return course;
    }

    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM lms_course";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course course = extractCourse(rs);
                courseList.add(course);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return courseList;
    }
    
    public void insert(Course course) {
        String sql = "INSERT INTO lms_course(subject_id, code, semester_id, teacher_id, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, course.getSubjectId());
            ps.setString(2, course.getCode());
            ps.setLong(3, course.getSemesterId());
            ps.setLong(4, course.getTeacherId());
            ps.setBoolean(5, course.isIsActive());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void softDelete(Long id, boolean status) {
        String sql = "UPDATE lms_course SET is_active = ? WHERE course_id = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void update(Course course) {
        String sql = "UPDATE lms_course SET subject_id = ?, code = ?, semester_id = ?, teacher_id = ? WHERE course_id = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, course.getSubjectId());
            ps.setString(2, course.getCode());
            ps.setLong(3, course.getSemesterId());
            ps.setLong(4, course.getTeacherId());
            ps.setLong(5, course.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Course> searchCourseWithFilters(Course course) {
        List<Course> courseList = null;
        String sql = "SELECT * FROM lms_course WHERE code LIKE ?";
        int index = 1;
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(index++, "%" + course.getCode() + "%");
            if (course.getSubjectId() != null) {
                sql += " AND subject_id = ?";
                ps.setLong(index++, course.getSubjectId());
            }
            if (course.getTeacherId() != null) {
                sql += " AND teacher_id = ?";
                ps.setLong(index++, course.getTeacherId());
            }
            if (!course.isIsActive() || course.isIsActive()) {
                sql += " AND is_active = ?";
                ps.setBoolean(index++, course.isIsActive());
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (courseList == null) courseList = new ArrayList<>();
                Course newCourse = extractCourse(rs);
                courseList.add(newCourse);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return courseList;
    }
}