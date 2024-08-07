package com.example.lms_system.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.lms_system.entity.Course;
import com.example.lms_system.entity.Schedule;
import com.example.lms_system.repository.CourseRepository;
import com.example.lms_system.repository.RoomRepository;
import com.example.lms_system.repository.ScheduleRepository;
import com.example.lms_system.repository.SemesterRepository;
import com.example.lms_system.repository.SlotRepository;
import com.example.lms_system.repository.SubjectRepository;
import com.example.lms_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final DateTimeFormatter dateTimeFormatter;
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final RoomRepository roomRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public static boolean isValidExelFile(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null) {
            return false;
        }
        // equals is compare value of two variables;
        return contentType.equals("application/vnd.ms-excel");
    }

    @Transactional
    public Object getSchedulesFromExcelFile(InputStream inputStream) {
        List<Schedule> schedules = new ArrayList<Schedule>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            // int rowIndex = 0;
            System.out.println(sheet.getPhysicalNumberOfRows());
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                // if (rowIndex == 0) {
                //     rowIndex++;
                //     continue;
                // }
                var row = sheet.getRow(i);
                String semseterCode = row.getCell(1).getStringCellValue();
                String subjectCode = row.getCell(2).getStringCellValue();
                String teacherId = row.getCell(4).getStringCellValue();
                LocalDate trainingDate;
                try {
                    var date = row.getCell(6).getDateCellValue();
                    DateFormat df = new SimpleDateFormat("yyyy-M-d");
                    trainingDate = LocalDate.parse(df.format(date), dateTimeFormatter);
                } catch (DateTimeParseException e) {
                    throw new RuntimeException(e.getMessage());
                }
                long roomId = Math.round(row.getCell(7).getNumericCellValue());
                long slotId = Math.round(row.getCell(9).getNumericCellValue());

                if (scheduleRepository.existsBySemesterTeacherIdRoomId(slotId, semseterCode, teacherId)) {
                    continue;
                }
                var slot = slotRepository.findById(slotId);
                var semester = semesterRepository.findById(semseterCode);
                var room = roomRepository.findById(roomId);
                var subject = subjectRepository.findById(subjectCode);
                var teacher = userRepository.findById(teacherId);
                var _course = courseRepository.findAll().stream()
                        .filter(t -> t.getSemester().getSemesterCode().equals(semseterCode)
                                && t.getSubject().getSubjectCode().equals(subjectCode)
                                && t.getTeacher().getId().equals(teacherId))
                        .findFirst();
                Course course;
                if (!_course.isPresent()) {
                    course = courseRepository.save(Course.builder()
                            .semester(semester.get())
                            .subject(subject.get())
                            .teacher(teacher.get())
                            .build());
                } else {
                    course = _course.get();
                }

                schedules.add(Schedule.builder()
                        .trainingDate(trainingDate)
                        .course(course)
                        .room(room.get())
                        .slot(slot.get())
                        .subject(subject.get())
                        .build());
            }
        } catch (IOException e) {
            e.getStackTrace();
        } catch (Exception ex) {
            throw new RuntimeException("Something went wrong with excel file. Please check data in file!");
        } finally {
        }

        return scheduleRepository.saveAll(schedules);
    }

    public void exportScheduleExcel(HttpServletResponse response, List<Schedule> schedules) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Schedule");
            writeHeader(
                    workbook,
                    sheet,
                    "Index",
                    "Semester",
                    "Course Id",
                    "Course Name",
                    "Instuctor Id",
                    "Instructor Name",
                    "TrainingDate",
                    "Room Id",
                    "Room Number",
                    "Slot Id",
                    "Slot Time");
            write(workbook, sheet, schedules);
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHeader(XSSFWorkbook workbook, XSSFSheet sheet, String... fields) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        Row row = sheet.createRow(0);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < fields.length; i++) {
            createCell(sheet, row, i, fields[i], style);
        }
    }

    private void write(XSSFWorkbook workbook, XSSFSheet sheet, List<Schedule> schedules) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (var schedule : schedules) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(sheet, row, columnCount++, rowCount - 1, style);
            createCell(
                    sheet,
                    row,
                    columnCount++,
                    schedule.getCourse().getSemester().getSemesterCode(),
                    style);
            createCell(sheet, row, columnCount++, schedule.getCourse().getCourseId(), style);
            createCell(
                    sheet, row, columnCount++, schedule.getCourse().getSubject().getSubjectName(), style);
            createCell(
                    sheet, row, columnCount++, schedule.getCourse().getTeacher().getId(), style);
            createCell(
                    sheet, row, columnCount++, schedule.getCourse().getTeacher().getFullName(), style);
            createCell(sheet, row, columnCount++, schedule.getTrainingDate().format(dateTimeFormatter), style);
            createCell(sheet, row, columnCount++, schedule.getRoom().getRoomId(), style);
            createCell(sheet, row, columnCount++, schedule.getRoom().getRoomNumber(), style);
            createCell(sheet, row, columnCount++, schedule.getSlot().getSlotId(), style);
            createCell(
                    sheet,
                    row,
                    columnCount++,
                    schedule.getSlot().getStartTime() + " -> "
                            + schedule.getSlot().getEndTime(),
                    style);
        }
    }

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
}
