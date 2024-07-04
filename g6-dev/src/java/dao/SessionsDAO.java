package dao;

import entity.Sessions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionsDAO extends BaseDAO {
    protected static final String TABLE_NAME = "lms_session";

    public int countSessions() {
        String query = "SELECT count(*) from " + TABLE_NAME;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
        }
        return 0;
    }

    public List<Sessions> getAllSessions(int page, int pageSize) {
        String query = "SELECT * FROM " + TABLE_NAME + " " +
                 " LEFT JOIN lms_subjects on lms_session.subject_id = lms_subjects.subject_id LEFT JOIN lms_setting_details on lms_subjects.category_id = lms_setting_details.setting_id "+ 
                 " limit ?,?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Math.max((page - 1) * pageSize, 0));
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            List<Sessions> sessionsList = new ArrayList<>();
            while (rs.next()) {
                Sessions session = extractSession(rs);
                sessionsList.add(session);
            }
            return sessionsList;
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Sessions> getAllSessionsBySubjectId(Long subjectId) {
        String query = "SELECT * FROM " + TABLE_NAME + " " +
                " LEFT JOIN lms_subjects on lms_session.subject_id = lms_subjects.subject_id "
                + " LEFT JOIN lms_setting_details on lms_subjects.category_id = lms_setting_details.setting_id" + 
                " where lms_session.subject_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            List<Sessions> sessionsList = new ArrayList<>();
            while (rs.next()) {
                Sessions session = extractSession(rs);
                sessionsList.add(session);
            }
            return sessionsList;
        } catch (SQLException e) {
        }
        return null;
    }

    public Long upsertSession(Sessions session) {
        String sqlQuery;

        if (session.getId() != null) {
            sqlQuery = "UPDATE lms_session SET topic_name = ?, duration = ?, subject_id = ?, is_active = ? WHERE session_id = ?";
        } else {
            sqlQuery = "INSERT INTO lms_session (topic_name, duration, subject_id, is_active) VALUES (?, ?, ?, ?)";
        }

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {


            if (session.getId() != null) {
                ps.setLong(5, session.getId());
            }

            ps.setString(1, session.getTopicName());
            ps.setInt(2, session.getDuration());
            ps.setLong(3, session.getSubjectId());
            ps.setBoolean(4, session.getIsActive());

            // Execute Update and check the affected rows
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating session failed, no rows affected.");
            }

            // Handle the situation when insert operation is done. It retrieves the generated key
            if (session.getId() == null) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating session failed, no ID obtained.");
                    }
                }
            }

            // Return affected rows
            return (long) affectedRows;

        } catch (SQLException e) {
        }

        return -1L;
    }

    public Sessions findById(Long id) throws SQLException {
        Sessions session = null;
        String query = "SELECT * FROM " + TABLE_NAME +
                "                LEFT JOIN lms_subjects on lms_session.subject_id = lms_subjects.subject_id \n "
                + " LEFT JOIN lms_setting_details on lms_subjects.category_id = lms_setting_details.setting_id" + 
                "                 WHERE lms_session.session_id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (session == null) {
                    session = extractSession(rs);
                }

            }
        }

        return session;
    }

    public List<Sessions> findAll(int state) throws SQLException {
        List<Sessions> sessionsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME +
                " LEFT JOIN lms_subjects on lms_session.subject_id = lms_subjects.subject_id  "
                + " LEFT JOIN lms_setting_details on lms_subjects.category_id = lms_setting_details.setting_id"; 
        if (state != -1) query += " WHERE lms_session.is_active=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (state != -1) stmt.setInt(1, state);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sessions session = extractSession(rs);
                sessionsList.add(session);
            }
        }

        return sessionsList;
    }

    public void saveAll(List<Sessions> sessionsList) {
        for (Sessions session : sessionsList) {
            upsertSession(session);
        }
    }


    static Sessions extractSession(ResultSet rs) throws SQLException {
        Sessions session = new Sessions();
        session.setId(rs.getLong("session_id"));
        session.setTopicName(rs.getString("topic_name"));
        session.setDuration(rs.getInt("duration"));
        session.setSubjectId(rs.getLong("subject_id"));
        session.setIsActive(rs.getBoolean("is_active"));

        session.setSubjects(SubjectsDAO.extractSubjects(rs));
        return session;
    }
}
