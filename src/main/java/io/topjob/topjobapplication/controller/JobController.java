package io.topjob.topjobapplication.controller;


import io.topjob.topjobapplication.dto.JobResponse;
import io.topjob.topjobapplication.service.IJobService;
import io.topjob.topjobapplication.util.RequiresApiKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final IJobService jobService;

    @GetMapping
    @RequiresApiKey
    public ResponseEntity<List<JobResponse>> getAllJobs(){
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    @GetMapping("/search")
    @RequiresApiKey
    public ResponseEntity<List<JobResponse>> searchJobsByRole(@RequestParam String job) {
        return new ResponseEntity<>(jobService.searchJob(job), HttpStatus.OK);
    }
}
