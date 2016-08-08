package com.mason.brent.toolbar;

import com.mason.brent.toolbar.bean.User;
import com.yanzhenjie.andserver.AndServerRequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.tools.HeaderUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by brent on 2016/7/6,006.
 */

public class AndServerTestHandler implements AndServerRequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        // 拿到客户端参数key-value。
//        Map<String, String> params = HttpRequestParser.parse(request);
//
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            stringBuilder.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
//        }
//        System.out.println("客户端提交的参数：" + stringBuilder.toString());
//
//        StringEntity stringEntity = new StringEntity("请求已成功处理", "utf-8");
//        response.setEntity(stringEntity);
        // 如果要更新UI，这里用Handler或者广播发送过去。

        HttpEntity httpEntity = ((BasicHttpEntityEnclosingRequest) request).getEntity();
        InputStream is = httpEntity.getContent();
        byte[] byt = new byte[is.available()];
        try {
            is.read(byt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.wrap(byt);
        User user = User.getRootAsUser(bb);
        StringEntity stringEntity = new StringEntity(user.login() + " : 请求已成功处理", "utf-8");
        response.setEntity(stringEntity);


    }
}