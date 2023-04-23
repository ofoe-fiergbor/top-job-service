package io.topjob.topjobapplication.client;

import java.util.List;

public interface IClient {

    List<ClientResponse> getJobs();
    List<ClientResponse> fetchJobsFromClient(int value);
}
