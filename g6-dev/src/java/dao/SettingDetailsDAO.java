package dao;

import entity.SettingDetails;
import enums.SettingTypeEnums;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingDetailsDAO extends BaseDAO {

    public int countSetting(SettingTypeEnums enums, String query) {
        StringBuilder sql = new StringBuilder();
        if (enums == null || enums.equals(SettingTypeEnums.ALL)) {
            sql.append("select count(*) from lms_setting_details");
        } else {
            sql.append("select count(*) from lms_setting_details where type = ?");
        }
        if (query != null) {
            sql.append(" where setting_name like ? or setting_value like ?");
        }
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            if (enums != null && !enums.equals(SettingTypeEnums.ALL)) {
                ps.setString(1, enums.name());
                if (query != null) {
                    ps.setString(2, "%" + query + "%");
                    ps.setString(3, "%" + query + "%");
                }
            } else if (query != null) {
                ps.setString(1, "%" + query + "%");
                ps.setString(2, "%" + query + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public SettingDetails findById(Long id) throws SQLException {
        String sql = "SELECT * FROM lms_setting_details p where p.setting_id = ? ";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return extractSettingDetails(rs);
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public Long delete(Long id) {
        String query = "UPDATE lms_setting_details set is_active = not is_active where setting_id = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            return (long) ps.executeUpdate();
        } catch (SQLException e) {

        }
        return -1L;
    }

    public Long upSert(SettingDetails details) {
        String sqlQuery;
        if (details.getId() != null) {
            sqlQuery = "UPDATE lms_setting_details SET setting_name = ?, setting_value = ?, type = ?, "
                    + "type = ?, is_active = ?, created_by = ?, "
                    + "edited_by = ?, parent_id = ? WHERE setting_id = ?";
        } else {
            sqlQuery = "INSERT INTO lms_setting_details (setting_name, setting_value, type, is_active, created_by, edited_by, parent_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, details.getSettingName());
            ps.setString(2, details.getSettingValue());
            ps.setString(3, details.getType().name());
            ps.setBoolean(4, details.getIsActive());
            ps.setLong(5, details.getCreatedBy() == null ? 1L : details.getCreatedBy());
            ps.setLong(6, details.getEditedBy() == null ? -1L : details.getEditedBy());
            ps.setLong(7, details.getParentId() == null ? -1L : details.getParentId());

            if (details.getId() != null) {
                ps.setLong(8, details.getId());
            }
            return (long) ps.executeUpdate();
        } catch (SQLException e) {

        }
        return -1L;
    }

    public List<SettingDetails> findAllPermissionOfRoleName(String roleName, String role) throws SQLException {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        String sql = "SELECT rp.* FROM lms_setting_details p INNER JOIN lms_setting_details rp ON p.setting_id = rp.parent_id "
                + "WHERE p.setting_value = ? AND p.type = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, roleName);
            ps.setString(2, role);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(SettingDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return settingDetailsList;
    }

    public List<SettingDetails> findAllPermissionBySettingName(String settingName, String type) {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        String sql = "SELECT * FROM lms_setting_details WHERE setting_name = ? AND type = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, settingName);
            ps.setString(2, type);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            // Handle exception
        }

        return settingDetailsList;
    }

    public List<SettingDetails> findAllSettingsByType(String type) {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append("    s.* ");
        stringBuilder.append("FROM ");
        stringBuilder.append("    lms_setting_details s ");
        stringBuilder.append("JOIN ");
        stringBuilder.append("    (SELECT ");
        stringBuilder.append("        setting_value, ");
        stringBuilder.append("        MAX(setting_id) AS maxId ");
        stringBuilder.append("    FROM ");
        stringBuilder.append("        lms_setting_details ");
        stringBuilder.append("    WHERE ");
        stringBuilder.append("        type = ?  ");
        stringBuilder.append("    GROUP BY ");
        stringBuilder.append("        setting_value) AS groupedS ");
        stringBuilder.append("ON ");
        stringBuilder.append("    s.setting_value = groupedS.setting_value ");
        stringBuilder.append("    AND s.setting_id = groupedS.maxId");
        String sql = stringBuilder.toString();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, type);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            // Handle exception
        }

        return settingDetailsList;
    }

    public List<SettingDetails> findAllByTypeAndIsActive(String type, int page, int pageSize) {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        String sql = "SELECT * FROM lms_setting_details WHERE type = ? limit ?,?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setInt(3, pageSize);
            ps.setInt(2, Math.max((page - 1) * pageSize, 0));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            // Handle exception
        }

        return settingDetailsList;
    }

    public List<SettingDetails> findAllIsActive(int page, int pageSize) {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        String sql = "SELECT * FROM lms_setting_details limit ?,?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(2, pageSize);
            ps.setInt(1, Math.max((page - 1) * pageSize, 0));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            // Handle exception
        }

        return settingDetailsList;
    }

    public List<SettingDetails> findAllByTypeAndIsActiveSearch(String sort, String direction, int page, int pageSize, int isActiveValue, String query, String settingTypeName) {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM lms_setting_details WHERE (setting_name like ? OR setting_value like ?) AND type = ?");
        if (isActiveValue != -1) {
            sql.append(" and is_active =").append(isActiveValue);
        }
        if (sort != null) sql.append(" order by ? ?");

        sql.append(" limit ?,?");
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            // Used PreparedStatement's setString method with SQL wildcard for partial match search
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ps.setString(3, settingTypeName);
            if (sort != null) {
                ps.setInt(7, pageSize);
                ps.setInt(6, Math.max((page - 1) * pageSize, 0));
                ps.setString(4, sort);
                ps.setString(5, direction == null ? "asc" : direction);
            }else{
                ps.setInt(5, pageSize);
                ps.setInt(4, Math.max((page - 1) * pageSize, 0));

            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            // Handle exception
        }

        return settingDetailsList;
    }

    public List<SettingDetails> findAllByIsActiveSearch(String sort, String direction, int page, int pageSize, int isActiveValue, String query) {
        List<SettingDetails> settingDetailsList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM lms_setting_details WHERE (setting_name like ? OR setting_value like ?)");
        if (isActiveValue != -1) {
            sql.append(" and is_active =").append(isActiveValue);
        }
        if (sort != null) sql.append(" order by ?" + (direction == null ? "asc" : direction));

        sql.append(" limit ?,?");
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            if (query == null) {
                query = "";
            }
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");

            if (sort != null) {
                ps.setInt(5, pageSize);
                ps.setInt(4, Math.max((page - 1) * pageSize, 0));
                ps.setString(3, sort);
            }else {
                ps.setInt(4, pageSize);
                ps.setInt(3, Math.max((page - 1) * pageSize, 0));
            }


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SettingDetails settingDetails = extractSettingDetails(rs);
                settingDetailsList.add(settingDetails);
            }
        } catch (SQLException ex) {
            // Handle exception
        }

        return settingDetailsList;
    }

    static SettingDetails extractSettingDetails(ResultSet rs) throws SQLException {
        SettingDetails settingDetails = new SettingDetails();
        settingDetails.setId(rs.getLong("setting_id"));
        settingDetails.setSettingName(rs.getString("setting_name"));
        settingDetails.setSettingValue(rs.getString("setting_value"));
        settingDetails.setType(SettingTypeEnums.valueOf(rs.getString("type")));
        settingDetails.setIsActive(rs.getBoolean("is_active"));
        settingDetails.setCreateDt(rs.getTimestamp("create_dt"));
        settingDetails.setEditedDt(rs.getTimestamp("edited_dt"));
        settingDetails.setCreatedBy(rs.getLong("created_by"));
        settingDetails.setEditedBy(rs.getLong("edited_by"));
        settingDetails.setParentId(rs.getLong("parent_id"));
        return settingDetails;
    }
}
