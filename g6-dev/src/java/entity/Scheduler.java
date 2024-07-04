package entity;

import java.sql.Date;

public class Scheduler {
    private Long id;
    private Long courseId;
    private Long sessionsId;
    private Date traiining_date;
    private Long slotId;
    private Long roomId;
    private Long teacherId;
    private boolean attendance_taken;

    private Users teacher;
    private SettingDetails room;
    private SettingDetails slot;
    private Sessions sessions;
    private Course course;

    public Scheduler() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSessionsId() {
        return sessionsId;
    }

    public void setSessionsId(Long sessionsId) {
        this.sessionsId = sessionsId;
    }

    public Date getTraiining_date() {
        return traiining_date;
    }

    public void setTraiining_date(Date traiining_date) {
        this.traiining_date = traiining_date;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public boolean isAttendance_taken() {
        return attendance_taken;
    }

    public void setAttendance_taken(boolean attendance_taken) {
        this.attendance_taken = attendance_taken;
    }

    public Users getTeacher() {
        return teacher;
    }

    public void setTeacher(Users teacher) {
        this.teacher = teacher;
    }

    public SettingDetails getRoom() {
        return room;
    }

    public void setRoom(SettingDetails room) {
        this.room = room;
    }

    public SettingDetails getSlot() {
        return slot;
    }

    public void setSlot(SettingDetails slot) {
        this.slot = slot;
    }

    public Sessions getSessions() {
        return sessions;
    }

    public void setSessions(Sessions sessions) {
        this.sessions = sessions;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
