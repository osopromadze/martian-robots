package com.guidesmiths.martian_robot.repository;

import com.guidesmiths.martian_robot.entity.InputOutput;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputOutputRepository extends MongoRepository<InputOutput, String> {
}
