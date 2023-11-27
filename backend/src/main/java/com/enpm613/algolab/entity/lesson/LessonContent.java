package com.enpm613.algolab.entity.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonContent {
    @Id
    private Long lessonContentId;

    private Long lessonPageId;

    private byte[] data;

    private List<PracticeQuestion> practiceQuestions;

    private String mediaLink;

    public boolean isEmpty() {
        // Check if all relevant properties are null or empty
        return lessonContentId == null &&
                (lessonPageId == null || data==null);

    }

}
