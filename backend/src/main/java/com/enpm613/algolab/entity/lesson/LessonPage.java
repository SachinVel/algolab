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

    public boolean isEmpty() {
        // Check if all relevant properties are null or empty
        return id == null &&
                (title == null || title.trim().isEmpty()) &&
                (courseId == null || contents.isEmpty() || estimatedCompletionTime==null);
        // Add more properties as needed
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LessonContent> getContents() {
        return contents;
    }

    public void setContents(List<LessonContent> contents) {
        this.contents = contents;
    }

    public Long getEstimatedCompletionTime() {
        return estimatedCompletionTime;
    }

    public void setEstimatedCompletionTime(Long estimatedCompletionTime) {
        this.estimatedCompletionTime = estimatedCompletionTime;
    }
}
