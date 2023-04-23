package io.topjob.topjobapplication.client;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.form.SelectTag;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Component
public class JobWebClient implements IClient {
    private static final String JOB_WEB = "https://www.jobwebghana.com";
    private static final Set<String> roleCache = new HashSet<>();

    @Override
    public List<ClientResponse> getJobs() {
        return IntStream.range(1, 10).mapToObj(this::fetchJobsFromClient).flatMap(List::stream).toList();
    }

    @Override
    public List<ClientResponse> fetchJobsFromClient(int value) {
        String url = JOB_WEB + "/jobs/page/" + value;
        try {
            Document doc = Jsoup.connect(url).get();
            log.info(String.format("JobWebClient/fetchJobsFromClient: Scraping from %s", url));
            Elements elements = doc.getElementsByClass("job");
            return elements.stream().map(element -> {
                Elements title = element.getElementById("titlo").getElementsByTag("a");
                String[] splitTitle = title.text().split("\sat\s");
                String role = splitTitle[0];
                if (!roleCache.add(role)) {
                    return null;
                }
                String recruiter = splitTitle.length > 1 ? splitTitle[1] : "";
                String jobUrl = title.attr("href");
                Element exc = element.getElementById("exc");
                String imageUrl = "";
                String location = "";
                String description = exc.ownText().isBlank() ? exc.getElementsByClass("lista").text() : exc.ownText();
                return ClientResponse.builder()
                        .role(role)
                        .location(location)
                        .url(jobUrl)
                        .imageUrl(imageUrl)
                        .recruiter(recruiter)
                        .description(description)
                        .source(JOB_WEB)
                        .build();
            }).filter(Objects::nonNull).toList();
        } catch (IOException e) {
            log.error(String.format("JobWebGhana: Failed to load data from %s", url));
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
