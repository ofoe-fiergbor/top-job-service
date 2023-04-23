package io.topjob.topjobapplication.persistence.repository;

import io.topjob.topjobapplication.persistence.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    void deleteJobsBySource(String source);

    //    @Query("{ $or: [ { 'role': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } }, { 'recruiter': { $regex: ?0, $options: 'i' } } ] }")
//    List<Job> findJobsByRole(@Param("roleToFind") String role);
    List<Job> findByRoleContainingIgnoreCaseOrRecruiterContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String role, String recruiter, String description
    );

}
