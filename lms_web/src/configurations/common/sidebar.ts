// @ts-nocheck

import { SideBarType } from "src/types/type"

import UserList from "../../components/main/user/admin/UserList";
import { urlRolePrefix } from "./navigate";
import ScheduleList from "src/components/main/user/admin/ScheduleList";
import Schedule from "src/components/main/user/student/Schedule";
import Scheduler from "src/components/main/user/teacher/Scheduler";
import AttendanceStatus from "src/components/main/user/student/AttendanceStatus";

export const adminSidebarTabs: SideBarType[] = [
    {
        name: "Show all users",
        icon: "fa fa-users",
        componentName: "user-management-account-list",
        linkUrl: urlRolePrefix.admin + "/user-management/account-list"
    },
    {
        name: "Schedule",
        icon: "fa fa-calendar",
        componentName: "schedule",
        linkUrl: urlRolePrefix.admin + "/schedule"
    }
]

export const studentSidebarTabs: SideBarType[] = [
    {
        name: "Schedule",
        icon: "fa fa-calendar",
        componentName: "schedule",
        linkUrl: urlRolePrefix.student
    },
    {
        name: "Attendance Status",
        icon: "fa fa-check-circle",
        componentName: "student-attendance-status",
        linkUrl: urlRolePrefix.student + "/attendance-status"
    }
]

export const TeacherSidebarTabs: SideBarType[] = [
    {
        name: "Schedule",
        icon: "fa fa-calendar",
        componentName: "schedule",
        linkUrl: urlRolePrefix.teacher + ""
    }
]

export const sidebarComponents = {
    adminSidebarComponents: {
        "user-management-account-list": UserList,
        "schedule": ScheduleList
    },
    studentSidebarComponents: {
        "schedule": Schedule,
        "student-attendance-status": AttendanceStatus
    },
    teacherSidebarComponents: {
        "schedule": Scheduler
    }
}