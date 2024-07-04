/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author macbook
 */
public class Subjects {
    private Long id;
    private String name;
    private String description;
    private Boolean isOnline;
    private Boolean isActive;
    private Timestamp createDt;
    private Long categoryId;

    private SettingDetails category;

    public SettingDetails getCategory() {
        return category;
    }

    public void setCategory(SettingDetails category) {
        this.category = category;
    }

    public Subjects() {
    }

    public Subjects(Long id, String name, String description, Boolean isOnline, Boolean isActive, Timestamp createDt, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isOnline = isOnline;
        this.isActive = isActive;
        this.createDt = createDt;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
