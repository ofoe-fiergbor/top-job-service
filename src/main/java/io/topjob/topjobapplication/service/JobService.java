package io.topjob.topjobapplication.service;

import io.topjob.topjobapplication.dto.JobResponse;
import io.topjob.topjobapplication.persistence.model.Job;
import io.topjob.topjobapplication.persistence.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService implements IJobService {

    private final JobRepository jobRepository;

    @Override
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream().map(job -> {
            JobResponse response = new JobResponse();
            BeanUtils.copyProperties(job, response);
            return response;
        }).toList();
    }

    @Override
    public List<JobResponse> searchJob(String role) {
        List<Job> jobsByRole = jobRepository
                .findByRoleContainingIgnoreCaseOrRecruiterContainingIgnoreCaseOrDescriptionContainingIgnoreCase(role, role, role);
        Collections.shuffle(jobsByRole);
        return jobsByRole
                .stream().map(job -> {
                    var response = new JobResponse();
                    BeanUtils.copyProperties(job, response);
                    return response;
                }).toList();
    }
}
