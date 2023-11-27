package com.enpm613.algolab.service;

import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.entity.lesson.LessonPage;
import com.enpm613.algolab.entity.lesson.LessonContent;
import com.enpm613.algolab.entity.lesson.PracticeQuestion;
import com.enpm613.algolab.repository.LessonContentRepository;
import com.enpm613.algolab.repository.LessonRepository;
import com.enpm613.algolab.repository.PracticeQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LessonContentRepository lessonContentRepository;
    @Autowired
    PracticeQuestionRepository practiceQuestionRepository;





        @Transactional
        public LessonPage createLesson(LessonPage lessonPage) {
            if (lessonPage.isEmpty()) {
                throw new RuntimeException("Cannot create Lesson");
            }

            for (LessonContent lessonContent : lessonPage.getContents()) {
                if (!lessonContent.isEmpty()) {
                    lessonContentRepository.save(lessonContent);

                    for (PracticeQuestion practiceQuestion : lessonContent.getPracticeQuestions()) {
                        if (!practiceQuestion.isEmpty()) {
                            practiceQuestionRepository.save(practiceQuestion);
                        } else {
                            throw new RuntimeException("Practice Question is Empty");
                        }
                    }
                } else {
                    throw new RuntimeException("Cannot create Lesson with no Lesson Content");
                }
            }

            return lessonRepository.save(lessonPage);
        }



    public LessonPage getLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found."));



    }

    public LessonPage updateLesson(LessonPage updatedLessonPage) {
        Long lessonPageId = updatedLessonPage.getId();

        if (lessonRepository.existsById(lessonPageId)) {
            // Update LessonPage
            LessonPage existingLessonPage = lessonRepository.findById(lessonPageId).orElse(null);
            if (existingLessonPage != null) {
                existingLessonPage.setTitle(updatedLessonPage.getTitle());
                existingLessonPage.setCourseId(updatedLessonPage.getCourseId());
                existingLessonPage.setEstimatedCompletionTime(updatedLessonPage.getEstimatedCompletionTime());

                // Update LessonContents
                List<LessonContent> updatedLessonContents = updatedLessonPage.getContents();
                List<LessonContent> existingLessonContents = existingLessonPage.getContents();

                for (LessonContent updatedLessonContent : updatedLessonContents) {
                    Long lessonContentId = updatedLessonContent.getLessonContentId();
                    LessonContent existingLessonContent = existingLessonContents.stream()
                            .filter(content -> content.getLessonContentId().equals(lessonContentId))
                            .findFirst()
                            .orElse(null);

                    if (existingLessonContent != null) {
                        existingLessonContent.setData(updatedLessonContent.getData());
                        existingLessonContent.setMediaLink(updatedLessonContent.getMediaLink());

                        // Update PracticeQuestions
                        List<PracticeQuestion> updatedPracticeQuestions = updatedLessonContent.getPracticeQuestions();
                        List<PracticeQuestion> existingPracticeQuestions = existingLessonContent.getPracticeQuestions();

                        for (PracticeQuestion updatedPracticeQuestion : updatedPracticeQuestions) {
                            Long practiceQuestionId = updatedPracticeQuestion.getPracticeQuestionId();
                            PracticeQuestion existingPracticeQuestion = existingPracticeQuestions.stream()
                                    .filter(question -> question.getPracticeQuestionId().equals(practiceQuestionId))
                                    .findFirst()
                                    .orElse(null);

                            if (existingPracticeQuestion != null) {
                                existingPracticeQuestion.setQuestionDifficulty(updatedPracticeQuestion.getQuestionDifficulty());
                                existingPracticeQuestion.setQuestionContent(updatedPracticeQuestion.getQuestionContent());
                                existingPracticeQuestion.setAnswerContent(updatedPracticeQuestion.getAnswerContent());
                            } else {
                                // Handle new PracticeQuestion if needed
                                existingPracticeQuestions.add(practiceQuestionRepository.save(updatedPracticeQuestion));
                            }
                        }
                    } else {
                        // Handle new LessonContent if needed
                        existingLessonContents.add(lessonContentRepository.save(updatedLessonContent));
                    }
                }
                // Save the updated hierarchy
                return lessonRepository.save(existingLessonPage);
            }
        }

        // Return null if the lessonPage doesn't exist
        return null;
    }


    public void deleteLesson(Long id) {
        LessonPage existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found."));

        List<LessonContent> lessonContents = existingLesson.getContents();
        for(LessonContent lessonContent : lessonContents)
        {
            practiceQuestionRepository.deleteAll(lessonContent.getPracticeQuestions());
        }
        lessonContentRepository.deleteAll(lessonContents);
        lessonRepository.delete(existingLesson);
    }



//    public LessonContent createLessonContent(LessonContent lessonContent) {
//        return lessonContentRepository.save(lessonContent);
//    }
//
//    public LessonContent getLessonContent(Long id) {
//        return lessonContentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Lesson not found."));
//    }
//
//    public LessonContent updateLessonContent(LessonContent lessonContent) {
//        return lessonContentRepository.save(lessonContent);
//    }
//
//    public void deleteLessonContent(Long id) {
//
//        LessonContent existingLessonContent = lessonContentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Lesson Content not found."));
//        lessonContentRepository.delete(existingLessonContent);
//    }




    public PracticeQuestion createPracticeQuestion(PracticeQuestion practiceQuestion) {
        return practiceQuestionRepository.save(practiceQuestion);
    }

    public PracticeQuestion getPracticeQuestion(Long id) {

        return practiceQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found."));
    }

    public PracticeQuestion updatePracticeQuestion(PracticeQuestion practiceQuestion) {
        return practiceQuestionRepository.save(practiceQuestion);
    }

    public void deletePracticeQuestion(Long id) {


        PracticeQuestion existingPracticeQuestion = practiceQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson Content not found."));
        practiceQuestionRepository.delete(existingPracticeQuestion);
    }
}
