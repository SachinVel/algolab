package com.enpm613.algolab.repository;

import com.enpm613.algolab.entity.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

}