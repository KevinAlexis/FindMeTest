package com.pilambda.findmerenew.CustomViews;

import android.content.Context;

/**
 * Created by Alexis on 1/26/2018.
 */

public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
