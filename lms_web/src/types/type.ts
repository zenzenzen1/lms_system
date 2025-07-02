import { sidebarComponents } from "src/configurations/common/sidebar";

export type SideBarTabs = keyof typeof sidebarComponents.adminSidebarComponents;

export type SideBarType = {
    name: string;
    icon?: string;
    componentName: string;
    linkUrl: string;
    children?: SideBarType[];
}

export type ApiResponseType<T,> = {
    code: number;
    result: T;
    message: string;
}

export type AttendanceType = {
    "attendanceId": number,
    "attendanceStatus"?: boolean | undefined,
    "attendanceNote"?: string,
    student: StudentType,
    schedule: {
        scheduleId: number;
        trainingDate: string;
    }
}

export type SlotType = {
    slotId: number;
    startTime: string;
    endTime: string;
}

export type RoomType = {
    roomId: number;
    roomNumber: string;
}

export type SubjectType = {
    subjectCode: string;
    subjectName: string;
    status: boolean;
}

export type CourseType = {
    "courseId": number,
    "subject": SubjectType;
    "semester": SemesterType;
    "teacher": UserType;
}

export type SemesterType = {
    "semesterCode": string,
    "startDate": string,
    "endDate": string,
    "description"?: string
}
export type UserResponseType = {

    id: string
    roles: string[]
    userProfile: {
        dob: string
        email: string
        fullName: string
        id: string
        phoneNumber: string | null
        userId: string
        username: string
    }
}

export type UserType = {
    "id": string,
    "userId": string,
    "fullName": string,
    "email": string,
    "phoneNumber"?: string,
    "dob"?: string,
}


export type StudentType = {
    active: boolean;
    dob?: string;
    email: string;
    fullName: string;
    id: string;
    phoneNumber?: string;
    roles: any[];
    userId: string;
    username: string;
}