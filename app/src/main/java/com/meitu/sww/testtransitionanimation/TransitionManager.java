package com.meitu.sww.testtransitionanimation;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * @author ShaoWenWen
 * @date 2019/5/23
 */
public class TransitionManager {

    public static void startActivityWithTransitionAnimation(Activity activity, Intent intent, View view){
//        int viewHalfWidth = (view.getRight() -view.getLeft())/2;
//        int viewCenterPointY = (view.getBottom() - view.getTop())/2;
        int[] viewLocation = new int[2];
        view.getLocationInWindow(viewLocation);

        intent.putExtra("position_x",viewLocation[0]+view.getWidth()/2);
        intent.putExtra("position_y",viewLocation[1]+view.getHeight()/2);

        activity.startActivity(intent);
    }

}
