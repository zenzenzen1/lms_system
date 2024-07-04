package dao;

import entity.Scheduler;
import entity.SettingDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchedulerDAO extends BaseDAO {
    protected static final String TABLE_NAME = "lms_scheduler";

    public Long upsertScheduler(Scheduler scheduler) {
        String sqlQuery;

        if (scheduler.getId() != null) {
            sqlQuery = "UPDATE " + TABLE_NAME + " SET slot_id = ?, room_id = ?, day = ?, session_id = ?, attendance_taken = ?, teacher_id = ?, course_id = ? WHERE scheduler_id = ?";
        } else {
            sqlQuery = "INSERT INTO " + TABLE_NAME + " (slot_id, room_id, day, session_id, attendance_taken, teacher_id, course_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            if (scheduler.getId() != null) {
                ps.setLong(8, scheduler.getId());
            }

            ps.setLong(1, scheduler.getSlotId());
            ps.setLong(2, scheduler.getRoomId());
            ps.setDate(3, scheduler.getTraiining_date());
            ps.setLong(4, scheduler.getSessionsId());
            ps.setBoolean(5, scheduler.isAttendance_taken());
            ps.setLong(6, scheduler.getTeacherId());
            ps.setLong(7, scheduler.getCourseId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating or updating scheduler failed, no rows affected.");
            }

            if (scheduler.getId() == null) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating scheduler failed, no ID obtained.");
                    }
                }
            }

            return (long) affectedRows;

        } catch (SQLException e) {
        }

        return -1L;
    }

    public Scheduler findById(Long id) {
        String query = "SELECT lsch.*\n" +
                "     , lsd_slot.setting_value as slot\n" +
                "     , lsd_room.setting_value as room\n" +
                "     , lco.*\n" +
                "     , lse.*\n" +
                "     , lus.* FROM " + TABLE_NAME + " lsch" +
                " left join lms_setting_details lsd_slot on lsd_slot.setting_id = lsch.slot_id\n" +
                "         left join lms_setting_details lsd_room on lsd_room.setting_id = lsch.room_id\n" +
                "         left join lms_course lco on lco.course_id = lsch.course_id\n" +
                "         left join lms_session lse on lse.session_id = lsch.session_id\n" +
                "         left join lms_users lus on lus.user_id = lsch.teacher_id " +
                " WHERE scheduler_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractScheduler(rs);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Scheduler> getSchedulesByDate(Date start, Date end) throws SQLException {
        String query = "SELECT lsch.*\n" +
                "     , lsd_slot.setting_value as slot\n" +
                "     , lsd_room.setting_value as room\n" +
                "     , lco.*\n" +
                "     , lse.*\n" +
                "     , lus.* FROM " + TABLE_NAME + " lsch" +
                " left join lms_setting_details lsd_slot on lsd_slot.setting_id = lsch.slot_id\n" +
                "         left join lms_setting_details lsd_room on lsd_room.setting_id = lsch.room_id\n" +
                "         left join lms_course lco on lco.course_id = lsch.course_id\n" +
                "         left join lms_session lse on lse.session_id = lsch.session_id\n" +
                "         left join lms_users lus on lus.user_id = lsch.teacher_id " +
                "where day >= ? and day <= ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, start);
            stmt.setDate(2, end);
            ResultSet rs = stmt.executeQuery();
            List<Scheduler> schedulerList = new ArrayList<>();
            while (rs.next()) {
                Scheduler scheduler = extractScheduler(rs);
                schedulerList.add(scheduler);
            }
            return schedulerList;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Scheduler> getAllSchedulers(String order, String direction) {
        String query = "SELECT lsch.*\n" +
                "     , lsd_slot.setting_value as slot\n" +
                "     , lsd_room.setting_value as room\n" +
                "     , lco.*\n" +
                "     , lse.*\n" +
                "     , lus.* FROM " + TABLE_NAME + " lsch" +
                " left join lms_setting_details lsd_slot on lsd_slot.setting_id = lsch.slot_id\n" +
                "         left join lms_setting_details lsd_room on lsd_room.setting_id = lsch.room_id\n" +
                "         left join lms_course lco on lco.course_id = lsch.course_id\n" +
                "         left join lms_session lse on lse.session_id = lsch.session_id\n" +
                "         left join lms_users lus on lus.user_id = lsch.teacher_id " +
                " order by ? "+ ((direction == null) ? "" : direction );
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, order);
            ResultSet rs = stmt.executeQuery();
            List<Scheduler> schedulerList = new ArrayList<>();
            while (rs.next()) {
                Scheduler scheduler = extractScheduler(rs);
                schedulerList.add(scheduler);
            }
            return schedulerList;
        } catch (SQLException e) {
        }
        return null;
    }

    static Scheduler extractScheduler(ResultSet rs) throws SQLException {
        Scheduler scheduler = new Scheduler();
        scheduler.setId(rs.getLong("scheduler_id"));
        scheduler.setSlotId(rs.getLong("slot_id"));
        scheduler.setRoomId(rs.getLong("room_id"));
        scheduler.setTraiining_date(rs.getDate("day"));
        scheduler.setSessionsId(rs.getLong("session_id"));
        scheduler.setAttendance_taken(rs.getBoolean("attendance_taken"));
        scheduler.setTeacherId(rs.getLong("teacher_id"));
        scheduler.setCourseId(rs.getLong("course_id"));

        scheduler.setCourse(CourseDAO.extractCourse(rs));
        scheduler.setSessions(SessionsDAO.extractSession(rs));
        scheduler.setTeacher(UsersDAO.extractUser(rs));

        SettingDetails slot = new SettingDetails();
        slot.setId(rs.getLong("slot_id"));
        slot.setSettingValue("slot");

        SettingDetails room = new SettingDetails();
        room.setId(rs.getLong("room_id"));
        room.setSettingValue("room");

        scheduler.setSlot(slot);
        scheduler.setRoom(room);

        return scheduler;
    }

    public int countScheduler() {
        String query = "SELECT count(*) from " + TABLE_NAME;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
        }
        return 0;
    }
}