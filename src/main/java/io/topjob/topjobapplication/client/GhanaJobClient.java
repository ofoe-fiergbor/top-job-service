package io.topjob.topjobapplication.client;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GhanaJobClient implements IClient {
    private static final String GHANA_JOB_URL = "https://www.ghanajob.com";
    private static final String URL_TEMPLATE = GHANA_JOB_URL + "/job-vacancies-search-ghana?page=%s";

    @Override
    public List<ClientResponse> getJobs() {
        return IntStream.range(0, 3).mapToObj(this::fetchJobsFromClient)
                .flatMap(List::stream).toList();
    }

    @Override
    public List<ClientResponse> fetchJobsFromClient(int value) {
        try {
            Document doc = Jsoup.connect(URL_TEMPLATE.formatted(value)).get();
            log.info(String.format("GhanaJobClient/fetchJobsFromClient: Scraping from %s", URL_TEMPLATE.formatted(value)));

            Elements elements = doc.getElementsByClass("job-title");
            return elements.stream().map(element -> {
                var titles = element.getElementsByTag("h5")
                        .stream().map(title -> title.getElementsByTag("a")).findFirst().get();
                String jobUrl = String.format("%s%s", GHANA_JOB_URL, titles.attr("href"));
                String role = titles.text();
                String recruiter = element.getElementsByClass("company-name").text();
                String imageUrl = "";
                String location = "";
                String category = "";
                String description = element.getElementsByClass("search-description").text();
                return ClientResponse.builder()
                        .role(role)
                        .location(location)
                        .url(jobUrl)
                        .imageUrl(imageUrl)
                        .recruiter(recruiter)
                        .description(description)
                        .source(GHANA_JOB_URL)
                        .category(category)
                        .build();
            }).toList();
        } catch (Exception e) {
            log.error(String.format("GhanaJob: Failed to load data from %s", GHANA_JOB_URL));
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
