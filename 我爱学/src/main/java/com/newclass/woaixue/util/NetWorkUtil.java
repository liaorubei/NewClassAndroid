package com.newclass.woaixue.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liaorubei on 2015/8/20.
 */
public class NetWorkUtil {


    public static void connect(final String url, final ConnectionCallback callback) {


        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    if (con.getResponseCode() == 200) {
                        InputStream inputStream = con.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, len);
                        }
                        Log.i("e", "run " + outputStream.toString());

                        //关闭流
                        outputStream.close();//此流关闭无效
                        inputStream.close();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        ).start();


    }

    public interface ConnectionCallback {
        void getResult(String result);
    }
}
