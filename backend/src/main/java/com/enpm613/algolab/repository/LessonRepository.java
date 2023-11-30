package com.enpm613.algolab.repository;

import com.enpm613.algolab.entity.lesson.LessonPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends MongoRepository<LessonPage, Long> {

    @Query("{id: ?0}")
    Optional<LessonPage> findById(Long id);

    List<LessonPage> findByCourseId(Long courseId);
}
