package io.topjob.topjobapplication.client;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JobsInGhana implements IClient {

    private static final String JOBS_IN_GHANA = "https://www.jobsinghana.com";
    private static final String URL_TEMPLATE = JOBS_IN_GHANA + "/jobs/indexnew.php?device=d&cat=%s";
    private static final List<Integer> categories = List.of(
            10, 20, 26, 363, 59, 34, 38, 355, 81,
            48, 67, 16, 2, 27, 29, 347, 60, 40, 44,
            43, 57, 368, 17, 25, 320, 30, 349, 35, 39, 71,
            47, 359, 313, 350, 346, 31, 21,
            37, 41, 84, 362, 51);

    @Override
    public List<ClientResponse> getJobs() {
        return categories.stream().map(this::fetchJobsFromClient).flatMap(List::stream).toList();
    }

    @Override
    public List<ClientResponse> fetchJobsFromClient(int value) {
        try {
            Document doc = Jsoup.connect(URL_TEMPLATE.formatted(value)).get();
            log.info(String.format("JobsInGhana/fetchJobsFromClient: Scraping from %s", URL_TEMPLATE.formatted(value)));

            Elements elements = doc.getElementsByClass("joblistitem");
            return elements.stream().map(element -> {
                String role = element.getElementById("jobtitle").ownText();
                String availableRecruiter = element.getElementsByAttributeValueContaining("title", "view all jobs posted by").text();
                String location = element.getElementsByAttributeValueContaining("property", "address").text();
                String imageUrl = element.getElementsByAttributeValueContaining("class", "jlistlogo").attr("src");
                String jobUrl = element.getElementById("jobtitle").attr("href");
                String recruiter = availableRecruiter.isEmpty() ? "Confidential" : availableRecruiter;
                String description = element.getElementsByTag("p").text();

                return ClientResponse.builder()
                        .role(role)
                        .description(description)
                        .imageUrl(formatUrl(imageUrl))
                        .location(location)
                        .recruiter(recruiter)
                        .url(formatUrl(jobUrl))
                        .source(JOBS_IN_GHANA)
                        .category(String.valueOf(value))
                        .build();
            }).toList();

        } catch (IOException e) {
            log.error(String.format("JobsInGhana: Failed to load data from %s", JOBS_IN_GHANA));
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private String formatUrl(String link) {
        return String.format("%s%s", JOBS_IN_GHANA, link.substring(2));
    }
}
