import React, { MouseEvent, useEffect, useMemo, useRef, useState } from 'react'
import { useSelector } from 'react-redux'
import { getCoursesByStudentIdSemesterCode } from '../../../../services/courseService';
import { getAttendanceByCourseIdStudentId, getAttendancesByCourseId } from '../../../../services/attendanceService';
import { Table } from 'react-bootstrap';
import { getAllRooms, getAllSlots, getAllSubjects, getSemesters } from 'src/services/ScheduleService';
import { AttendanceType, CourseType, RoomType, SemesterType, SlotType } from 'src/types/type';
import { RootStateType } from 'src/redux/store';
import { orderBy } from 'lodash';

const AttendanceStatus = () => {
    const [slots, setSlots] = useState<SlotType[]>([]);
    const [rooms, setRooms] = useState<RoomType[]>([]);
    const [semesters, setSemesters] = useState<SemesterType[]>([]);
    const [semesterCode, setSemesterCode] = useState<string>();
    const [attendances, setAttendances] = useState<AttendanceType[]>([]);
    const [courses, setCourses] = useState<CourseType[]>([]);
    // const [numberOfAbsent, setNumberOfAbsent] = useState(0);
    const numberOfAbsent = useMemo(() => {
        let count = 0;
        attendances && attendances.forEach((attendance) => {
            if (attendance.attendanceStatus !== null && !attendance.attendanceStatus)
                count++;
        })
        return count;
    }, [attendances]);

    const user = useSelector((state: RootStateType) => state.user);

    useEffect(() => {
        (async () => {
            try {
                const [_slots, _rooms] = await Promise.all([getAllSlots(), getAllRooms()]);
                setSlots(_slots.data.result);
                setRooms(_rooms.data.result);
                const res = await getSemesters();
                const semesters = orderBy(res.data, ['startDate'], ['asc']);
                setSemesters(semesters);
                setSemesterCode(semesters[semesters.length - 1].semesterCode);
            } catch (error) {
                console.log(error);
            }
        })();
    }, []);

    useMemo(async () => {
        if(!semesterCode || !user) return;
        
        const res = await getCoursesByStudentIdSemesterCode(user.userProfile.id, semesterCode);
        console.log(res);
        setCourses(res.result);

        // const attendaceRes = await getAttendancesByCourseId(res.data[0].courseId, user.id);
        // setAttendances(attendaceRes.data);
    }, [semesterCode, user])

    const handleChangeSemester = (_semesterCode: string) => {
        if (_semesterCode != semesterCode) {
            setSemesterCode(_semesterCode);
            setAttendances([]);
        }
    }
    console.log({ semesters, user, semesterCode, courses, attendances, numberOfAbsent });

    const handleChangeCourse = async (courseId: number) => {
        console.log(courseId);
        // if (attendances[0] && +courseId === +attendances[0].schedule.scheduleId)
        //     return;
        const res = await getAttendanceByCourseIdStudentId(courseId, user.userProfile.id);
        console.log(res);
        setAttendances(res.result);
    }
    console.log(numberOfAbsent);

    return (
        <div className='row'>
            {/* <select value={semesterCode}
                onChange={(e) => setSemesterCode(e.target.value)}
            >
                {semesters.map((semester, index) => {
                    return <option key={index} value={semester.semesterCode}
                    >
                        {semester.semesterCode}
                    </option>
                })}
            </select> */}
            <div className='col-md-3'>
                <ul className='pl-0'>
                    {semesters && semesters.map((semester, index) => {
                        return (
                            semesterCode === semester.semesterCode
                                ? (<li key={index} className={semesterCode === semester.semesterCode ? 'text-blue-500' : ''} id={semester.semesterCode}
                                    onClick={() => handleChangeSemester(semester.semesterCode)}
                                >
                                    {"--> " + semester.semesterCode}
                                </li>)
                                : <li key={index} className='cursor-pointer' id={semester.semesterCode}
                                    onClick={() => handleChangeSemester(semester.semesterCode)}
                                >
                                    {semester.semesterCode}
                                </li>
                        )
                    })}
                </ul>
                <ul className='pl-1'>
                    {courses && courses.length === 0 ? <span>No data</span> : courses && courses.map((course, index) => {
                        return (
                            <li className={`cursor-pointer w-fit `} key={index} id={course.courseId.toString()}
                                onClick={_e => handleChangeCourse(course.courseId)}
                            >
                                {course.subject.subjectCode} - {course.subject.subjectName}
                            </li>
                        )
                    })}
                </ul>
            </div>

            <div className='col-md-9'>
                {attendances && attendances.length !== 0 &&
                    <div>
                        <h4>Total: {attendances.length} results. Absent: {numberOfAbsent}/{attendances.length} <span className='text-red-400'>{`(${(numberOfAbsent / attendances.length * 100).toFixed(2)}%)`}</span></h4>
                        <Table hover>
                            <thead>
                                <tr>
                                    <th>Index</th>
                                    <th>Training Date</th>
                                    <th>Attendance Status</th>
                                    <th>Attendance Note</th>
                                </tr>
                            </thead>
                            <tbody>
                                {<>
                                    {attendances.map((a, index) => {
                                        return (
                                            <tr key={index}>
                                                <td>
                                                    {index + 1}
                                                </td>
                                                <td>
                                                    {`${a.schedule.trainingDate}`}
                                                </td>
                                                <td>
                                                    {a.attendanceStatus === null ? <span>null</span> : <span>{a.attendanceStatus ? "Attend" : "Absent"}</span>}
                                                </td>
                                                <td>
                                                    {a.attendanceNote}
                                                </td>
                                            </tr>)
                                    }
                                    )}

                                </>}
                            </tbody>
                        </Table>
                    </div>}
            </div>
        </div>
    )
}

export default AttendanceStatus
