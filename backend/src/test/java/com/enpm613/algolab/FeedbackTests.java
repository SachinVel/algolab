package com.enpm613.algolab;

import com.enpm613.algolab.entity.Course;
import com.enpm613.algolab.entity.CourseDTO;
import com.enpm613.algolab.entity.Feedback;
import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.repository.CourseRepository;
import com.enpm613.algolab.repository.FeedbackRepository;
import com.enpm613.algolab.service.CourseService;
import com.enpm613.algolab.service.FeedbackService;
import com.enpm613.algolab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.enpm613.algolab.entity.Course.Difficulty.EASY;
import static com.enpm613.algolab.entity.Role.INSTRUCTOR;
import static com.enpm613.algolab.entity.Role.STUDENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FeedbackTests {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FeedbackService feedbackService;

    @Test
    public void testSendCourseFeedback() {
        //get student user details
        User user = new User();
        user.setFirstName("testFeedback");
        user.setLastName("test");
        user.setEmail("test@email.com");
        user.setUsername("testFeedback");
        user.setPassword("testFeedback@1234");
        user.setRole(STUDENT);
        user.setBio("testFeedback");
        userService.registerUser(user);

        // instructor
//        User instructor = new User();
//        instructor.setFirstName("testInstructor");
//        instructor.setLastName("test");
//        instructor.setEmail("testInstructor@email.com");
//        instructor.setUsername("testInstructor");
//        instructor.setPassword("testInstructor@1234");
//        instructor.setRole(INSTRUCTOR);
//        instructor.setBio("test");
//        userService.registerUser(instructor);
        User instructor = userService.getUserByUsername("barath98");


        List<Course> course = courseService.getUserCourse(instructor);


        for(Course curCourse:course) {
            // Send feedback
            Feedback feedback = new Feedback(user, curCourse, curCourse.getInstructor(), "This course is challenging but rewarding.");
            Feedback savedFeedback = feedbackService.addFeedback(feedback, user, curCourse.getId());

            assertNotNull(savedFeedback);
            assertEquals(savedFeedback.getContent(),"This course is challenging but rewarding.");
        }
        userService.deleteUser(user.getId());
    }

    @Test
    public void viewFeedback() {
        List<Feedback> feedbacks = feedbackService.viewFeedback();
        assertNotNull(feedbacks);
    }

    @Test
    public void viewFeedbackByCourse() throws IOException {
        User instructor = userService.getUserByUsername("barath98");

        FileInputStream fileInputStream = new FileInputStream("src/main/resources/image.jpg");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image.jpg", "multipart/form-data", fileInputStream);
        CourseDTO course = new CourseDTO();
        course.setTitle("testCourse");
        course.setDifficulty(EASY);
        course.setDescription("Description");
        course.setImage(mockMultipartFile);
        Course newCourse = courseService.createCourse(course,instructor);

        feedbackService.viewFeedbackByCourse(newCourse.getId());

        List<Feedback> feedbacks = feedbackService.viewFeedbackByCourse(newCourse.getId());
        assertNotNull(feedbacks);
        for(Feedback feedback:feedbacks)
            assertEquals(feedback.getCourse(), course);
    }

    @Test
    public void viewFeedbackByInstructor() {
        User instructor = userService.getUserByUsername("barath98");
        System.out.println(instructor);
        List<Feedback> feedbacks = feedbackService.viewFeedbackByInstructor(instructor.getId());
        assertNotNull(feedbacks);
        for(Feedback feedback:feedbacks) {
            System.out.println(feedback.getInstructor());
            assertEquals(feedback.getInstructor(), instructor);
        }
    }

    }



