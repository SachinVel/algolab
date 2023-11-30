package com.enpm613.algolab.entity;

import com.enpm613.algolab.entity.lesson.LessonPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {

    @Id
    private String id;
    private String title;
    private Difficulty difficulty;
    private String description;
    private User instructor;
    private List<LessonPage> lessonPages;

    public Course(Object o, String title, Difficulty difficulty, String description, User instructor) {
    }

    public enum Difficulty {
        EASY,
        AVERAGE,
        HARD
    }
}


