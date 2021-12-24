package hk.edu.cuhk.ie.iems5722.a2_1155164913.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;
    public static void showMsg(Context context, String msg){
        if(toast == null){
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
}
