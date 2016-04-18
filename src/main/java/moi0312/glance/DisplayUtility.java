package moi0312.glance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtility {
    private static final String TAG = DisplayUtility.class.getName();

    private static SparseArray<Integer> fittingSizes;
    public static final int KEY_DISPLAY_SHORT = 0;
    public static final int KEY_DISPLAY_LONG = 1;
    public static final int KEY_STATUSBAR_HEIGHT = 2;
    public static final int KEY_ACTIONBAR_HEIGHT = 3;

    private static Display display;
    private static float density;
    private static int dpi;

    private static Display getDisplay(Context context) {
        if(display == null){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            display = wm.getDefaultDisplay();
        }
        return display;
    }

    /**
     *
     * @param context
     * @return          a SparseArray<Integer>
     */
    public static SparseArray<Integer> getFittingSizes(Context context){
        if(fittingSizes == null){
            fittingSizes = fittingSizeCalculator(context);
            Log.v(TAG, "fittingSizes init.");
        }
        return fittingSizes;
    }

    private static SparseArray<Integer> fittingSizeCalculator(Context context) {
        if(context == null){
            return null;
        }
        //get display and density
        deviceDensity(context); // getDisplay(context);
        //
        SparseArray<Integer> resultSizes = new SparseArray<Integer>();
        Resources res = context.getResources();
        Resources.Theme theme = context.getTheme();
        Point displaySize = new Point();
        display.getSize(displaySize);
        // Be aware if app orientation could be changed by device rotate
        int displayShort, displayLong;
        if( displaySize.x > displaySize.y){ //landscape
            displayLong = displaySize.x;
            displayShort = displaySize.y;
        }else{ //portrait
            displayLong = displaySize.y;
            displayShort = displaySize.x;
        }
        resultSizes.put(KEY_DISPLAY_SHORT, displayShort);
        resultSizes.put(KEY_DISPLAY_LONG, displayLong);
//        resultSizes.put(KEY_DESITY, metrics.density); //not an integer
//        resultSizes.put(KEY_DPI, getDpi(context));

        //
        int statusBarHeight = 0;
        int statusBarHeightId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarHeightId > 0) {
            statusBarHeight = res.getDimensionPixelSize(statusBarHeightId);
        }
        resultSizes.put(KEY_STATUSBAR_HEIGHT, statusBarHeight);

        //
        TypedValue typedValue = new TypedValue();
        int actionBarHeight = 0;
        if (theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, res.getDisplayMetrics());
        }
        resultSizes.put(KEY_ACTIONBAR_HEIGHT, actionBarHeight);

        return resultSizes;
    }

    /**
     * get an int value by DisplayUtility.KEY_XXXX
     * @param context   if context == null will return -1
     * @param key
     * @return
     */
    public static int getValueByKey(Context context, int key){
        if(context == null){ return -1; }
        if(fittingSizes == null){
            getFittingSizes(context);
        }
        return fittingSizes.get(key);
    }

    /**
     * get device density and dpi value then save them to static.
     * @param context
     * @return
     */
    private static boolean deviceDensity(Context context) {
        if(context != null){
            DisplayMetrics metrics = new DisplayMetrics();
            getDisplay(context).getMetrics(metrics);
            dpi = metrics.densityDpi;
            density = metrics.density;
            return true;
        }
        return false;
    }

    /**
     * dots-per-inch. <br/>
     * May be either <br/>120(low), 160(default), <br/>
     * 213(tv), <br/>
     * 240(high), <br/>260, <br/>
     * 320(xhigh), 360, 400, 420, <br/>
     * 480(xxhigh), 560, <br/>
     * 640(xxxhigh)<br/>
     *
     * @param context
     * @return
     */
    public static int getDpi(Context context) {
        if(dpi <= 0){
            deviceDensity(context);
        }
        return dpi;
    }

    /**
     * get density float (like 1.33, 2.0, ... etc.)
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        if(density <= 0){
            deviceDensity(context);
        }
        return density;
    }

    /**
     * convert dp to pixel
     * @param context
     * @param dp
     * @return float
     */
    public static int dpToPx(Context context, int dp){
        if(context != null){
            return (int)(getDensity(context) * dp);
        }
        return -1;
    }

    /**
     * convert pixel to dp
     * @param context
     * @param px
     * @return
     */
    public static int pxToDp(Context context, int px){
        if(context != null){
            return (int)(px / getDensity(context));
        }
        return -1;
    }
}
