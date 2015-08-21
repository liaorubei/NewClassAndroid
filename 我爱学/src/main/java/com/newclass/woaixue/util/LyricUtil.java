package com.newclass.woaixue.util;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by liaorubei on 2015/8/21.
 */
public class LyricUtil {


    public static Lyric parse(String lrc) {


        return new Lyric();
    }

    public static class Lyric {

        private HashMap<String,String> signs;

        private TreeMap<String,String> lines;

    }
}
