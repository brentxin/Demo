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
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.SocketInputBuffer;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
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

//        BasicHttpEntity httpEntity = (BasicHttpEntity) ((BasicHttpEntityEnclosingRequest) request).getEntity();

        BasicHttpContext basicHttpContext = (BasicHttpContext) context;
        BasicHttpEntityEnclosingRequest rq = (BasicHttpEntityEnclosingRequest) basicHttpContext.getAttribute("http.request");
        BasicHttpEntity httpEntity = (BasicHttpEntity) rq.getEntity();
        ContentLengthInputStream is = (ContentLengthInputStream) httpEntity.getContent();

        byte[] byt = new byte[(int) httpEntity.getContentLength()];
        try {
            is.read(byt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        do {
            i++;
        } while (Math.pow(2, i) < httpEntity.getContentLength());
        byte[] result = new byte[(int) Math.pow(2, i)];
        System.arraycopy(byt, 0, result, result.length - byt.length, byt.length);

        ByteBuffer bb = ByteBuffer.wrap(byt);
        User user = User.getRootAsUser(bb);
        String str = user.login();
        //user.login() +
        StringEntity stringEntity = new StringEntity(" : 请求已成功处理", "utf-8");
        response.setEntity(stringEntity);


    }
}