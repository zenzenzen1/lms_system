/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author macbook
 */
public class Files {
    private Long id;
    
    private Long objectId;
    
    private Integer type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Files files = (Files) o;
        return Objects.equals(getId(), files.getId()) && Objects.equals(getObjectId(), files.getObjectId()) && Objects.equals(getType(), files.getType()) && Objects.equals(getUrl(), files.getUrl()) && Objects.equals(getFileName(), files.getFileName()) && Objects.equals(getIsActive(), files.getIsActive()) && Objects.equals(getCreateDt(), files.getCreateDt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getObjectId(), getType(), getUrl(), getFileName(), getIsActive(), getCreateDt());
    }

    private String url;

    private String fileName;
    
    private Boolean isActive;
    
    private Timestamp createDt;

    public Files(Long id, Long objectId, Integer type, String url, String fileName, Boolean isActive, Timestamp createDt) {
        this.id = id;
        this.objectId = objectId;
        this.type = type;
        this.url = url;
        this.fileName = fileName;
        this.isActive = isActive;
        this.createDt = createDt;
    }

    public Files() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
