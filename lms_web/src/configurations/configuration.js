export const CONFIG = {
  API_GATEWAY: "http://localhost:8090/api/v1",
};

export const API = {
  LOGIN: "/identity/auth/token",
  MY_INFO: "/identity/users/userInfo",
  INTROSPECT: "/identity/auth/introspect",
  REGISTER: "/identity/users/registration",
  ALL_USER: "/identity/users",
  USER_PROFILE: "/lms/users",
  SLOTS: "/lms/slots",
  SUBJECTS: "/lms/subjects",
  ROOM: "/lms/rooms",
  ALL_SCHEDULE: "/lms/schedules/all",
  ALL_SEMESTERS: "/lms/semesters/all",
  SCHEDULE: "/lms/schedules",
  SCHEDULE_BY_STUDENTID: "/lms/schedules/studentId",
  SCHEDULE_BY_TEACHERID: "/lms/attendances/teacherId",
  STUDENTS_BY_SCHEDULEID: "/lms/attendances/scheduleId",
  SAVE_ALL_ATTENDANCES: "/lms/attendances/saveAll"
};

export const days = ["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"];