package com.enpm613.algolab.service;

import com.enpm613.algolab.entity.Course;
import com.enpm613.algolab.entity.Feedback;
import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.repository.CourseRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.enpm613.algolab.repository.FeedbackRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Feedback addFeedback(Feedback feedback, User user, String courseId){
            Course course = courseRepository.findByCourseId(courseId);
            Feedback newFeedback = new Feedback(user,course, course.getInstructor(), feedback.getContent());
            return feedbackRepository.save(newFeedback);
    }
    
    public List<Feedback> viewFeedback(){
        return feedbackRepository.findAll();
    }

    public List<Feedback> viewFeedbackByCourse(String courseId){
        return feedbackRepository.findByCourse(courseId);
    }

}