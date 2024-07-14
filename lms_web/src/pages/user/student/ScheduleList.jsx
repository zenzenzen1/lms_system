import { useMemo, useState } from 'react';
import { Table } from 'react-bootstrap';
import { days } from '../../../configurations/configuration';

const ScheduleList = () => {
    var date = new Date();
    const [currentDay, setCurrentDay] = useState(new Date(date.getFullYear(), date.getMonth(), date.getDate()));
    const [firstDate, setFirstDate] = useState(new Date(currentDay.getFullYear(), currentDay.getMonth(),
        currentDay.getDate() - (currentDay.getDay() === 0 ? 6 : (currentDay.getDay() - 1))));
    const [lastDate, setLastDate] = useState(new Date(currentDay.getFullYear(), currentDay.getMonth(), currentDay.getDate() - currentDay.getDay() + 7));
    const [firstMondayOfYear, setFirstMondayOfYear] = useState(day => {
        const _date = new Date().setMonth(0, 1);
        return new Date(_date);
    });
    const daysOfWeek = days.map((day, index) => {
        return new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate() + index);
    });
    const weekOfYear = useMemo(() => {
        const firstMonday = new Date(firstMondayOfYear.getFullYear(), firstMondayOfYear.getMonth(), firstMondayOfYear.getDate());
        const current = new Date(currentDay.getFullYear(), currentDay.getMonth(), currentDay.getDate());
        const diff = current - firstMonday;
        const oneDay = 24 * 60 * 60 * 1000;
        return Math.floor(diff / oneDay / 7);
    }, [currentDay, firstMondayOfYear]);
    
    useMemo(() => {
        setFirstDate(date => new Date(new Date(date.setFullYear(currentDay.getFullYear())).setMonth(currentDay.getMonth(), currentDay.getDate() - (currentDay.getDay() === 0 ? 6 : (currentDay.getDay() - 1)))));
        setLastDate(date => new Date(date.setFullYear(currentDay.getFullYear())));
        setFirstMondayOfYear(date => new Date(new Date(date.setFullYear(currentDay.getFullYear())).setMonth(0, 1)));
    }, [currentDay]);
    
    console.log({ weekOfYear, date, firstMondayOfYear, firstDate, currentDay, lastDate });
    
    
    return (
        <>
            <Table size='sm' className='' style={{ fontSize: "15px" }}>
                <thead>
                    <tr>
                        <th rowSpan={2}>
                            <label>Year</label>
                            <select className='border ml-2' value={currentDay.getFullYear()}
                                onChange={e => {
                                    setCurrentDay(date => {
                                        const year = e.target.value;
                                        return year === date.getFullYear() ? date : new Date(date.setFullYear(year));
                                    })
                                }}
                            >
                                <option>2021</option>
                                <option>2022</option>
                                <option>2023</option>
                                <option>2024</option>
                                <option>2025</option>
                            </select>
                            <br />
                            <label>Week</label>
                            <select className='border ml-2' value={currentDay.getMonth()}
                                onChange={e => {
                                    setCurrentDay(date => {
                                        const month = e.target.value;
                                        return month === date.getMonth() ? date : new Date(date.getFullYear(), month, date.getDate());
                                    })
                                }}
                            >
                                {
                                    
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
                    <tr>
                        <td>07:30 - 09:30</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    {/* Add more rows as needed */}
                </tbody>
            </Table>
        </>
    )
}

export default ScheduleList
