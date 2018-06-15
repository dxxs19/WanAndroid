package com.wei.wanandroid.activity.http.retrofit.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.wei.wanandroid.activity.http.retrofit.model.FuliBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author: WEI
 * @date: 2018/6/14
 */
public class StringGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    StringGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException
    {
        String response = value.string();
        Log.e(getClass().getSimpleName(), "response : " + response);
        FuliBean bean = gson.fromJson(response, FuliBean.class);
        if (bean.isError())
        {
            Log.e(getClass().getSimpleName(), "网络请求结果出错！");
            value.close();
            /// TODO 出错类型可以在这里拦截，做处理。例如，抛出异常
        }
        else
        {
            Log.e(getClass().getSimpleName(), "数据请求成功！");
        }

        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }
}
