package org.iliuta.youtubesubscriptionsbackend.service;

import org.iliuta.youtubesubscriptionsbackend.client.YouTubeClient;
import org.iliuta.youtubesubscriptionsbackend.dto.SubscriptionInfo;
import org.iliuta.youtubesubscriptionsbackend.dto.youtube.Subscription;
import org.iliuta.youtubesubscriptionsbackend.mapper.SubscriptionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {

    private final YouTubeClient youTubeClient;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionService(YouTubeClient youTubeClient, SubscriptionMapper subscriptionMapper) {
        this.youTubeClient = youTubeClient;
        this.subscriptionMapper = subscriptionMapper;
    }

    public List<SubscriptionInfo> getAllSubscriptions(String accessToken) {
        String nextPageToken = null;

        List<Subscription> allSubscriptions = new ArrayList<>();

        do {
            var response =
                    youTubeClient.fetchSubscriptions(accessToken, nextPageToken);

            var currentSubs = response.items();
            allSubscriptions.addAll(currentSubs);

            nextPageToken = response.nextPageToken();
        } while (nextPageToken != null);

        return allSubscriptions.stream()
                .map(subscriptionMapper::toSubscriptionInfo)
                .toList();
    }
}
