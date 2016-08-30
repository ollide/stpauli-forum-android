package org.ollide.stpauliforum.util;

import org.ollide.stpauliforum.api.ApiModule;

public final class ForumUtils {

    private ForumUtils() {
        // do not instantiate
    }

    public static String urlShowThreadLatest(int topicId) {
        return ApiModule.BASE_URL + "viewtopic.php?t=" + String.valueOf(topicId);
    }

}
