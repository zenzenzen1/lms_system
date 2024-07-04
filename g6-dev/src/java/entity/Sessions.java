/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author macbook
 */
public class Sessions {
    private Long id;
    private String topicName;
    private int duration;
    private Long subjectId;
    private boolean isActive;

    private Subjects subjects;

    public Sessions(Long id, String topicName, int duration, Long subjectId, boolean isActive) {
        this.id = id;
        this.topicName = topicName;
        this.duration = duration;
        this.subjectId = subjectId;
        this.isActive = isActive;
    }

    public Sessions() {
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
