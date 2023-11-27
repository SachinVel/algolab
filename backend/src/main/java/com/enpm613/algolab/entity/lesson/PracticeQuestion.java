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


}
