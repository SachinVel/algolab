package com.enpm613.algolab.entity.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonPage {

    @Id
    private Long id;

    private Long courseId;

    private String title;

    private List<LessonContent> contents;

    private Long estimatedCompletionTime;


}
