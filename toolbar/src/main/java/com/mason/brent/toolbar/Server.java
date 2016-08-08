package com.mason.brent.toolbar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mason.brent.toolbar.AndServerPingHandler;
import com.mason.brent.toolbar.AndServerTestHandler;
import com.mason.brent.toolbar.AndServerUploadHandler;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.AndServerBuild;


/**
 * Created by brent on 2016/8/8,008.
 */
public class Server extends Service {

    /**
     * AndServer。
     */
    private AndServer mAndServer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (mAndServer == null || !mAndServer.isRunning()) {// 服务器没启动。
                    startAndServer();// 启动服务器。
            }
            }
        }).start();
    }

    /**
     * 启动服务器。
     */
    private void startAndServer() {
        if (mAndServer == null || !mAndServer.isRunning()) {

            AndServerBuild andServerBuild = AndServerBuild.create();
            andServerBuild.setPort(4477);// 指定端口号。

            // 添加普通接口。
            andServerBuild.add("ping", new AndServerPingHandler());// 到时候在浏览器访问是：http://localhost:4477/ping
            andServerBuild.add("test", new AndServerTestHandler());// 到时候在浏览器访问是：http://localhost:4477/test

            // 添加接受客户端上传文件的接口。
            andServerBuild.add("upload", new AndServerUploadHandler());// 到时候在浏览器访问是：http://localhost:4477/upload
            mAndServer = andServerBuild.build();

            // 启动服务器。
            mAndServer.launch();
            System.out.println("AndServer已经成功启动");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAndServer != null && mAndServer.isRunning()) {
            mAndServer.close();
        }
    }
}
