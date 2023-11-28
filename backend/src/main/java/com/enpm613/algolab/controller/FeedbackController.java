package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.Course;
import com.enpm613.algolab.entity.Feedback;
import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.repository.FeedbackRepository;
import com.enpm613.algolab.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackRepository feedbackRepository, FeedbackService feedbackService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @ResponseBody
    public ResponseEntity<Feedback> addFeedback(@AuthenticationPrincipal UserDetails user, @RequestBody Feedback feedback){
        Feedback savedFeedback = feedbackService.addFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/viewAll")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT','ADMIN')")
    @ResponseBody
    public ResponseEntity<List<Feedback>> getAllFeedback(@AuthenticationPrincipal UserDetails user) {
        List<Feedback> feedbackList = feedbackService.viewFeedback();
        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/viewCourseFeedback")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT','ADMIN')")
    @ResponseBody
    public ResponseEntity<List<Feedback>> getFeedbackByCourse(@AuthenticationPrincipal UserDetails user, @RequestBody String courseId) {
        List<Feedback> feedbackList = feedbackService.viewFeedbackByCourse(courseId);
        return ResponseEntity.ok(feedbackList);
    }

}