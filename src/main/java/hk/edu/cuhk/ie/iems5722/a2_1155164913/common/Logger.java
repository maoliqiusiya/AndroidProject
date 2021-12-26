package hk.edu.cuhk.ie.iems5722.a2_1155164913.common;

import android.text.TextUtils;
import android.util.Log;


public class Logger {

    public static void e(String tag, String log) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(log)) {
            Log.e(tag, log);
        }
    }

}