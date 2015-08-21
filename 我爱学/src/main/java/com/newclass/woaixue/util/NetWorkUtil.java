package com.newclass.woaixue.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by liaorubei on 2015/8/20.
 */
public class NetWorkUtil {


    public static void connect(String url, ConnectionCallback callback) {
        try {

            //子线程
            InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }

            //主线程
            callback.getResult(sb.toString());

            reader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public interface ConnectionCallback {
        void getResult(String result);
    }
}
