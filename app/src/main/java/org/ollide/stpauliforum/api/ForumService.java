package org.ollide.stpauliforum.api;

import org.ollide.stpauliforum.api.network.ForceCacheRequestInterceptor;
import org.ollide.stpauliforum.model.xml.TopicXmlList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ForumService {

    @GET("rdf/rdf.php?type=latest&titlepattern=__DATE__;__FORUM__;__TITLE__&chars=80")
    Call<TopicXmlList> getTopicsInForum(@Query("fid") int forumId, @Query("count") int count);

    @Headers(ForceCacheRequestInterceptor.HEADER_FORCE_CACHED)
    @GET("rdf/rdf.php?type=latest&titlepattern=__DATE__;__FORUM__;__TITLE__&chars=80")
    Call<TopicXmlList> getTopicsInForumCached(@Query("fid") int forumId, @Query("count") int count);

}
