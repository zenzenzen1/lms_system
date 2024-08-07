import React, { useEffect, useMemo, useState } from 'react';
import { Table } from "react-bootstrap";
import { useDispatch, useSelector } from 'react-redux';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { days, years } from '../../../configurations/configuration';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';
import { getStudentIdByScheduleId } from '../../../services/attendanceService';
import { getAllRooms, getAllSlots, getAllSubjects, getAttendancesByTeacherId } from '../../../services/ScheduleService';
import ClassDetail from './ClassDetail';

const TeacherScheduleList = () => {
    var date = new Date();
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState("schedulePage");
    const [currentDay, setCurrentDay] = useState(new Date(date.getFullYear(), date.getMonth(), date.getDate()));
    const [year, setYear] = useState(currentDay.getFullYear());
    const dispatch = useDispatch();
    const [firstDate, setFirstDate] = useState(new Date(year, currentDay.getMonth(),
        currentDay.getDate() - (currentDay.getDay() === 0 ? 6 : (currentDay.getDay() - 1))));
    const [lastDate, setLastDate] = useState(new Date(year, currentDay.getMonth(), currentDay.getDate() - currentDay.getDay() + 7));
    const [firstMondayOfYear, setFirstMondayOfYear] = useState(day => {
        let _date = new Date(new Date().setMonth(0, 1));
        while (_date.getDay() !== 1) {
            _date = new Date(_date.setDate(_date.getDate() + 1));
        }
        return _date;
    });

    const daysOfWeek = days.map((day, index) => {
        return new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate() + index);
    });
    const [content, setContent] = useState(null);
    const user = useSelector(state => state.user);
    useMemo(() => {
        const _firstDate = new Date(year, currentDay.getMonth(), (currentDay.getDate() - (currentDay.getDay() === 0 ? 6 : (currentDay.getDay() - 1))));
        setFirstDate(_firstDate);
        setLastDate(new Date(_firstDate.getFullYear(), _firstDate.getMonth(), _firstDate.getDate() + 6));
        setFirstMondayOfYear(day => {
            let _date = new Date(year, 0, 1);
            while (_date.getDay() !== 1) {
                _date = new Date(_date.setDate(_date.getDate() + 1));
            }
            return _date;
        });

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [year]);

    const weeks = useMemo(() => {
        const _weeks = [];
        let firstMonday = new Date(firstMondayOfYear);
        while (firstMonday.getFullYear() === +year) {
            _weeks.push(new Date(firstMonday.getFullYear(), firstMonday.getMonth(), firstMonday.getDate()));
            firstMonday.setDate(firstMonday.getDate() + 7);
        }
        return _weeks;
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [firstMondayOfYear, year]);

    useMemo(async () => {
        const res = await getAttendancesByTeacherId(user.userProfile.id, firstDate.toLocaleDateString("en-CA"), lastDate.toLocaleDateString("en-CA"));
        console.log(res.data);
        
        setSchedules(res.data);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [firstDate]);

    const ScheduleList = () => {
        return (
            <Table size='sm' className='' style={{ fontSize: "15px" }} hover>
                <thead>
                    <tr>
                        <th rowSpan={2}>
                            <div>
                                <label>Year</label>
                                <select className='border ml-2' value={year}
                                    onChange={e => {
                                        setCurrentDay(date => {
                                            const year = e.target.value;
                                            return year === date.getFullYear() ? date : new Date(date.setFullYear(e.target.value));
                                        })
                                        setYear(e.target.value);
                                    }}
                                >
                                    {years.map((year, index) => {
                                        return (
                                            <option key={index} value={year}>{year}</option>
                                        )
                                    })
                                    }
                                </select>
                            </div>
                            <div>
                                <label>Week</label>
                                <select className='border ml-2' value={firstDate}
                                    onChange={e => {
                                        // setCurrentDay(date => {
                                        //     const month = e.target.value;
                                        //     return month === date.getMonth() ? date : new Date(date.getFullYear(), month, date.getDate());
                                        // })
                                        const date = new Date(Date.parse(e.target.value))
                                        setFirstDate(date);
                                        setLastDate(new Date(date.getFullYear(), date.getMonth(), date.getDate() + 6));
                                    }}
                                >
                                    {
                                        weeks.map((week, index) => {
                                            const lastDayOfWeek = new Date(week.getFullYear(), week.getMonth(), week.getDate() + 6);
                                            return (
                                                <option className={`${(firstDate.toString() === week.toString()) && "text-red-500"}`} key={index} value={week}>{`${week.getDate()}/${week.getMonth() + 1}/${week.getFullYear()} -> ${lastDayOfWeek.getDate()}/${lastDayOfWeek.getMonth() + 1}/${lastDayOfWeek.getFullYear()}`}</option>
                                            )
                                        })
                                    }
                                </select>
                            </div>
                        </th>
                        {days.map((day, index) => {
                            return (
                                <th className={`${daysOfWeek[index].toDateString() === new Date().toDateString() && ((currentDay.getDay() === 0 && index === 6 || currentDay.getDay() - 1 === index)) && "text-green-500"}`} key={index}>{day}</th>
                            )
                        })}
                    </tr>
                    <tr>
                        {daysOfWeek.map((date, index) => {
                            return (
                                <td key={index}>{`${date.getDate()}/${date.getMonth() + 1}/${firstDate.getFullYear()}`}</td>
                            )
                        })}
                    </tr>
                </thead>
                <tbody>
                    {
                        scheduleContent.reduce((prev, curr, index) => {
                            const slot = curr[1];
                            return [...prev, (
                                <tr key={index}>
                                    <td>
                                        Slot {slot.slotId} <br />
                                        {`${slot.startTime} -> ${slot.endTime}`}
                                    </td>
                                    {curr[0].map((value, index) => {
                                        console.log(value);
                                        return (
                                            <td className={"text-sm"} key={index}>
                                                {value
                                                    ? (<>
                                                        <span onClick={(e) => {

                                                        }}>
                                                            <span className='cursor-pointer' onClick={(e) => {
                                                                // showClassDetail(value.schedule_id)
                                                                console.log(e);
                                                                navigate(`/user/teacher/class-detail/${value.schedule_id}`, {
                                                                    state: {
                                                                        scheduleId: value.schedule_id
                                                                    }
                                                                });
                                                            }}
                                                            // target='_blank'
                                                            >
                                                                {value.subject_code} <br/>
                                                                {value.room_number}
                                                            </span>
                                                        </span>
                                                    </>)
                                                    : "-"}

                                            </td>
                                        )
                                    })}
                                </tr>
                            )]
                        }, []).map((value, index) => {
                            return value;
                        })
                    }
                </tbody>
            </Table>
        )
    }


    const [schedules, setSchedules] = useState([]);


    useEffect(() => {
        (async () => {
            try {
                const _slot = await getAllSlots();
                dispatch(setSlots(_slot.data.result));

                const _subjects = await getAllSubjects();
                dispatch(setSubjects(_subjects.data.result));

                const _rooms = await getAllRooms();
                dispatch(setRooms(_rooms.data.result));


            } catch (error) {
                // setError(error);
                console.log(error);
            }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])


    const slots = useSelector(state => state.schedule.slots);
    // console.log({ slots, schedules });


    // console.log({ weekOfYear, date, firstMondayOfYear, firstDate, currentDay, lastDate });
    const scheduleContent = useMemo(() => {
        // if(!schedules) return [];
        setContent(ScheduleList);
        return slots.reduce((prev, curr, index) => {
            const s = [];
            for (let i = 0; i < 7; i++) {
                const schedule = schedules.find(schedule => schedule.slot_id === curr.slotId
                    && new Date(schedule.training_date).toLocaleDateString("en-CA") === new Date(new Date(firstDate).setDate(firstDate.getDate() + i)).toLocaleDateString("en-CA"));
                s.push(schedule ?? null);
            }
            return [
                ...prev,
                [s, curr]
            ];
        }, []);

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [firstDate, schedules, slots, year, firstMondayOfYear]);
    // console.log({ scheduleContent, schedules });

    const showClassDetail = async (scheduleId) => {
        setContent(
            <ClassDetail scheduleId={scheduleId} />
        )
    }

    return (
        <>
            {content}
        </>
    )
}

export default TeacherScheduleList
