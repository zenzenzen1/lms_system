import { DataTable } from 'primereact/datatable';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { setSchedulesAction } from '../../../../redux/action/ScheduleAction';
import { Column } from 'primereact/column';
import { getStudentIdByScheduleId } from '../../../../services/attendanceService';
import ScheduleDetail from './ScheduleDetail';
import { Button } from 'primereact/button';
import { ToastContainer } from 'react-toastify';
import { Dialog } from 'primereact/dialog';
import Scheduler from './Scheduler';
import type { FormikProps } from 'formik';

// eslint-disable-next-line react/prop-types
const ScheduleList = ({ schedules = [], _isScheduleList = true, _setIsScheduleList }) => {
    const { slots, subjects, rooms } = useSelector(state => state.schedule);
    // const [scheduleList, setScheduleList] = useState(useSelector(state => state.schedule.schedules))
    // const [scheduleList, setScheduleList] = useState(schedules);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        // const e = document.getElementById('schedule-table');
        // const t = e.getElementsByTagName('table')[0];
    });
    // (async () => {
    //     const res = await getSchedules();
    //     setSchedules(res.data.content);
    // })();
    useEffect(() => {
        // dispatch(setSchedulesAction());
        // schedules.map(async (value, index) => {
        //     const res = await getStudentIdByScheduleId(value.scheduleId);
        //     const students = res.data.map((value) => value.student);
        //     console.log(students);
        //     setScheduleList(schedule => schedule.map((value) => value.scheduleId === value.scheduleId ? { ...value, students } : { ...value }))
        // return {
        //     ...value,
        //     students: students
        // }
        // })
        // setSchedules(s);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [schedules])
    // console.log(schedules);
    const StudentsEnrolledTemplate = (e) => {
        return <>
            {
                e.students ? <Link >
                    {e.students && e.students.map(value => value.fullName)}
                </Link> : "???"
            }
        </>
    }

    const [scheduleSelected, setscheduleSelected] = useState(null);
    // const [isScheduleList, setIsScheduleList] = useState(_isScheduleList);
    const [isScheduleList, setIsScheduleList] = [_isScheduleList, _setIsScheduleList];
    schedules = schedules.map((value, index) => {
        return {
            ...value,
            index: <Link
                // to={"/user/admin/schedule-detail"} state={{ schedule: value }}
                onClick={(e) => {
                    console.log({ scheduleSelected, isScheduleList });
                    setscheduleSelected(value);
                    setIsScheduleList(false);
                    if (!isScheduleList) {
                        setIsScheduleList(true);
                        setscheduleSelected(null);
                    }
                }}>{index + 1}</Link>
        }
    })

    const [showDialog, setShowDialog] = useState(false);
    const formikRef = useRef<FormikProps<any>>(null);
    const handleSubmitFromParent = () => {
        formikRef.current?.submitForm();
    };
    return (
        // !isScheduleList && scheduleSelected ? <>
        //     <div>
        //         {/* {"<--"}<Link to={"/user/admin"} >
        //             {"Back"}
        //         </Link> */}
        //         <ScheduleDetail schedule={scheduleSelected} />
        //     </div>
        // </>
        //     :
        <>
            <ToastContainer />
            <div className='relative'>
                <Button label="Thêm mới" icon="pi pi-plus" severity="success" className=" btn-lg ml-2 w-28! px-2!" onClick={() => { setShowDialog(true) }} />
                <Button label="Excel" icon="pi pi-file-excel" className=" btn btn-success btn-lg ml-2 w-28!" />
                {/* <Button  className='absolute border-green-700 right-10 before:bg-green-50'>
                    <Link target='_self' to={user.roles.includes("ADMIN") && CONFIG.API_GATEWAY + API.EXPORT_TO_EXCEL_SCHEDULES} onClick={() => {
                    }}>
                        </Link>
                        Export to Excel
                </Button> */}
            </div>
            <DataTable value={schedules} tableStyle={{ minWidth: '50rem' }}
                className='text-sm'
                size='small'
                removableSort
            >
                <Column header="Index" body={""} field='index'></Column>
                <Column
                    // sortable 
                    header="Semester"
                    body={e => <>{`${e.course && e.course.semester.semesterCode}`}</>}
                // sortFunction={e => {
                //     return e.course.semester.semesterCode;
                // }}
                // field='semester'
                // sortField='semester'
                ></Column>
                <Column sortable field="courseName" header="Course Name" body={(e) => {
                    return (
                        <>
                            <Link to={"#"}>
                                {e.subject && `${e.subject.subjectCode} - ${e.subject.subjectName}`}
                            </Link>
                        </>)
                }}></Column>
                <Column header="Instructor" body={(e) => e.course.teacher.id} />
                <Column sortable field="trainingDate" body={(e) => {
                    return (<>{`${e.trainingDate.startDate} -> ${e.trainingDate.endDate}`}</>)
                }} header="Training Date" ></Column>
                <Column field="room" header="Room" body={(e) => { return (<>{e.room && `${e.room.roomNumber}`}</>) }}></Column>
                <Column sortable field="slot.slotId" header="Slot"
                    // sortFunction={e => { console.log(e); }}
                    body={(e) => { return (<>{e.slot && `${e.slot.slotId}: ${e.slot.startTime} -> ${e.slot.endTime}`}</>) }}
                ></Column>
                {/* <Column header="Students Enrolled" body={StudentsEnrolledTemplate} /> */}
                {/* <Column field="teacher" header="Teacher" body={(e) => { return (<>{`${e.teacher.firstName} ${e.teacher.lastName}`}</>) }}></Column> */}
                {/* <Column field="attendanceTaken" header="Attendance taken?"></Column> */}
            </DataTable>
            <Dialog visible={showDialog} onHide={() => { if (!showDialog) return; setShowDialog(false); }} header={() => {
                return (
                    <div className='flex justify-between items-center pr-5'>
                        <h2 className='mb-0'>Create Schedule</h2>
                        <Button
                            onClick={() => {
                                // fileRef.current.click();
                                // importFromExcel();
                            }}
                            className='btn btn-primary btn-lg'>Import from excel</Button>
                    </div>
                )
            }} className="w-[80vw] h-[92vh]" position='top' maximizable
                footer={
                    <div className='flex justify-end'>
                        <Button label="Cancel" icon="pi pi-times" severity="secondary" onClick={() => setShowDialog(false)} />
                        <Button label="Save" icon="pi pi-check" severity="success" onClick={() => {
                            handleSubmitFromParent();
                            
                        }} />
                    </div>
                }
            >
                <Scheduler formikRef={formikRef} />

            </Dialog>
        </>
    )
}

export default ScheduleList
