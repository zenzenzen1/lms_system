package dao;

import com.mysql.cj.util.StringUtils;
import entity.SettingDetails;
import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDAO extends BaseDAO {

    public boolean existUserWithAssignRole(Long roleId) {
        String sql = "select 1 "
                + "from lms_users u "
                + "where exists(select 1 "
                + "             from lms_setting_details lsd "
                + "             where lsd.setting_id = u.role_id) and u.is_active = 1 and u.user_id = ?";

        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
        }
        return false;
    }

    public List<Users> findAll() {
        List<Users> userList = new ArrayList<>();
        String sql = "SELECT u.*, r.setting_name, r.setting_value from lms_users u "
                + "join lms_setting_details r on r.setting_id = u.role_id";
        try ( Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users user = extractUser(rs);
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
            return null;
        }
        return userList;
    }

    public List<Users> findAll(int page, int pageSize, Map<String, String> filters) {
        List<Users> userList = new ArrayList<>();
        StringBuilder sql =  new StringBuilder("select u.*, r.setting_name, r.setting_value from lms_users u ");
        sql.append("join lms_setting_details r on r.setting_id = u.role_id where 1 = 1");
        if(filters != null && !filters.isEmpty()){
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if(!StringUtils.isNullOrEmpty(entry.getValue()) && !"-1".equals(entry.getValue())){
                    if(entry.getKey().toLowerCase().contains("_id")){
                        sql.append(" and ".concat(entry.getKey())
                                .concat(" = ").concat(entry.getValue()));
                    }else{
                        sql.append(" and ".concat(entry.getKey())
                                .concat(" like '%").concat(entry.getValue()).concat("%'"));
                    }

                }
            }
        }
        sql.append(" limit ?, ?");
        try ( Connection connection = getConnection();
              PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, Math.max((page - 1) * pageSize, 0));
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users user = extractUser(rs);
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql.toString());
            return null;
        }
        return userList;
    }

    public int count(Map<String, String> filters) {
        StringBuilder sql =  new StringBuilder("select count(*) from lms_users u");
        sql.append(" join lms_setting_details r on r.setting_id = u.role_id where 1 = 1");
        if(filters != null && !filters.isEmpty()){
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if(!StringUtils.isNullOrEmpty(entry.getValue()) && !"-1".equals(entry.getValue())){
                    if(entry.getKey().toLowerCase().contains("_id")){
                        sql.append(" and ".concat(entry.getKey())
                                .concat(" = ").concat(entry.getValue()));
                    }else{
                        sql.append(" and ".concat(entry.getKey())
                                .concat(" like '%").concat(entry.getValue()).concat("%'"));
                    }

                }
            }
        }
        try ( Connection connection = getConnection();
              PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql.toString());
        }
        return 0;
    }

    public Users findUserById(Long id) {
        String sql = "SELECT u.*, r.setting_name, r.setting_value from lms_users u "
                + "join lms_setting_details r on r.setting_id = u.role_id where u.user_id = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    public List<Users> findUserByRoleName(String roleName) {
        List<Users> userList = new ArrayList<>();

        String sql = "SELECT u.*, r.setting_name, r.setting_value from lms_users u "
                + "join lms_setting_details r on r.setting_id = u.role_id where r.setting_name = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, roleName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users user = extractUser(rs);
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
            return null;
        }

        return userList;
    }

    public Users findByUsername(String userName) {
        Users user = null;

        String sql = "SELECT u.*, r.setting_name, r.setting_value from lms_users u "
                + "join lms_setting_details r on r.setting_id = u.role_id where u.username = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
        }

        return user;
    }

    public boolean isEmailOrUserExisted(String username, String email) {
        String sql = "select 1 "
                + "from lms_users "
                + "where exists(select 1 "
                + "             from lms_users "
                + "             where username = "
                + "                   ? "
                + "                or email = ?) limit 1";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
        }
        return false;
    }

    public void updateUserStateByIds(List<Long> ids, boolean state) {
        String sql = "UPDATE lms_users SET is_active = ? WHERE user_id IN (?)";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Long id : ids) {
                ps.setBoolean(1, state);
                ps.setLong(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
        }
    }

    public Long upsertUser(Users user) {
        String sql;
        if (user.getId() != null) {
            sql = "UPDATE lms_users SET username = ?, user_code = ?, password_hash = ?, name = ?, "
                    + "date_of_birth = ?, email = ?, phone = ?, address = ?, description = ?, is_active = ?, role_id = ? "
                    + "WHERE user_id = ?";
        } else {
            sql = "INSERT INTO lms_users "
                    + "(username, user_code, password_hash, name, date_of_birth, email, phone, address,"
                    + "description, is_active, role_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUserCode());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getName());
            ps.setDate(5, user.getDateOfBirth());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getAddress());
            ps.setString(9, user.getDescription());
            ps.setBoolean(10, user.getIsActive());
            ps.setLong(11, user.getRoleId());
            if (user.getId() != null) {
                ps.setLong(11, user.getId());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
        }
        return -1L;
    }

    public int delete(Long userId) {
        String sql = "delete from lms_users where user_id = ?";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, sql);
        }
        return -1;
    }

    static Users extractUser(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setUserCode(rs.getString("user_code"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setName(rs.getString("name"));
        user.setDateOfBirth(rs.getDate("date_of_birth"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setDescription(rs.getString("description"));
        user.setIsActive(rs.getBoolean("is_active"));
        user.setCreateDt(rs.getTimestamp("create_dt"));
        user.setRoleId(rs.getLong("role_id"));
        // Get Role of User
        SettingDetails stDetail = new SettingDetails(
                user.getRoleId(),
                rs.getString("setting_name"),
                rs.getString("setting_value")
        );
        user.setLmsRolesByRoleId(stDetail);
        return user;
    }


}
