/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enums.DataState;
import enums.SettingTypeEnums;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author macbook
 */
public class SettingDetails {
    private Long id;
    private String settingName;
    private String settingValue;
    private SettingTypeEnums type;
    private Boolean isActive;
    private Timestamp createDt;
    private Timestamp editedDt;
    private Long createdBy;
    private Long editedBy;
    private Long parentId;
    private int displayOrder;

    private List<SettingDetails> permissions;

    public List<SettingDetails> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SettingDetails> permissions) {
        this.permissions = permissions;
    }

    public SettingDetails() {
    }

    
    
    public SettingDetails(Long id, String settingName, String settingValue, SettingTypeEnums type, Boolean isActive, Timestamp createDt, Timestamp editedDt, Long createdBy, Long editedBy, Long parentId) {
        this.id = id;
        this.settingName = settingName;
        this.settingValue = settingValue;
        this.type = type;
        this.isActive = isActive;
        this.createDt = createDt;
        this.editedDt = editedDt;
        this.createdBy = createdBy;
        this.editedBy = editedBy;
        this.parentId = parentId;
    }
    
    public SettingDetails(Long id, String settingName, String settingValue){
        this.settingName = settingName;
        this.settingValue = settingValue;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public Timestamp getEditedDt() {
        return editedDt;
    }

    public void setEditedDt(Timestamp editedDt) {
        this.editedDt = editedDt;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getIsActiveStr() {
        return isActive ? DataState.ACTIVE.getName() : DataState.INACTIVE.getName();
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
