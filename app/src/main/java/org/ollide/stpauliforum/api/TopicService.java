package org.ollide.stpauliforum.api;

import org.ollide.stpauliforum.model.html.PostList;
import org.ollide.stpauliforum.model.html.TopicList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopicService {

    @GET("viewtopic.php?postdays=0&postorder=desc")
    Call<PostList> getPostsInTopicPage(@Query("t") int topicId, @Query("start") int offset);

    /**
     * This is usually used for pagination.
     * One page contains 15 entries, so to request page n use:
     * (n - 1) * 15 for offset.
     *
     * @param topicId the topic to find posts for
     * @param offset  the paging offset (15 per page)
     * @return the requested PostList
     */
    @GET("viewtopic.php?postdays=0&postorder=asc")
    Call<PostList> getPostsAscStartingAt(@Query("t") int topicId, @Query("start") int offset);


    @GET("viewtopic.php")
    Call<PostList> getPostsByPostId(@Query("p") int topicId);

    @GET("recent.php?selorder=laday")
    Call<TopicList> getActiveTopicsInLastDays(@Query("nodays") int days);

}
