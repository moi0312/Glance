package moi0312.glance.util;

import android.view.View;

public class ViewUtility {
    /**
     *
     * @param visibility    0(View.VISIBLE) || 4(View.INVISIBLE) || 8(View.GONE)
     * @param views
     */
    public static void visible(int visibility, View... views) {
        if(visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            for (int i = 0; i < views.length; i++) {
                View v = views[i];
                if(v != null && v.getVisibility() != visibility){
                    v.setVisibility(visibility);
                }
            }
        }
    }
}
