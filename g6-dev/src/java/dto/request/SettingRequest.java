package dto.request;

import entity.SettingDetails;
import enums.SettingTypeEnums;

import java.util.List;
import java.util.Map;

public class SettingRequest {
    private Long id;
    private String settingName;
    private String settingValue;
    private SettingTypeEnums type;
    private Boolean isActive;
    private Long createdBy;
    private Long editedBy;
    private List<SettingDetails> roles;
    private Map<String, Boolean> permissions;
    private Integer order;

    public SettingRequest() {
    }

    public SettingRequest(Long id, String settingName, String settingValue, SettingTypeEnums type, Boolean isActive, Long createdBy, Long editedBy, List<SettingDetails> roles, Integer order) {
        this.id = id;
        this.settingName = settingName;
        this.settingValue = settingValue;
        this.type = type;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.editedBy = editedBy;
        this.roles = roles;
        this.order = order;
    }

    public SettingRequest(Long id, String settingName, String settingValue, SettingTypeEnums type, Boolean isActive, Long createdBy, Long editedBy, Map<String, Boolean> permissionIds, Integer order) {
        this.id = id;
        this.settingName = settingName;
        this.settingValue = settingValue;
        this.type = type;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.editedBy = editedBy;
        this.permissions = permissionIds;
        this.order = order;
    }

    public List<SettingDetails> getRoles() {
        return roles;
    }

    public void setRoles(List<SettingDetails> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public SettingTypeEnums getType() {
        return type;
    }

    public void setType(SettingTypeEnums type) {
        this.type = type;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Long editedBy) {
        this.editedBy = editedBy;
    }


    public Integer getOrder() {
        return order;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
