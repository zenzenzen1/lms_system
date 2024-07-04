package dto.response;

import entity.*;

import java.sql.Date;
import java.util.List;

public class SchedulerResponse {
    private Long id;
    private Date trainingDate;
    private boolean attendance_taken;

    private Users teacher;
    private SettingDetails room;
    private SettingDetails slots;
    private Sessions sessions;
    private Course course;

    private List<Users> teacherList;
    private List<SettingDetails> roomList;
    private List<SettingDetails> slotList;
    private List<Sessions> sessionsList;
    private List<Course> courseList;

    public SchedulerResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
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
        return slots;
    }

    public void setSlot(SettingDetails slots) {
        this.slots = slots;
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

    public List<Users> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Users> teacherList) {
        this.teacherList = teacherList;
    }

    public List<SettingDetails> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<SettingDetails> roomList) {
        this.roomList = roomList;
    }

    public List<SettingDetails> getSlotList() {
        return slotList;
    }

    public void setSlotList(List<SettingDetails> slotList) {
        this.slotList = slotList;
    }

    public List<Sessions> getSessionsList() {
        return sessionsList;
    }

    public void setSessionsList(List<Sessions> sessionsList) {
        this.sessionsList = sessionsList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public static SchedulerResponse mapSchedulerToSchedulerResponse(Scheduler classOne) {
        SchedulerResponse classTwo = new SchedulerResponse();

        classTwo.setId(classOne.getId());
        classTwo.setTrainingDate(classOne.getTraiining_date());
        classTwo.setAttendance_taken(classOne.isAttendance_taken());
        classTwo.setTeacher(classOne.getTeacher());
        classTwo.setRoom(classOne.getRoom());
        classTwo.setSlot(classOne.getSlot());
        classTwo.setSessions(classOne.getSessions());
        classTwo.setCourse(classOne.getCourse());

        return classTwo;
    }
}
