
package entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author macbook
 */
public class Users {
    private Long id;
    private String username;
    private String userCode;
    private String passwordHash;
    private String name;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String address;
    private String description;
    private Boolean isActive;
    private Timestamp createDt;
    private Long authorId;
    private Long roleId;
    
    private List<Announcements> lmsAnnouncementsById;
    private SettingDetails lmsRolesByRoleId;

    public Users() {
    }

    public Users(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isActive = true;
    }
    
    public Users(String username, String name, String passwordHash, String email, String phone) {
        this.username = username;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isActive = true;
        this.phone = phone;
    }
    
    
    public Users(Long id, String username, String userCode, String passwordHash, String name, Date dateOfBirth, String email, String phone, String address, String description, Boolean isActive, Timestamp createDt, Long authorId, Long roleId) {
        this.id = id;
        this.username = username;
        this.userCode = userCode;
        this.passwordHash = passwordHash;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.isActive = isActive;
        this.createDt = createDt;
        this.authorId = authorId;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Announcements> getLmsAnnouncementsById() {
        return lmsAnnouncementsById;
    }

    public void setLmsAnnouncementsById(List<Announcements> lmsAnnouncementsById) {
        this.lmsAnnouncementsById = lmsAnnouncementsById;
    }

    public SettingDetails getLmsRolesByRoleId() {
        return lmsRolesByRoleId;
    }

    public void setLmsRolesByRoleId(SettingDetails lmsRolesByRoleId) {
        this.lmsRolesByRoleId = lmsRolesByRoleId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.username);
        hash = 11 * hash + Objects.hashCode(this.userCode);
        hash = 11 * hash + Objects.hashCode(this.passwordHash);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 11 * hash + Objects.hashCode(this.email);
        hash = 11 * hash + Objects.hashCode(this.phone);
        hash = 11 * hash + Objects.hashCode(this.address);
        hash = 11 * hash + Objects.hashCode(this.description);
        hash = 11 * hash + Objects.hashCode(this.isActive);
        hash = 11 * hash + Objects.hashCode(this.createDt);
        hash = 11 * hash + Objects.hashCode(this.authorId);
        hash = 11 * hash + Objects.hashCode(this.roleId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Users other = (Users) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.userCode, other.userCode)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        if (!Objects.equals(this.isActive, other.isActive)) {
            return false;
        }
        if (!Objects.equals(this.createDt, other.createDt)) {
            return false;
        }
        if (!Objects.equals(this.authorId, other.authorId)) {
            return false;
        }
        return Objects.equals(this.roleId, other.roleId);
    }

    
}
