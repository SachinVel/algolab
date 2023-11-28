package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.Feedback;
import com.enpm613.algolab.repository.FeedbackRepository;
import com.enpm613.algolab.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @ResponseBody
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback){
        Feedback savedFeedback = feedbackService.addFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/view")
    @ResponseBody
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.viewFeedback();
        return ResponseEntity.ok(feedbackList);
    }




}