package com.enpm613.algolab.service;

import com.enpm613.algolab.entity.Feedback;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.enpm613.algolab.repository.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback addFeedback(Feedback feedback){
            return feedbackRepository.save(feedback);
    }
    
    public List<Feedback> viewFeedback(){
        return feedbackRepository.findAll();
    }
    
//    public static List<Course> viewCourses(){
//
//    }
}