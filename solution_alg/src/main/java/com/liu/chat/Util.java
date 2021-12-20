package com.liu.chat;

import java.io.Closeable;

public class Util {

    public static void closeAll(Closeable... io) {
        for (Closeable temp : io) {
            try {
                if (null != temp) {
                    temp.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
