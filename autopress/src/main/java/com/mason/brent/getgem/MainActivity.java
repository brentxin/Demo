package com.mason.brent.getgem;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        timer = new Timer();

    }

    /**
     * 执行shell命令
     *
     * @param cmd
     */
    private void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


//    免费
//    width 0035 000003d2  2+16*13+16*16*3 = 770 + 208 = 978
//    height /dev/input/event2: 0003 0036 00000108 8+0+16*16*1 = 264
//
//    watch 32s
//    0035 000001fc 12+15*16+1*16*16 = 12+256+240 = 508
//            0036 000003dd 13+16*13+16*16*3 = 13+768+208 = 989
//
//    close
//    0035 000003b8 8+11*16+3*16*16 = 8+176+768 = 952
//            0036 00000650 0+5*16+6*16*16 = 80+36+1500 = 1616


//   (40,210) adb shell sendevent /dev/input/event0 3 0 40
//        adb shell sendevent /dev/input/event0 3 1 210
//        adb shell sendevent /dev/input/event0 1 330 1 //touch
//        adb shell sendevent /dev/input/event0 0 0 0       //it must have
//        adb shell sendevent /dev/input/event0 1 330 0 //untouch
//        adb shell sendevent /dev/input/event0 0 0 0 //it must have

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            button.setText(msg.what + "");
        }
    };

    public void click(View view) {
        execShellCmd("getevent -p");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.scheduleAtFixedRate(new MyTask(), 1, 35000);
            }
        }, 3000);

//        for (int i = 0; i < 10; i++) {
//            tapScreen(978,264);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    tapScreen(508,989);
//                }
//            },500);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    tapScreen(952,1616);
//                }
//            },35000);
//            ((Button) view).setText("完成次数: " + i);
//        }
    }


//    参数：–time-limit [秒数]，例如指定录制10S：
//
//    adb shell screenrecord  --time-limit 10 /sdcard/demo.mp4
//    1
//            1
//    指定分辨率
//
//    　　参数：–size [宽*高]，例如指定录制分辨率为720*1280：
//
//    adb shell screenrecord --size 720*1280 /sdcard/demo.mp4
//    1
//            1
//    指定比特率
//
//    　　参数：–bit-rate 比特率，为了发博客方便我们指定比特率为2Mbps：
//
//    adb shell screenrecord --bit-rate 2000000 /sdcard/demo.mp4

    private void tapScreen(int x, int y) {
        execShellCmd("adb shell sendevent /dev/input/event0 3 0 " + x);
        execShellCmd("adb shell sendevent /dev/input/event0 3 1 " + y);
        execShellCmd("adb shell sendevent /dev/input/event0 1 330 1");
        execShellCmd("adb shell sendevent /dev/input/event0 0 0 0");
        execShellCmd("adb shell sendevent /dev/input/event0 1 330 0");
        execShellCmd("adb shell sendevent /dev/input/event0 0 0 0");
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {

            tapScreen(978, 264);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tapScreen(508, 989);
                }
            }, 500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tapScreen(952, 1616);
                }
            }, 32000);

            Message message = new Message();
            message.what++;
            handler.sendMessage(message);

        }
    }
}

