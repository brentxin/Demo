package com.mason.brent.toolbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;

/**
 * Created by brent on 2016/8/8,008.
 */
public interface MyHttpRequest extends HttpRequest{
    HttpEntity getEntity();

    void setEntity(HttpEntity var1);
}
