package org.ollide.stpauliforum.api.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

/**
 * stpauli-forum.de does not include a charset in the Content-Type
 * response header but uses ISO-8859-1, this filter sets the header
 * accordingly so retrofit uses the correct encoding.
 */
public class ContentTypeResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("ContentTypeResponseInterceptor: intercepting...");

        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .removeHeader("Content-Type")
                .header("Content-Type", "text/html; charset=ISO-8859-1")
                .build();
    }

}
