package com.enpm613.algolab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Feedback {

    private String userId;
    private String courseId;
    private String instructorId;
    private String content;

    public String getUser() {
        return userId;
    }

    public void setUser(String user) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String course) {
        this.courseId = courseId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
}
