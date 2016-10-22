package com.xu.simplenetwork;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Xu on 2016/10/22.
 */

public class CloseUtils {

    private CloseUtils() {

    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
