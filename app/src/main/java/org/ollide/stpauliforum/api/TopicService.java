package org.ollide.stpauliforum.api;

import org.ollide.stpauliforum.model.html.PostList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopicService {

    @GET("viewtopic.php?postdays=0&postorder=desc")
    Call<PostList> getPostsInTopicPage(@Query("t") int topicId, @Query("start") int offset);

    @GET("viewtopic.php")
    Call<PostList> getPostsByPostId(@Query("p") int topicId);

}
