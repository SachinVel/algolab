package com.enpm613.algolab.entity.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PracticeQuestion {
    @Id
    private Long practiceQuestionId;

    private String questionDifficulty;

    private String questionContent;

    private String answerContent;

    public boolean isEmpty() {
        // Check if all relevant properties are null or empty
        return practiceQuestionId == null;

    }

    public Long getPracticeQuestionId() {
        return practiceQuestionId;
    }

    public void setPracticeQuestionId(Long practiceQuestionId) {
        this.practiceQuestionId = practiceQuestionId;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
