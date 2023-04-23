package io.topjob.topjobapplication.client;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientResponse{
    String role;
    String recruiter;
    String description;
    String url;
    String location;
    String imageUrl;
    String category;
    String source;
}
