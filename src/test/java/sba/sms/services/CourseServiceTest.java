package sba.sms.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.utils.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseServiceTest {
    static CourseService courseService;
    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
    }

    @Test
    void getCourseById() {
        assertThat(courseService.getCourseById(1)).extracting(course -> course.getId()).isEqualTo(1);
        assertThat(courseService.getCourseById(1)).extracting(course -> course.getName()).isEqualTo("Java");
        assertThat(courseService.getCourseById(1)).extracting(course -> course.getInstructor()).isEqualTo("Phillip Witkin");
    }
}
