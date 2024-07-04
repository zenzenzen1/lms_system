package entity;

/**
 *
 * @author macbook
 */
public class Attendances {
    private long id;
    private long studentId;
    private long schedulerId;
    private boolean attendanceStatus;
    private String attendanceNote;

    private Users student;
    private Scheduler scheduler;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public boolean isAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(boolean attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getAttendanceNote() {
        return attendanceNote;
    }

    public void setAttendanceNote(String attendanceNote) {
        this.attendanceNote = attendanceNote;
    }

    public Attendances(long id, long studentId, long schedulerId, boolean attendanceStatus, String attendanceNote) {
        this.id = id;
        this.studentId = studentId;
        this.schedulerId = schedulerId;
        this.attendanceStatus = attendanceStatus;
        this.attendanceNote = attendanceNote;
    }

    public Attendances() {
    }
}
