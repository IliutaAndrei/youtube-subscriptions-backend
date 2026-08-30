package org.iliuta.youtubesubscriptionsbackend.mapper;

import org.iliuta.youtubesubscriptionsbackend.dto.SubscriptionInfo;
import org.iliuta.youtubesubscriptionsbackend.dto.youtube.Subscription;
import org.springframework.stereotype.Component;


@Component
public class SubscriptionMapper {

    public SubscriptionInfo toSubscriptionInfo(Subscription subscription) {
        return new SubscriptionInfo(
                        subscription.snippet().title(),
                        subscription.snippet().publishedAt()
        );

    }
}
