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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LessonServiceTest {


    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonContentRepository lessonContentRepository;

    @Mock
    private PracticeQuestionRepository practiceQuestionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lessonService = new LessonService(lessonRepository, lessonContentRepository, practiceQuestionRepository);
    }

    @Test
    void testCreateLessonWithLessonContent() {
        // Create a LessonPage with valid data
        LessonPage lessonPage = createSampleLessonPage();
        System.out.println(lessonPage);

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
        lessonPage.setId("1");
        lessonPage.setCourseId("1");
        lessonPage.setTitle("Test Lesson");
        lessonPage.setEstimatedCompletionTime(1L);
        LessonContent lessonContent = new LessonContent();
        List<LessonContent> lessonContentList = new ArrayList<>();
        lessonContentList.add(lessonContent);
        lessonPage.setContents(lessonContentList);

        // Call the service method
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> lessonService.createLesson(lessonPage));

        // Verify the exception message
        assertEquals("Cannot create Lesson with no Lesson Content", exception.getMessage());
    }

    @Test
    void testGetLesson() {
        // Mock repository findById method
        when(lessonRepository.findById("1")).thenReturn(java.util.Optional.of(createSampleLessonPage()));

        // Call the service method
        LessonPage retrievedLesson = lessonService.getLesson("1");

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
        when(lessonRepository.existsById("1")).thenReturn(true);
        when(lessonRepository.findById("1")).thenReturn(java.util.Optional.of(createSampleLessonPage()));
        when(lessonRepository.save(any(LessonPage.class))).thenReturn(updatedLesson);

        // Call the service method
        LessonPage result = lessonService.updateLesson(updatedLesson);

        // Assertions
        assertNotNull(result);
        assertEquals("Updated Lesson", result.getTitle());
    }

    @Test
    void testDeleteLesson() {

        LessonPage sampleLesson = createSampleLessonPage();
        System.out.println(sampleLesson);
        // Mock repository findById method
        when(lessonRepository.findById("1")).thenReturn(java.util.Optional.of(sampleLesson));

        // Call the service method
        lessonService.deleteLesson("1");

        // Verify that the delete methods were called
        verify(practiceQuestionRepository, times(1)).deleteAll(eq(sampleLesson.getContents().get(0).getPracticeQuestions()));
        System.out.println("Actual invocation: " + Mockito.mockingDetails(practiceQuestionRepository).getInvocations());
        verify(lessonContentRepository, times(1)).deleteAll(eq(sampleLesson.getContents()));
        verify(lessonRepository, times(1)).delete(eq(sampleLesson));
    }

    @Test
    void testGetLessonContent() {
        // Mock repository findById method
        when(lessonRepository.findById("1")).thenReturn(java.util.Optional.of(createSampleLessonPage()));

        // Call the service method
        LessonPage retrievedLesson = lessonService.getLessonContent("1");

        // Assertions
        assertNotNull(retrievedLesson);
        assertEquals("Test Lesson", retrievedLesson.getTitle());
    }

    @Test
    void testGetLessonPages() {
        // Mock repository findByCourseId method
        when(lessonRepository.findByCourseId("1")).thenReturn(List.of(createSampleLessonPage()));

        // Call the service method
        List<LessonPage> lessonPages = lessonService.getLessonPages("1");

        // Assertions
        assertNotNull(lessonPages);
        assertEquals(1, lessonPages.size());
        assertEquals("Test Lesson", lessonPages.get(0).getTitle());
    }

    private LessonPage createSampleLessonPage() {
        LessonPage lessonPage = new LessonPage();
        lessonPage.setId("1");
        lessonPage.setCourseId("1");
        lessonPage.setTitle("Test Lesson");
        lessonPage.setEstimatedCompletionTime(1L);
        LessonContent lessonContent = new LessonContent();
        lessonContent.setId("1");
        lessonContent.setData("Lesson content data");
        lessonContent.setMediaLink("http://algolab.com");

        PracticeQuestion practiceQuestion = new PracticeQuestion();
        practiceQuestion.setId("1");
        practiceQuestion.setQuestionName("Arrays");
        practiceQuestion.setQuestionDifficulty("Medium");
        practiceQuestion.setQuestionLink("http://www.w3schools.com");
        practiceQuestion.setAnswerContent("The time complexity of quicksort is O(n log n)");

        PracticeQuestion practiceQuestion1 = new PracticeQuestion();
        practiceQuestion1.setId("1");
        practiceQuestion1.setQuestionName("Arrays");
        practiceQuestion1.setQuestionDifficulty("Medium");
        practiceQuestion1.setQuestionLink("http://www.w3schools.com");
        practiceQuestion1.setAnswerContent("The time complexity of quicksort is O(n log n)");


        List<PracticeQuestion> practiceQuestionList = new ArrayList<>();
        practiceQuestionList.add(practiceQuestion);
        practiceQuestionList.add(practiceQuestion1);

        lessonContent.setPracticeQuestions(practiceQuestionList);

        List<LessonContent> lessonContentList = new ArrayList<>();
        lessonContentList.add(lessonContent);

        lessonPage.setContents(lessonContentList);

        return lessonPage;
    }
}
