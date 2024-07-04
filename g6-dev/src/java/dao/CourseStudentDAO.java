/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.CourseStudent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hayashi
 */
public class CourseStudentDAO extends BaseDAO {
    
    public static CourseStudent extractCourseStudent(ResultSet rs) throws SQLException {
        CourseStudent cs = new CourseStudent();
        cs.setId(rs.getLong("id"));
        cs.setCourseId(rs.getLong("course_id"));
        cs.setStudentId(rs.getLong("student_id"));
        cs.setStatus(rs.getBoolean("status"));
        cs.setNote(rs.getString("note"));
        cs.setCreatedDate(rs.getTimestamp("created_dt"));
        cs.setCreatedById(rs.getLong("created_by"));
        cs.setUpdatedDate(rs.getTimestamp("updated_dt"));
        cs.setUpdatedById(rs.getLong("updated_by"));
        return cs;
    }
    
    public CourseStudent findById(Long id) {
        CourseStudent cs = null;
        String sql = "SELECT * from lms_course_student WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ps.setLong(1, id);
            if (rs.next()) {
                cs = extractCourseStudent(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cs;
    }
    
    public List<CourseStudent> findAll() {
        List<CourseStudent> csList = null;
        String sql = "SELECT * from lms_course_student";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (csList == null) csList = new ArrayList<CourseStudent>();
                CourseStudent cs = extractCourseStudent(rs);
                csList.add(cs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return csList;
    }
    
    public void insert(CourseStudent cs) {
        String sql = "INSERT INTO lms_course_student(course_id, student_id, status, note, created_dt, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cs.getCourseId());
            ps.setLong(2, cs.getStudentId());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
