package com.enpm613.algolab;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.enpm613.algolab.entity.lesson.LessonContent;
import com.enpm613.algolab.entity.lesson.LessonPage;
import com.enpm613.algolab.entity.lesson.PracticeQuestion;
import com.enpm613.algolab.repository.LessonContentRepository;
import com.enpm613.algolab.repository.LessonRepository;
import com.enpm613.algolab.repository.PracticeQuestionRepository;
import com.enpm613.algolab.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LessonServiceTest {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonContentRepository lessonContentRepository;

    @Autowired
    private PracticeQuestionRepository practiceQuestionRepository;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        lessonContentRepository = mock(LessonContentRepository.class);
        practiceQuestionRepository = mock(PracticeQuestionRepository.class);

        lessonService = new LessonService(lessonRepository, lessonContentRepository, practiceQuestionRepository);
    }

    @Test
    void testCreateLessonWithLessonContent() {
        // Create a LessonPage with valid data
        LessonPage lessonPage = createSampleLessonPage();


        // Mock repository save methods
        when(lessonRepository.save(any(LessonPage.class))).thenReturn(lessonPage);
        when(lessonContentRepository.save(any(LessonContent.class))).thenReturn(new LessonContent());
        when(practiceQuestionRepository.save(any(PracticeQuestion.class))).thenReturn(new PracticeQuestion());

        // Call the service method
        LessonPage createdLesson = lessonService.createLesson(lessonPage);

        // Assertions
        assertNotNull(createdLesson);
        assertEquals("Test Lesson", createdLesson.getTitle());
    }

    @Test
    void testCreateLessonWithNoLessonContent() {
        // Create a LessonPage with no lesson content
        LessonPage lessonPage = new LessonPage();
        lessonPage.setTitle("Lesson with No Content");

        // Call the service method
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> lessonService.createLesson(lessonPage));

        // Verify the exception message
        assertEquals("Cannot create Lesson with no Lesson Content", exception.getMessage());
    }

    @Test
    void testGetLesson() {
        // Mock repository findById method
        when(lessonRepository.findById(1L)).thenReturn(java.util.Optional.of(createSampleLessonPage()));

        // Call the service method
        LessonPage retrievedLesson = lessonService.getLesson(1L);

        // Assertions
        assertNotNull(retrievedLesson);
        assertEquals("Test Lesson", retrievedLesson.getTitle());
    }

    @Test
    void testUpdateLesson() {
        // Create an updated LessonPage
        LessonPage updatedLesson = createSampleLessonPage();
        updatedLesson.setTitle("Updated Lesson");

        // Mock repository methods
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(java.util.Optional.of(createSampleLessonPage()));
        when(lessonRepository.save(any(LessonPage.class))).thenReturn(updatedLesson);

        // Call the service method
        LessonPage result = lessonService.updateLesson(updatedLesson);

        // Assertions
        assertNotNull(result);
        assertEquals("Updated Lesson", result.getTitle());
    }

    @Test
    void testDeleteLesson() {
        // Mock repository findById method
        when(lessonRepository.findById(1L)).thenReturn(java.util.Optional.of(createSampleLessonPage()));

        // Call the service method
        lessonService.deleteLesson(1L);

        // Verify that the delete methods were called
        verify(practiceQuestionRepository, times(1)).deleteAll(anyList());
        verify(lessonContentRepository, times(1)).deleteAll(anyList());
        verify(lessonRepository, times(1)).delete(any(LessonPage.class));
    }

    @Test
    void testGetLessonContent() {
        // Mock repository findById method
        when(lessonRepository.findById(1L)).thenReturn(java.util.Optional.of(createSampleLessonPage()));

        // Call the service method
        LessonPage retrievedLesson = lessonService.getLessonContent(1L);

        // Assertions
        assertNotNull(retrievedLesson);
        assertEquals("Test Lesson", retrievedLesson.getTitle());
    }

    @Test
    void testGetLessonPages() {
        // Mock repository findByCourseId method
        when(lessonRepository.findByCourseId(1L)).thenReturn(List.of(createSampleLessonPage()));

        // Call the service method
        List<LessonPage> lessonPages = lessonService.getLessonPages(1L);

        // Assertions
        assertNotNull(lessonPages);
        assertEquals(1, lessonPages.size());
        assertEquals("Test Lesson", lessonPages.get(0).getTitle());
    }

    private LessonPage createSampleLessonPage() {
        LessonPage lessonPage = new LessonPage();
        lessonPage.setId(1L);
        lessonPage.setCourseId(11L);
        lessonPage.setTitle("Test Lesson");

        LessonContent lessonContent = new LessonContent();
        lessonContent.setLessonPageId(1L);
        lessonContent.setLessonContentId(2L);
        lessonContent.setData("Lesson content data");
        lessonContent.setMediaLink("http://algolab.com");

        PracticeQuestion practiceQuestion = new PracticeQuestion();
        practiceQuestion.setPracticeQuestionId(1L);
        practiceQuestion.setQuestionDifficulty("Medium");
        practiceQuestion.setQuestionContent("What is the time complexity of quicksort?");
        practiceQuestion.setAnswerContent("The time complexity of quicksort is O(n log n)");

        List<PracticeQuestion> practiceQuestionList = new ArrayList<>();
        practiceQuestionList.add(practiceQuestion);

        lessonContent.setPracticeQuestions(practiceQuestionList);

        List<LessonContent> lessonContentList = new ArrayList<>();
        lessonContentList.add(lessonContent);

        lessonPage.setContents(lessonContentList);

        return lessonPage;
    }
}
