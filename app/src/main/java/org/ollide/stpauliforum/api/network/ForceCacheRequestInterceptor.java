package org.ollide.stpauliforum.api.network;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class ForceCacheRequestInterceptor implements Interceptor {

    public static final String X_CACHE_CONTROL = "x-app-cache-control";
    public static final String FORCE_CACHED = "force-cache";

    public static final String HEADER_FORCE_CACHED = X_CACHE_CONTROL + ": " + FORCE_CACHED;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("ForceCacheRequestInterceptor: intercepting...");

        Request request = chain.request();

        if (FORCE_CACHED.equals(request.header(X_CACHE_CONTROL))) {
            Timber.d("Enforcing FORCE_CACHE header on request");
            request = request.newBuilder()
                    .removeHeader(X_CACHE_CONTROL)
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        return chain.proceed(request);
    }
}
