package com.mason.brent.toolbar;

import com.yanzhenjie.andserver.AndServerRequestHandler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by brent on 2016/7/6,006.
 */
public class AndServerPingHandler implements AndServerRequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        response.setEntity(new StringEntity("ok", "utf-8"));
    }
}
