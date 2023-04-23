package io.topjob.topjobapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobResponse {
    String role;
    String recruiter;
    String description;
    String url;
    String location;
    String imageUrl;
    String id;
}

