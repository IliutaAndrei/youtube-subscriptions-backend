package org.iliuta.youtubesubscriptionsbackend.client;

import org.iliuta.youtubesubscriptionsbackend.dto.youtube.SubscriptionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class YouTubeClient {

    private final RestClient restClient;

    public YouTubeClient(RestClient restClient) {
        this.restClient = restClient;
    }


    public SubscriptionResponse fetchSubscriptions(String accessToken, String nextPageToken) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/youtube/v3/subscriptions")
                        .queryParam("mine", true)
                        .queryParam("part", "snippet,contentDetails")
                        .queryParamIfPresent("pageToken", Optional.ofNullable(nextPageToken))
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(SubscriptionResponse.class);
    }
}
