package org.ollide.stpauliforum.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopicService {

    @GET("viewtopic.php?postdays=0&postorder=desc")
    Call<String> getPostsInTopicPage(@Query("t") int topicId, @Query("start") int offset);

}
