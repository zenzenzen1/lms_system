package dao;

import entity.Attendances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendancesDAO extends BaseDAO {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM lms_admin.lms_attendances WHERE attedance_id = ?";
    private static final String INSERT_ATTENDANCE_QUERY = "INSERT INTO lms_admin.lms_attendances(student_id, scheduler_id, attendance_status, attendance_note) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ATTENDANCE_QUERY = "UPDATE lms_admin.lms_attendances SET student_id = ?, scheduler_id = ?, attendance_status = ?, attendance_note = ? WHERE attedance_id = ?";
    private static final String DELETE_ATTENDANCE_QUERY = "DELETE FROM lms_admin.lms_attendances WHERE attedance_id = ?";

    public static Attendances extractAttendance(ResultSet rs) throws SQLException {
        Attendances attendances = new Attendances();
        attendances.setId(rs.getLong("attedance_id"));
        attendances.setStudentId(rs.getLong("student_id"));
        attendances.setSchedulerId(rs.getLong("scheduler_id"));
        attendances.setAttendanceStatus(rs.getBoolean("attendance_status"));
        attendances.setAttendanceNote(rs.getString("attendance_note"));
        return attendances;
    }

    public Attendances findById(Long id) throws SQLException {
        Attendances attendance = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                attendance = extractAttendance(rs);
            }
        }
        return attendance;
    }

    public void insertAttendance(Attendances attendances) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ATTENDANCE_QUERY)) {
            stmt.setLong(1, attendances.getStudentId());
            stmt.setLong(2, attendances.getSchedulerId());
            stmt.setBoolean(3, attendances.isAttendanceStatus());
            stmt.setString(4, attendances.getAttendanceNote());
            stmt.executeUpdate();
        }
    }

    public void updateAttendance(Attendances attendances) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_ATTENDANCE_QUERY)) {
            stmt.setLong(1, attendances.getStudentId());
            stmt.setLong(2, attendances.getSchedulerId());
            stmt.setBoolean(3, attendances.isAttendanceStatus());
            stmt.setString(4, attendances.getAttendanceNote());
            stmt.setLong(5, attendances.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteAttendance(Long id) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_ATTENDANCE_QUERY)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}