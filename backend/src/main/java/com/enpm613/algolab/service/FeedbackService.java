package com.enpm613.algolab.service;

import com.enpm613.algolab.entity.Feedback;
import com.enpm613.algolab.entity.User;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.enpm613.algolab.repository.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback addFeedback(Feedback feedback, User user, String courseId){
            Feedback newFeedback = new Feedback(user,courseId, feedback.getInstructorId(), feedback.getContent());
            return feedbackRepository.save(newFeedback);
    }
    
    public List<Feedback> viewFeedback(){
        return feedbackRepository.findAll();
    }

    public List<Feedback> viewFeedbackByCourse(String courseId){
        return feedbackRepository.findByCourse(courseId);
    }
    
//    public static List<Course> viewCourses(){
//
//    }
}