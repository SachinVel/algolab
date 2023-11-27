package com.enpm613.algolab.controller;


import com.enpm613.algolab.entity.lesson.LessonPage;
import com.enpm613.algolab.entity.lesson.PracticeQuestion;
import com.enpm613.algolab.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class LessonController {

    @Autowired
    LessonService lessonService;

    @GetMapping("/getLesson/{lessonId}")
    public ResponseEntity<Object> getLesson(@PathVariable("lessonId") Long id) {
        try {
            return ResponseEntity.ok(lessonService.getLesson(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/createLesson")
    public ResponseEntity<Object> createLesson(@RequestBody LessonPage lessonPage) {
        try {
            return ResponseEntity.ok(lessonService.createLesson(lessonPage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/updateLesson")
    public ResponseEntity<Object> updateLesson(@RequestBody LessonPage lessonPage) {
        try {
            return ResponseEntity.ok(lessonService.updateLesson(lessonPage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteLesson/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable("lessonId") Long id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.ok("Lesson deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getPracticeQuestion/{practiceQuestionId}")
    public ResponseEntity<Object> getPracticeQuestion(@PathVariable("practiceQuestionId") Long id) {
        try {
            return ResponseEntity.ok(lessonService.getPracticeQuestion(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/createPracticeQuestion")
    public ResponseEntity<Object> createPracticeQuestion(@RequestBody PracticeQuestion practiceQuestion) {
        try {
            return ResponseEntity.ok(lessonService.createPracticeQuestion(practiceQuestion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/updatePracticeQuestion")
    public ResponseEntity<Object> updatePracticeQuestion(@RequestBody PracticeQuestion practiceQuestion) {
        try {
            return ResponseEntity.ok(lessonService.updatePracticeQuestion(practiceQuestion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePracticeQuestion/{deletePracticeQuestionId}")
    public ResponseEntity<Object> deletePracticeQuestion(@PathVariable("practiceQuestionId") Long id) {
        try {
            lessonService.deletePracticeQuestion(id);
            return ResponseEntity.ok("Practice Question deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



}
