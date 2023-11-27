package com.enpm613.algolab.repository;
import com.enpm613.algolab.entity.lesson.LessonContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonContentRepository extends MongoRepository<LessonContent, Long> {
}

