package org.iliuta.youtubesubscriptionsbackend.controller;


import org.iliuta.youtubesubscriptionsbackend.dto.SubscriptionInfo;
import org.iliuta.youtubesubscriptionsbackend.service.SubscriptionService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionInfo> getAllSubscriptions(
           @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        return subscriptionService.getAllSubscriptions(accessToken);
    }
}
