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

    private String data;

    private List<PracticeQuestion> practiceQuestions;

    private String mediaLink;

    public boolean isEmpty() {
        // Check if all relevant properties are null or empty
        return lessonContentId == null &&
                (lessonPageId == null || data==null);

    }

    public Long getLessonContentId() {
        return lessonContentId;
    }

    public void setLessonContentId(Long lessonContentId) {
        this.lessonContentId = lessonContentId;
    }

    public Long getLessonPageId() {
        return lessonPageId;
    }

    public void setLessonPageId(Long lessonPageId) {
        this.lessonPageId = lessonPageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<PracticeQuestion> getPracticeQuestions() {
        return practiceQuestions;
    }

    public void setPracticeQuestions(List<PracticeQuestion> practiceQuestions) {
        this.practiceQuestions = practiceQuestions;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }
}
