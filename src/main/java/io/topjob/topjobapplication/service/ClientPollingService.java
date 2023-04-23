package io.topjob.topjobapplication.service;

import io.topjob.topjobapplication.client.ClientResponse;
import io.topjob.topjobapplication.client.IClient;
import io.topjob.topjobapplication.persistence.model.Job;
import io.topjob.topjobapplication.persistence.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientPollingService {
    private final List<IClient> jobClients;
    private final JobRepository jobRepository;

    @Transactional
    @Scheduled(fixedRate = 86_400_000)
    public void pollClientData() {
        jobClients.forEach(iClient -> {
            List<ClientResponse> response = iClient.getJobs();
            var nonNullResponses = response.stream().filter(Objects::nonNull).toList();

            if (!nonNullResponses.isEmpty()) {
                jobRepository.deleteJobsBySource(response.get(0).getSource());
                List<Job> entities = nonNullResponses.stream().map(res -> {
                    var job = new Job();
                    BeanUtils.copyProperties(res, job);
                    return job;
                }).toList();
                jobRepository.saveAll(entities);
            }
        });
    }
}
