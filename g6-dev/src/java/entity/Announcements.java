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
public class Announcements {
    private Long id;
    private String title;
    private String content;
    private String tags;
    private Timestamp expireDate;
    private Timestamp startDate;
    private Boolean isActive;
    private Timestamp createDt;
    private Long authorId;
    private Long audienceId;
    private Long imageId;
    
    private Users usersByAuthorId;
    private Files imageByImageId;
    private Users usersByAudienceId;

    public Announcements(Long id, String title, String content, String tags, Timestamp expireDate, Timestamp startDate, Boolean isActive, Timestamp createDt, Long authorId, Long audienceId, Long imageId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.expireDate = expireDate;
        this.startDate = startDate;
        this.isActive = isActive;
        this.createDt = createDt;
        this.authorId = authorId;
        this.audienceId = audienceId;
        this.imageId = imageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
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

    public Long getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(Long audienceId) {
        this.audienceId = audienceId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.title);
        hash = 13 * hash + Objects.hashCode(this.content);
        hash = 13 * hash + Objects.hashCode(this.tags);
        hash = 13 * hash + Objects.hashCode(this.expireDate);
        hash = 13 * hash + Objects.hashCode(this.startDate);
        hash = 13 * hash + Objects.hashCode(this.isActive);
        hash = 13 * hash + Objects.hashCode(this.createDt);
        hash = 13 * hash + Objects.hashCode(this.authorId);
        hash = 13 * hash + Objects.hashCode(this.audienceId);
        hash = 13 * hash + Objects.hashCode(this.imageId);
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
        final Announcements other = (Announcements) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.tags, other.tags)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.expireDate, other.expireDate)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
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
        if (!Objects.equals(this.audienceId, other.audienceId)) {
            return false;
        }
        return Objects.equals(this.imageId, other.imageId);
    }

    
}
