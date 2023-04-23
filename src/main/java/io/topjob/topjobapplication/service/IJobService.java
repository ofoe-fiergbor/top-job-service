package io.topjob.topjobapplication.service;

import io.topjob.topjobapplication.dto.JobResponse;

import java.util.List;

public interface IJobService {
    List<JobResponse> getAllJobs();

    List<JobResponse> searchJob(String str);
}
