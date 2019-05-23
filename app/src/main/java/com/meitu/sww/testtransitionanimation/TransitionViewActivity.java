package com.meitu.sww.testtransitionanimation;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * @author ShaoWenWen
 * @date 2019/5/23
 */
public class TransitionViewActivity extends AppCompatActivity {

    RelativeLayout rootView;
    protected int mPositionX, mPositionY;
//    Handler handler = new Handler(Looper.getMainLooper());
    protected boolean inEndAniming = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_view);
        rootView = findViewById(R.id.root_view);
        enterTransition(rootView);
    }

    /**
     * 设置转场动画
     */
    protected void enterTransition(View view) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPositionX = getIntent().getIntExtra("position_x", 0);
            mPositionY = getIntent().getIntExtra("position_y", 0);
            if (mPositionX != 0 && mPositionY != 0) {
                rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int
                            oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);
                        Animator animator = createRevealAnimator(false, mPositionX, mPositionY);
                        animator.start();
                    }
                });
            }
        }
    }

    protected Animator createRevealAnimator(boolean reversed, int x, int y) {
        float hypot = (float) Math.hypot(rootView.getHeight(), rootView.getWidth());
        float startRadius = reversed ? hypot: 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(
                    rootView, x, y,
                    startRadius,
                    endRadius);
            animator.setDuration(500);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            if (reversed) {
                animator.addListener(animatorListener);
            }
        }
        return animator;
    }

    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            rootView.setVisibility(View.INVISIBLE);
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                rootView != null &&
                mPositionX != 0 &&
                mPositionY != 0) {
            if (!inEndAniming) {
                inEndAniming = true;
                Animator animator = createRevealAnimator(true, mPositionX, mPositionY);
                animator.start();
            }
        } else {
            super.onBackPressed();
        }
    }

}
