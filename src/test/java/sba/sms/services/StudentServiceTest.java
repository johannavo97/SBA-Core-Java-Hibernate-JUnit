package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        CommandLine.addData();
    }

    @Test
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password")
        ));

        assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);

    }
    @Test
    @DisplayName("Create Student and assert size increased to 6 total students")
    void createStudent() {
        studentService.createStudent(new Student("thanhtruc2009@gmail.com", "thanh vo", "password"));
        Student actual = studentService.getStudentByEmail("thanhtruc2009@gmail.com");
        assertThat(actual).extracting(Student::getEmail).isEqualTo("thanhtruc2009@gmail.com");
        assertThat(studentService.getAllStudents()).isNotEmpty().hasSize(6);

    }

    @Test
    void getStudentByEmail() {
        assertThat(studentService.getStudentByEmail("annette@gmail.com")).extracting(student-> student.getEmail()).isEqualTo("annette@gmail.com");
        assertThat(studentService.getStudentByEmail("annette@gmail.com")).extracting(student-> student.getName()).isEqualTo("annette allen");
        assertThat(studentService.getStudentByEmail("annette@gmail.com")).extracting(student-> student.getPassword()).isEqualTo("password");
    }

    @ParameterizedTest
    @ValueSource(strings = {"reema brown", "annette allen", "anthony gallegos","ariadna ramirez", "bolaji saibu"})
    void testNamesFromDb(String names) {
        List<Student> list = studentService.getAllStudents();
        assertThat(list).extracting(Student::getName).contains(names);

    }
}