package com.emarsys.mobileengage.endpoint;

import com.emarsys.mobileengage.BuildConfig;

public class Endpoint {
    public static final String ME_BASE_V2 = BuildConfig.ME_BASE_V2_URL;
    public static final String INBOX_BASE = BuildConfig.INBOX_BASE_URL;
    public static final String DEEP_LINK = BuildConfig.DEEP_LINK_BASE_URL;

    public static final String ME_V3_CLIENT_HOST = "https://me-client.eservice.emarsys.net";

    public static final String ME_V3_EVENT_HOST = "https://mobile-events.eservice.emarsys.net";

    public static final String ME_V3_INBOX_HOST = "https://me-inbox.eservice.emarsys.net/v3";

    public static String clientBase(String applicationCode) {
        return "/v3/apps/" + applicationCode + "/client";
    }

    public static String eventBase(String applicationCode) {
        return "/v3/apps/" + applicationCode + "/client/events";
    }

    public static String inboxBase(String applicationCode) {
        return "/apps/" + applicationCode + "/inbox";
    }

    public static String geofencesBase(String applicationCode) {
        return "/v3/apps/" + applicationCode + "/geo-fences";
    }

    public static String inlineInAppBase(String applicationCode) {
        return "/v3/apps/" + applicationCode + "/inline-messages";
    }
}
