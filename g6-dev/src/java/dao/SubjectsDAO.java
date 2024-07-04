package dao;

import entity.Subjects;
import utils.BeanFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectsDAO extends BaseDAO {

    private PreparedStatement prepareInsertStatement(Connection connection, Subjects subjects) throws SQLException {
        String sql = "INSERT INTO lms_subjects(name, description, is_online, is_active, create_dt, category_id) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, subjects.getName());
        ps.setString(2, subjects.getDescription());
        ps.setBoolean(3, subjects.getIsOnline());
        ps.setBoolean(4, subjects.getIsActive());
        ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        ps.setLong(6, subjects.getCategoryId());
        return ps;
    }

    private PreparedStatement prepareUpdateStatement(Connection connection, Subjects subjects) throws SQLException {
        String sql = "UPDATE lms_subjects SET name = ?, description = ?, is_online = ?, is_active = ? WHERE subject_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, subjects.getName());
        ps.setString(2, subjects.getDescription());
        ps.setBoolean(3, subjects.getIsOnline());
        ps.setBoolean(4, subjects.getIsActive());
        ps.setLong(5, subjects.getId());
        return ps;
    }

    private PreparedStatement prepareDeleteStatement(Connection connection, Long id) throws SQLException {
        String sql = "DELETE FROM lms_subjects WHERE subject_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    static Subjects extractSubjects(ResultSet rs) throws SQLException {
        Subjects subjects = new Subjects();
        subjects.setId(rs.getLong("subject_id"));
        subjects.setName(rs.getString("name"));
        subjects.setDescription(rs.getString("description"));
        subjects.setIsOnline(rs.getBoolean("is_online"));
        subjects.setIsActive(rs.getBoolean("is_active"));
        subjects.setCreateDt(rs.getTimestamp("create_dt"));
        subjects.setCategoryId(rs.getLong("category_id"));
        subjects.setCategory(SettingDetailsDAO.extractSettingDetails(rs));
        return subjects;
    }

    public List<Subjects> findAll() {
        List<Subjects> subjectsList = new ArrayList<>();
        String sql = "select * from lms_subjects lsb " +
                "left join lms_setting_details lsd on lsd.setting_id = lsb.category_id";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Subjects subjects = extractSubjects(rs);
                subjectsList.add(subjects);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectsList;
    }

    public Subjects findById(Long id) {
        Subjects subjects = null;
        String sql = "SELECT * FROM lms_subjects WHERE subject_id = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subjects = extractSubjects(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    public void insert(Subjects subjects) {
        try (Connection connection = getConnection(); PreparedStatement ps = prepareInsertStatement(connection, subjects)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Subjects subjects) {
        try (Connection connection = getConnection(); PreparedStatement ps = prepareUpdateStatement(connection, subjects)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Long id) {
        try (Connection connection = getConnection(); PreparedStatement ps = prepareDeleteStatement(connection, id)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}