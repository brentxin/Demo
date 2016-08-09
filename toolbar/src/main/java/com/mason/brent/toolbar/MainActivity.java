package com.mason.brent.toolbar;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.flatbuffers.FlatBufferBuilder;
import com.mason.brent.toolbar.bean.Repo;
import com.mason.brent.toolbar.bean.ReposList;
import com.mason.brent.toolbar.bean.User;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.AndServerBuild;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.HttpResponse;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity implements OnResponseListener {
//    int i = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setIcon(R.drawable.ic_toolbar);
//        getSupportActionBar().setTitle("Title");
//
//        Button tv = (Button) findViewById(R.id.tv);
//        final LinearLayout scrollView = (LinearLayout) findViewById(R.id.scroll);
//
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"This is a toast.",Toast.LENGTH_LONG).show();
//                TextView textView = new TextView(MainActivity.this);
//                textView.setText("TextView:  "+ i++);
//                scrollView.addView(textView);
//            }
//        });
//    }


    private RawDataReader rawDataReader;
    TextView tv;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(onClickListener);
        findViewById(R.id.btn_stop).setOnClickListener(onClickListener);
        findViewById(R.id.btn_senddata).setOnClickListener(onClickListener);
        tv = (TextView) findViewById(R.id.tv);
        rawDataReader = new RawDataReader(this);

        this.startService(new Intent(this, Server.class));
        queue = NoHttp.newRequestQueue();
        queue.start();

    }

    /**
     * 按钮监听。
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_start) {
                rawDataReader.loadBytes(R.raw.reps_json).subscribe(new SimpleObserver<byte[]>() {
                    @Override
                    public void onNext(byte[] bytes) {
                        loadFlatBuffer(bytes);
                    }
                });

//                if (mAndServer == null || !mAndServer.isRunning()) {// 服务器没启动。
//                    startAndServer();// 启动服务器。
//                } else {
//                    Toast.makeText(MainActivity.this, "AndServer已经启动，请不要重复启动。", Toast.LENGTH_LONG).show();
//                }
            } else if (v.getId() == R.id.btn_stop) {
//                if (mAndServer == null || !mAndServer.isRunning()) {
//                    Toast.makeText(MainActivity.this, "AndServer还没有启动。", Toast.LENGTH_LONG).show();
//                } else {// 关闭服务器。
//                    mAndServer.close();
//                    Toast.makeText(MainActivity.this, "AndServer已经停止。", Toast.LENGTH_LONG).show();
//                }
            } else if (v.getId() == R.id.btn_senddata) {
                FlatBufferBuilder builder = new FlatBufferBuilder(0);
                int user = User.createUser(builder, builder.createString("sniper"),
                        10001L,
                        builder.createString("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png"),
                        builder.createString("1001"),
                        builder.createString("https://www.baidu.com/"),
                        builder.createString("http://news.baidu.com/"),
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        builder.createString("http://news.baidu.com/"),
                        true);

                builder.finish(user); // You could also call `Monster.finishMonsterBuffer(builder, orc);`.

                // We now have a FlatBuffer that can be stored on disk or sent over a network.

                // ...Code to store to disk or send over a network goes here...

                // Instead, we are going to access it right away, as if we just received it.

                ByteBuffer buf = builder.dataBuffer();

                // Get access to the root:


                ByteArrayInputStream bais = new ByteArrayInputStream(buf.array(), builder.dataBuffer().position(), builder.offset());
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int len = 0;
//
//                try {
//                    while ((len = bais.read()) != -1) {
//                        baos.write(len);
//                    } // close while
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                byte[] b1 = baos.toByteArray();




                Request<String> request = NoHttp.createStringRequest("http://192.168.199.73:4477/test", RequestMethod.POST);
                request.setDefineRequestBody(bais, "BIN");

                queue.add(0, request, MainActivity.this);

                User mUser = User.getRootAsUser(buf);
//                User mUser = User.getRootAsUser(ByteBuffer.wrap(b1));

                Toast.makeText(MainActivity.this, mUser.login(), Toast.LENGTH_LONG).show();

//                CallServer.getRequestInstance().add(this, 0, request, this, false, true);
            }
        }
    };

    // 这里为了简单就写在Activity中了，强烈建议写在服务中。


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stopService(new Intent(this, Server.class));
    }

    private void loadFlatBuffer(byte[] bytes) {
        long startTime = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        ReposList reposListFlat = ReposList.getRootAsReposList(bb);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < reposListFlat.reposLength(); i++) {
            Repo repos = reposListFlat.repos(i);
            sb.append("Repo " + i + ", id: " + repos.id() + " : " + repos.fullName() + "\n");
        }
        long endTime = System.currentTimeMillis() - startTime;
        sb.append("Elements: " + reposListFlat.reposLength() + ": load time: " + endTime + "ms");
        tv.setText(sb.toString());

    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response response) {

    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }

    @Override
    public void onFinish(int what) {

    }

}
