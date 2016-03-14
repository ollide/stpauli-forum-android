package org.ollide.stpauliforum.api.converter;

import org.ollide.stpauliforum.model.html.PostList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class PhpBbHtmlConverterFactory extends Converter.Factory {

    private static final Type TYPE_POST_LIST = PostList.class;

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        Converter<ResponseBody, ?> converter = null;
        if (TYPE_POST_LIST == type) {
            converter = new PostListResponseBodyConverter();
        }

        return converter;
    }

}
