package guru.voidmain.mappolydrawer.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by voidmain on 16/6/14.
 */
public class Utils {
    public static void vibratePhone(Context context, int duration) {
        // 手机震动
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    public static void vibratePhoneShort(Context context) {
        vibratePhone(context, 10);
    }
}
