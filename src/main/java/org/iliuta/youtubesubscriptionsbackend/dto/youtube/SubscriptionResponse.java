package org.iliuta.youtubesubscriptionsbackend.dto.youtube;

import java.util.List;

public record SubscriptionResponse(
        String nextPageToken,
        List<Subscription> items
) {
}
