package com.mason.brent.toolbar;

import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import java.io.IOException;

public interface MyServerRequestHandler {

    /**
     * When is the client request is triggered.
     *
     * @param request  {@link org.apache.http.HttpRequest}.
     * @param response {@link HttpResponse}.
     * @param context  {@link HttpContext}.
     * @throws HttpException may be.
     * @throws IOException   read data.
     */
    void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException;

}