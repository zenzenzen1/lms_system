import { useEffect, useMemo, useState } from 'react';
import { Table } from 'react-bootstrap';
import { days, years } from '../../../configurations/configuration';
import { getAllRooms, getAllSlots, getAllSubjects, getSchedulesByStudentId } from '../../../services/ScheduleService';
import { useDispatch, useSelector } from 'react-redux';
import { setRooms, setSchedules, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';

const ScheduleList = () => {
    var date = new Date();
    const dispatch = useDispatch();
    const [currentDay, setCurrentDay] = useState(new Date(date.getFullYear(), date.getMonth(), date.getDate() - 6));
    const [firstDate, setFirstDate] = useState(new Date(currentDay.getFullYear(), currentDay.getMonth(),
        currentDay.getDate() - (currentDay.getDay() === 0 ? 6 : (currentDay.getDay() - 1))));
    const [lastDate, setLastDate] = useState(new Date(currentDay.getFullYear(), currentDay.getMonth(), currentDay.getDate() - currentDay.getDay() + 7));
    const [firstMondayOfYear, setFirstMondayOfYear] = useState(day => {
        const _date = new Date().setMonth(0, 1);
        return new Date(_date);
    });
    const [year, setYear] = useState(currentDay.getFullYear());
    const daysOfWeek = days.map((day, index) => {
        return new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate() + index);
    });
    // const weekOfYear = useMemo(() => {
    //     const firstMonday = new Date(firstMondayOfYear.getFullYear(), firstMondayOfYear.getMonth(), firstMondayOfYear.getDate());
    //     const current = new Date(currentDay.getFullYear(), currentDay.getMonth(), currentDay.getDate());
    //     const diff = current - firstMonday;
    //     const oneDay = 24 * 60 * 60 * 1000;
    //     return Math.floor(diff / oneDay / 7);
    // }, [currentDay, firstMondayOfYear]);
    const [schedules, setSchedules] = useState([]);

    useMemo(() => {
        setFirstDate(date => new Date(new Date(date.setFullYear(firstDate.getFullYear())).setMonth(firstDate.getMonth(), firstDate.getDate() - (firstDate.getDay() === 0 ? 6 : (firstDate.getDay() - 1)))));
        setLastDate(date => new Date(date.setFullYear(firstDate.getFullYear())));
        setFirstMondayOfYear(date => new Date(new Date(date.setFullYear(firstDate.getFullYear())).setMonth(0, 1)));

    }, [currentDay]);
    const user = useSelector(state => state.user);
    const weeks = useMemo(() => {
        const _weeks = [];
        const firstMonday = new Date(firstMondayOfYear.getFullYear(), firstMondayOfYear.getMonth(), firstMondayOfYear.getDate());
        while(firstMonday.getFullYear() === year) {
            _weeks.push(new Date(firstMonday.getFullYear(), firstMonday.getMonth(), firstMonday.getDate()));
            firstMonday.setDate(firstMonday.getDate() + 7);
        }
        return _weeks;
    }, [year]);
    useEffect(() => {
        (async () => {
            try {
                const _slot = await getAllSlots();
                dispatch(setSlots(_slot.data.result));

                const _subjects = await getAllSubjects();
                dispatch(setSubjects(_subjects.data.result));

                const _rooms = await getAllRooms();
                dispatch(setRooms(_rooms.data.result));

                (async () => {
                    const res = await getSchedulesByStudentId(user.userProfile.id, firstDate.toLocaleDateString("en-CA"), lastDate.toLocaleDateString("en-CA"));
                    setSchedules(res.data);
                })();
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
    const coursesBySlots = useMemo(() => {
        // if(!schedules) return [];
        
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
    }, [firstDate, schedules, slots]);
    // console.log(coursesBySlots);

    return (
        <>
            <Table size='sm' className='' style={{ fontSize: "15px" }} hover>
                <thead>
                    <tr>
                        <th rowSpan={2}>
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
                            <br />
                            <label>Week</label>
                            <select className='border ml-2' value={firstDate}
                                onChange={e => {
                                    // setCurrentDay(date => {
                                    //     const month = e.target.value;
                                    //     return month === date.getMonth() ? date : new Date(date.getFullYear(), month, date.getDate());
                                    // })
                                    console.log(e.target.value);
                                }}
                            >
                                {
                                    weeks.map((week, index) => {
                                        const lastDayOfWeek = new Date(week.getFullYear(), week.getMonth(), week.getDate() + 6);
                                        return (
                                            <option key={index} value={week}>{`${week.getDate()}/${week.getMonth() + 1}/${week.getFullYear()} -> ${lastDayOfWeek.getDate()}/${lastDayOfWeek.getMonth() + 1}/${lastDayOfWeek.getFullYear()}`}</option>
                                        )
                                    })
                                }
                            </select>
                        </th>
                        {days.map((day, index) => {
                            return (
                                <th key={index}>{day}</th>
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
                        // coursesBySlots && coursesBySlots.flatMap((value, index, array) => {
                        //     return value.map((course, i) => {
                        //         console.log(i);
                        //         return (
                        //             <td key={i}>
                        //                 {
                        //                     course && "abc"
                        //                 }
                        //             </td>
                        //         )
                        //     })
                        // })
                        coursesBySlots.reduce((prev, curr, index) => {
                            const slot = curr[1];
                            return [... prev, (
                                <tr key={index}>
                                    <td>
                                        Slot {slot.slotId} <br/>
                                        {`${slot.startTime} -> ${slot.endTime}`}
                                    </td>
                                    {curr[0].map((value, index) => {
                                        return (
                                            <td className={"text-sm"} key={index}>
                                                {value ? value.subject_name : "-"}
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
        </>
    )
}

export default ScheduleList
