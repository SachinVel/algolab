package com.enpm613.algolab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseInstructorMappingRepository extends MongoRepository<CourseInstructorMapping, String> {
}

