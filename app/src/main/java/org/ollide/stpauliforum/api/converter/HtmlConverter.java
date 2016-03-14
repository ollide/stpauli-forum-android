package org.ollide.stpauliforum.api.converter;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public abstract class HtmlConverter<T> implements Converter<ResponseBody, T> {

}
