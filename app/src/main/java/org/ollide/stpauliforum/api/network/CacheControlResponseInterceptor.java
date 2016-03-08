package org.ollide.stpauliforum.api.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Dangerous interceptor that rewrites the server's cache-control header.
 */
public class CacheControlResponseInterceptor implements Interceptor {

    // 5 Minutes
    private static final int MAX_AGE = 5 * 60;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("CacheControlResponseInterceptor: intercepting...");

        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + MAX_AGE)
                .build();
    }

}
