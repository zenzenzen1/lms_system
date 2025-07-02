import React, { useEffect, useMemo, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { setSchedulesAction, setSemestersAction } from '../../../../redux/action/ScheduleAction';
import { setRooms, setSlots, setSubjects } from '../../../../redux/slice/ScheduleSlice';
import { exportToExcelSchedules, getAllRooms, getAllSlots, getAllSubjects, getSchedules, getSemesters, importFromExcel, saveSchedule } from '../../../../services/ScheduleService';
import { getAllUserAndUserProfile } from '../../../../services/UserService';
import ScheduleList from './ScheduleList';
import { Toast } from 'primereact/toast';
import { Button, Button as PrimereactButton } from 'primereact/button';
import { getCoursesBySemester } from '../../../../services/courseService';
import { getStudentIdByScheduleId } from '../../../../services/attendanceService';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { API, CONFIG } from '../../../../configurations/configuration';
import { toast } from 'react-toastify';
import { RootStateType, RootType } from 'src/redux/store';
import { headerClassName } from 'src/configurations/common/datatable';
import { SelectField } from 'src/components/common/form/FormComponent';
import { Form, Formik, FormikProps, useField } from 'formik';
import { CourseType, RoomType, SemesterType, SlotType, StudentType } from 'src/types/type';
import * as Yup from 'yup';

export type ScheduleRequestType = {
    semesterCode: string;
    slotId: string | number;
    courseId: string | number;
    roomId: string | number;
    students: StudentType[];
}

const validationSchema = Yup.object().shape({
    semesterCode: Yup.string().required("Semester is required"),
    slotId: Yup.string().required("Slot is required"),
    courseId: Yup.string().required("Course is required"),
    roomId: Yup.string().required("Room is required"),
    students: Yup.array().min(1, "At least one student is required").required("Students are required")
});
interface ChildFormProps {
    formikRef: React.RefObject<FormikProps<ScheduleRequestType>>;
}
// eslint-disable-next-line react/prop-types
const Scheduler = ({ formikRef }: ChildFormProps) => {
    const [show, setShow] = useState(false);
    const [slots, setSlots] = useState<SlotType[]>([]);
    const [subjects, setSubjects] = useState([]);
    const [rooms, setRooms] = useState<RoomType[]>([]);
    const [semesters, setSemesters] = useState<SemesterType[]>([]);
    const [schedule, setSchedule] = useState([]);
    const [schedulesDisplayed, setScheduleDisplayed] = useState([]);
    const [error, setError] = useState("");
    const [students, setStudents] = useState([]);
    const [courses, setCourses] = useState<CourseType[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [selectedStudents, setSelectedStudents] = useState<StudentType[]>([]);
    useEffect(() => {
        const e = document.querySelector(".schedule-modal>div>table");
        if (e && !e.classList.contains("table")) {
            // e.classList.add("");
        }
    }, [show])
    const handleSave = async (values: ScheduleRequestType) => {
        console.log(values);
        const _confirm = confirm("Save?");
        setLoading(true);
        if (_confirm) {
            // save schedule
            const request = { ...values, studentIds: values.students.map(s => s.id) };
            saveSchedule(request).then(res => {
                console.log(res);
                // dispatch(setSchedulesAction())
                return (res.data)
            })
                .then(schedules => {

                    // schedules.map(async (value, index) => {
                    //     const res = await getStudentIdByScheduleId(value.scheduleId);
                    //     const students = res.data.map((value) => value.student);
                    //     console.log({ value, students });
                    //     setSchedule(schedules => [...schedules, { ...value, students }])
                    //     return {
                    //         ...value,
                    //         students: students
                    //     }
                    // })
                    setSchedule(schedules);
                    toast.success("Success");
                })
                .catch(e => {
                    console.log(e);
                    toast.error(e.response.data.message || "Error while saving schedule");
                })
                .finally(() => {
                    setLoading(false);

                })
        }
    }

    useEffect(() => {
        var toast = document.querySelector("div.p-toast-message-content");
    }, []);

    // schedules.map(async (value, index) => {
    //     const res = await getStudentIdByScheduleId(value.scheduleId);
    //     const students = res.data.map((value) => value.student);
    //     console.log(students);
    //     setScheduleList(schedule => schedule.map((value) => value.scheduleId === value.scheduleId ? { ...value, students } : { ...value }))
    // const { slots, subjects, rooms, semesters } = useSelector((state: RootStateType) => state.schedule);

    useEffect(() => {
        (async () => {
            try {
                const [_slots, _subjects, _rooms] = await Promise.all([getAllSlots(), getAllSubjects(), getAllRooms()]);
                setSlots(_slots.data.result);
                setSubjects(_subjects.data.result);
                setRooms(_rooms.data.result);
                const res = await getSemesters();
                const semesters = res.data;
                setSemesters(semesters.filter(s => s.endDate >= new Date().toISOString().split("T")[0]));
            } catch (error) {
                console.log(error);
            }
        })();
    }, [navigate]);
    useEffect(() => {
        (async () => {
            const schedules = await getSchedules().then(res => {
                return (res.data.content)
            })
                .then(schedules => {
                    return (
                        // schedules.map(async (value, index) => {
                        //     const res = await getStudentIdByScheduleId(value.scheduleId);
                        //     const students = await res.data.map((value) => value.student)
                        //     return {
                        //         ...value, students
                        //     }
                        // })
                        schedules.reduce((prev, curr, index, array) => {
                            // value.then((v) => console.log(v))
                            const i = prev.findIndex(t => t.course.semester.semesterCode === curr.course.semester.semesterCode && t.course.courseId == curr.course.courseId && t.room.roomId === curr.room.roomId && t.slot.slotId === curr.slot.slotId);
                            if (+i === -1) {
                                return [...prev, {
                                    ...curr,
                                    trainingDate: {
                                        startDate: curr.trainingDate,
                                        endDate: curr.trainingDate
                                    }
                                }]
                            } else {
                                const _prev = [...prev];
                                _prev[i] = {
                                    ..._prev[i],
                                    trainingDate: {
                                        startDate: (new Date(prev[i].trainingDate.startDate) > new Date(curr.trainingDate)) ? curr.trainingDate : prev[i].trainingDate.startDate,
                                        endDate: (new Date(prev[i].trainingDate.endDate) < new Date(curr.trainingDate)) ? curr.trainingDate : prev[i].trainingDate.endDate
                                    }
                                }
                                return [..._prev];
                            }
                        }, [])
                    );
                })
            // schedules.map(async value => {
            //     value.then(v => { console.log(v); })
            // })
            console.log(schedules);
            setScheduleDisplayed(schedules);


            const users = await getAllUserAndUserProfile();
            const s = users.filter(u => u.roles.find(r => r.name.toLowerCase() === "student"));
            setStudents(s)
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [navigate, schedule])


    const parseDate = (date) => {
        const d = new Date(Date.parse(date));
        return d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear();
    }

    const [scheduleRequest, setScheduleRequest] = useState<ScheduleRequestType>({ students: [], courseId: "", roomId: "", semesterCode: "", slotId: "" });
    console.log(scheduleRequest);
    // useMemo(async () => {
    //     console.log(scheduleRequest.semesterCode);
    //     const res = await getCoursesBySemester(scheduleRequest.semesterCode);
    //     console.log(res);
    //     setCourses(res.data);
    //     setScheduleRequest(request => {
    //         return {
    //             ...request,
    //             courseId: "",
    //         }
    //     })
    // }, [scheduleRequest.semesterCode])

    const handleChangeScheduleRequest = (e) => {
        setScheduleRequest(state => {
            return {
                ...state,
                [e.target.id]: e.target.value
            }
        })
    }


    const showToastSuccess = (detail: string, life: number) => {
        toast.success(detail, { autoClose: life });
    }
    const handleExportToExcel = async () => {
        await exportToExcelSchedules();
    }
    const user = useSelector((state: RootStateType) => state.user)
    const fileRef = useRef(null);
    return (
        <>

            <div>
                <input
                    type='file' accept='.xlsx'
                    // buttonAfter={uploadFileButton}
                    hidden
                    ref={fileRef}
                    onChange={(e) => {
                        const file = e?.target?.files[0];
                        console.log(file);
                        importFromExcel(file).then(res => {
                            console.log(res);
                            setSchedule(state => null);
                            showToastSuccess("Ok", 8000);
                        }).catch(e => {
                            console.log(e);
                            // toast.current.show({ severity: 'error', summary: 'Error', detail: e.response.data.message, life: 8000 })
                            toast.error(e.response.data.message || "Error while importing from excel");
                        });
                        e.target.value = null;
                    }}
                />

            </div>
            <div>
                <Formik
                    innerRef={formikRef}
                    validationSchema={validationSchema}
                    initialValues={scheduleRequest}
                    validateOnChange={true}
                    validateOnBlur={true}
                    validateOnMount={false}
                    validate={(values) => {
                        try {
                            validationSchema.validateSync(values, { abortEarly: false });
                            return {};
                            // eslint-disable-next-line @typescript-eslint/no-explicit-any
                        } catch (err: any) {
                            const errors: Record<string, string[]> = {};
                            // eslint-disable-next-line @typescript-eslint/no-explicit-any
                            err.inner.forEach((e: any) => {
                                if (!errors[e.path]) errors[e.path] = [];
                                errors[e.path].push(e.message);
                            });
                            return errors;
                        }
                    }}
                    onSubmit={(values) => {
                        // console.log(values);
                        handleSave(values);
                    }}>
                    {({ errors, touched, values, handleChange, handleBlur, setFieldValue, setTouched }) => {
                        // console.log(errors);
                        console.log(values);
                        useEffect(() => {
                            if (!values.semesterCode) return;
                            getCoursesBySemester(values.semesterCode)
                                .then(res => {
                                    console.log(res);
                                    setCourses(res.data);
                                    setScheduleRequest(request => {
                                        return {
                                            ...request,
                                            courseId: "",
                                        }
                                    })
                                })
                                .catch(e => {
                                    console.log(e);
                                    toast.error(e.response.data.message || "Error while fetching courses");
                                });
                        }, [values.semesterCode])
                        return (<Form><>
                            <table>
                                <tbody>
                                    {/* <tr>
                                        <td>
                                            <label htmlFor="semesterCode">Semester: </label>
                                        </td>
                                        <td>
                                            <select id='semesterCode' name='semesterCode' value={values.semesterCode ?? ""}
                                                // onChange={handleChangeScheduleRequest}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                            >
                                                <option disabled value={""}>Choose semester</option>
                                                {semesters && semesters.map((value, index) => {
                                                    return (
                                                        <option key={index} value={value.semesterCode}>
                                                            {`${value.semesterCode}`} {`${parseDate(value.startDate)} -> ${parseDate(value.endDate)}`}
                                                        </option>)
                                                })}
                                            </select>
                                        </td>
                                    </tr> */}
                                    <SelectTableRow
                                        fieldName="semesterCode"
                                        label="Semester"
                                        options={semesters.map(semester => ({
                                            label: `${semester.semesterCode} ${parseDate(semester.startDate)} -> ${parseDate(semester.endDate)}`,
                                            value: semester.semesterCode
                                        }))}
                                        placeholder="Choose semester"
                                        messageOnUnavailability={semesters && semesters.length > 0 ? undefined : "No semesters available"}
                                        onChange={(e) => {
                                            handleChange(e);
                                            // setFieldValue("semesterCode", e.target.value);
                                            setTouched({ courseId: false, slotId: false, roomId: false, semesterCode: true });
                                            setFieldValue("courseId", "");
                                            setFieldValue("slotId", "");
                                            setFieldValue("roomId", "");
                                        }}
                                        disabled={loading}
                                    />

                                    <SelectTableRow
                                        fieldName="courseId"
                                        label="Course"
                                        options={courses.map(course => ({
                                            label: `${course.subject.subjectCode} - ${course.subject.subjectName} by ${course.teacher.fullName}`,
                                            value: course.courseId
                                        }))}
                                        placeholder="Choose course"
                                        messageOnUnavailability={courses && courses.length > 0 ? undefined : "No courses available. Please choose other semester"}
                                    />
                                    <SelectTableRow
                                        fieldName="slotId"
                                        label="Slot"
                                        options={slots.map(slot => ({
                                            label: `Slot ${slot.slotId} ${slot.startTime} -> ${slot.endTime}`,
                                            value: slot.slotId
                                        }))}
                                        placeholder="Choose slot"
                                        messageOnUnavailability={courses && courses.length > 0 ? undefined : "Choose a course first to see available slots"}
                                    />

                                    <SelectTableRow
                                        fieldName="roomId"
                                        label="Room"
                                        options={rooms.map(room => ({
                                            label: room.roomNumber,
                                            value: room.roomId
                                        }))}
                                        placeholder="Choose room"
                                        messageOnUnavailability={courses && courses.length > 0 ? undefined : "Choose a course first to see available rooms"}
                                    />
                                    <tr className='relative'>
                                        <td className='absolute top-9'>
                                            <label htmlFor="students">Students: </label>
                                        </td>
                                        <td className='w-full'>
                                            <DataTable
                                                // scrollable scrollHeight="60vh" 
                                                showGridlines
                                                size='small' value={students} className='text-sm schedule-modal'
                                                selectionMode={"multiple"} dragSelection selection={selectedStudents} onSelectionChange={(e) => {
                                                    console.log(e);
                                                    // setScheduleRequest(request => { return e.value.length === 1 ? { ...request, studentIds: e.value } : { ...request, studentIds: e.value } })
                                                    setSelectedStudents(e.value);
                                                    setFieldValue("students", e.value);
                                                }}
                                                dataKey={"id"}

                                            >
                                                <Column selectionMode="multiple" style={{ width: "2rem" }} />
                                                <Column field='id' header="Student Id"></Column>
                                                <Column field='fullName' header={"Full Name"} />
                                                <Column field='email' header="Email" />
                                                <Column field='dob' header="dob" />
                                                <Column field='phoneNumber' header="phone number" />
                                            </DataTable>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            {/* <div className='pt-3 gap-3 flex justify-end pr-3'>
                                <Button label='Save' severity='success' className='btn-lg w-[80px]' />
                                <Button label='Cancel' className='btn btn-danger btn-lg w-[80px]' />
                            </div> */}
                        </>
                        </Form>)
                    }}

                </Formik>
            </div >

        </>
    )
}

const SelectTableRow = ({ onChange, fieldName, label, options, className = "", validate = true, placeholder, disabled = false, messageOnUnavailability }: { onChange?: (e: React.ChangeEvent<HTMLSelectElement>) => void, fieldName: string, label: string, options: { label: string, value: string | number }[], className?: string, validate?: boolean, placeholder?: string, disabled?: boolean, messageOnUnavailability?: string }) => {
    const [field, meta] = useField({
        name: fieldName,
    });
    const { error, touched } = meta;
    return (
        <tr className={className}>
            <td>
                <label htmlFor={fieldName}>{label}</label>
            </td>
            <td>
                {messageOnUnavailability
                    ? <span className='p-error'>{messageOnUnavailability}</span>
                    : <>
                        <select id={fieldName} {...field} disabled={disabled} onChange={(e) => {
                            if (onChange && typeof onChange === "function") {
                                onChange(e);
                            }
                            field.onChange(e);
                        }}>
                            <option value="" disabled>{placeholder || "Select an option"}</option>
                            {options.map((option) => (
                                <option key={option.value} value={option.value}>
                                    {option.label}
                                </option>
                            ))}
                        </select>
                        {/* {meta.touched ? (meta.error && Array.isArray(meta.error) ? meta.error.map((msg, i) => {
                            return (
                                <small className="p-error " key={i}>
                                    {msg}
                                </small>
                            )
                        }) : (
                            <small className="p-error ">{meta.error}</small>
                        )) : null} */}
                        <ErrorMessages fieldName={fieldName} />
                    </>}
            </td>
        </tr>
    );
}

const ErrorMessages = ({ fieldName }: { fieldName: string }) => {
    const [field, meta] = useField({
        name: fieldName,
    });
    const { error, touched } = meta;
    return (
        meta.touched ? (meta.error && Array.isArray(meta.error) ? meta.error.map((msg, i) => {
            return (
                <small className="p-error " key={i}>
                    {msg}
                </small>
            )
        }) : (
            <small className="p-error ">{meta.error}</small>
        )) : null

    )
}

export default Scheduler
