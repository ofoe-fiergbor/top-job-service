package io.topjob.topjobapplication.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    private String role;
    private String recruiter;
    private String description;
    private String url;
    private String location;
    private String imageUrl;
    private String source;
}
