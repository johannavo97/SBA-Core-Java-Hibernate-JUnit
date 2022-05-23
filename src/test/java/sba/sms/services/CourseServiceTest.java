package sba.sms.services;

import org.junit.jupiter.api.BeforeAll;
import sba.sms.utils.CommandLine;

public class CourseServiceTest {
    static CourseService courseService;
    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
    }


}
