package org.ollide.stpauliforum.api;

import android.content.Context;

import org.ollide.stpauliforum.api.converter.PhpBbHtmlConverterFactory;
import org.ollide.stpauliforum.api.http.PersistentCookieStore;
import org.ollide.stpauliforum.api.network.CacheControlResponseInterceptor;
import org.ollide.stpauliforum.api.network.ContentTypeResponseInterceptor;
import org.ollide.stpauliforum.api.network.ForceCacheRequestInterceptor;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class ApiModule {

    public static final String BASE_URL = "http://www.stpauli-forum.de/";

    private static final long CACHE_SIZE = 50 * 1024 * 1024;

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, CACHE_SIZE);

        CookieHandler cookieHandler = new CookieManager(
                new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);

        return new OkHttpClient.Builder()
                .cache(cache)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .addInterceptor(new ForceCacheRequestInterceptor())
                .addNetworkInterceptor(new CacheControlResponseInterceptor())
                .addNetworkInterceptor(new ContentTypeResponseInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(new PhpBbHtmlConverterFactory())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ForumService provideForumService(Retrofit retrofit) {
        return retrofit.create(ForumService.class);
    }

    @Provides
    @Singleton
    TopicService provideTopicService(Retrofit retrofit) {
        return retrofit.create(TopicService.class);
    }

    @Provides
    @Singleton
    LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }

    @Provides
    @Singleton
    LoginClient provideLoginClient() {
        return new LoginClient();
    }

}
