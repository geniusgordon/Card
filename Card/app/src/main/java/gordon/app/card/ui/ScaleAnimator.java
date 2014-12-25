package gordon.app.card.ui;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class ScaleAnimator {
    public static float linear(float value, float start, float end) {
        return start + value * (end - start);
    }

    public static void scaleTo(final View v, float scale, int duration) {
        ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
        scaleTo(v, scale, duration, va);
    }

    public static void scaleTo(final View v, final float scale, int duration, ValueAnimator va) {
        va.setInterpolator(new DecelerateInterpolator(2));
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                v.setScaleX(linear((Float) valueAnimator.getAnimatedValue(), v.getScaleX(), scale));
                v.setScaleY(linear((Float) valueAnimator.getAnimatedValue(), v.getScaleY(), scale));
            }
        });
        va.start();
    }
}
