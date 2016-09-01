package org.ollide.stpauliforum.util;

import org.ollide.stpauliforum.api.ApiModule;

public final class ForumUtils {

    private ForumUtils() {
        // do not instantiate
    }

    public static String urlShowThreadLatest(int topicId) {
        return ApiModule.BASE_URL + "viewtopic.php?t=" + String.valueOf(topicId);
    }

    /**
     * Calculates the start offset for the given page.
     * One page consists of 15 entries, so the formula is as follows:
     * (page - 1) * 15 = offset
     * @param page the page to calculate the start offset
     * @return the start offset
     */
    public static int pageToEntryOffset(int page) {
        return (page - 1) * 15;
    }

}
