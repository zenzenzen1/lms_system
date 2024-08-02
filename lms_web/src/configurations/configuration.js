export const CONFIG = {
  API_GATEWAY: "http://localhost:8090/api/v1",
};

const lms_prefix = "/lms";

export const API = {
  LOGIN: "/identity/auth/token",
  GOOGLE_LOGIN: "/identity/auth/google",
  MY_INFO: "/identity/users/userInfo",
  INTROSPECT: "/identity/auth/introspect",
  REGISTER: "/identity/users/registration",
  ALL_USER: "/identity/users",
  USER_PROFILE: lms_prefix + "/users",
  SLOTS: lms_prefix + "/slots",
  SUBJECTS: lms_prefix + "/subjects",
  ROOM: lms_prefix + "/rooms",
  ALL_SCHEDULE: lms_prefix + "/schedules/all",
  ALL_ATTENDANCES: lms_prefix + "/attendances/all",
  ALL_SEMESTERS: lms_prefix + "/semesters/all",
  SCHEDULE: lms_prefix + "/schedules",
  SCHEDULE_BY_STUDENTID: lms_prefix + "/schedules/studentId",
  ATTENDANCES_BY_STUDENTID: lms_prefix + "/attendances/studentId",
  ATTENDANCES_BY_TEACHERID: lms_prefix + "/attendances/teacherId",
  STUDENTS_BY_SCHEDULEID: lms_prefix + "/attendances/scheduleId",
  // ATTENDANCES_BY_SCHEDULEID: lms_prefix + "/attendances/scheduleId",
  GET_ATTENDANCES_BY_COURSEID_STUDENTID_SLOTID: lms_prefix + "/attendances/courseId/studentId/slotId",
  GET_SCHEDULES_BY_COURSEID_SLOTID_ROOMID_SEMESTERCODE: lms_prefix + "/schedules/courseId/slotId/roomId/semesterCode",
  GET_COURSES_BY_STUDENTID_SEMESTERCODE: lms_prefix + "/courses/studentId/semesterCode",
  GET_COURSES_BY_SEMESTER: lms_prefix + "/courses/semesterCode",
  SAVE_ALL_ATTENDANCES: lms_prefix + "/attendances/saveAll",
  GET_NOTIFICATIONS_BY_USER_ID: "/notification/getNotificationsByUserId"
};

export const days = ["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"];

export const years = [2021, 2022, 2023, 2024, 2025, 2026];

export const checkTokenInterval = 5000;