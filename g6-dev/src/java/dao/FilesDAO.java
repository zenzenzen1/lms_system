package dao;

import entity.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FilesDAO extends BaseDAO {

    protected static final String TABLE_NAME = "lms_files";

    static Files extractFiles(ResultSet rs) throws SQLException {
        Files files = new Files();
        files.setId(rs.getLong("id"));
        files.setObjectId(rs.getLong("object_id"));
        files.setType(rs.getInt("type"));
        files.setUrl(rs.getString("data"));
        files.setIsActive(rs.getBoolean("is_active"));
        files.setCreateDt(rs.getTimestamp("create_dt"));
        return files;
    }

    public List<Files> findAll() {
        List<Files> filesList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Files files = extractFiles(rs);
                filesList.add(files);
            }
        } catch (SQLException ex) {
            // Handle exception
        }
        return filesList;
    }

    public Files findById(Long id) {
        Files files = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                files = extractFiles(rs);
            }
        } catch (SQLException ex) {
            // Handle exception
        }
        return files;
    }

    public void insert(Files files) {
        String sql = "INSERT INTO " + TABLE_NAME + "(object_id, type, file_name, url, is_active, create_dt) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, files.getObjectId());
            ps.setInt(2, files.getType());
            ps.setString(3, files.getFileName());
            ps.setString(4,files.getUrl());
            ps.setBoolean(5, files.getIsActive());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Handle exception
        }
    }

    public void update(Files files) {
        String sql = "UPDATE " + TABLE_NAME + " SET object_id = ?, type = ?, url = ?, file_name = ?, is_active = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, files.getObjectId());
            ps.setInt(2, files.getType());
            ps.setString(3, files.getUrl());
            ps.setString(4, files.getFileName());
            ps.setBoolean(5, files.getIsActive());
            ps.setLong(6, files.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Handle exception
        }
    }

    public void delete(Long id) {
        String sql = "UPDATE " + TABLE_NAME + " set is_active = !is_active WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Handle exception
        }
    }
}