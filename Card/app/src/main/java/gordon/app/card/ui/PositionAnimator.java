package gordon.app.card.ui;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by 子皓 on 2014/12/22.
 */
public class PositionAnimator {
    public static float linear(float value, float start, float end) {
        return start + value * (end - start);
    }

    public static void moveTo(final View v, float endX, float endY, int duration) {
        ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
        va.setInterpolator(new DecelerateInterpolator(2));
        moveTo(v, endX, endY, duration, va);
    }

    public static void moveTo(final View v, final float endX, final float endY, int duration, ValueAnimator va) {
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                v.setX(linear((Float) valueAnimator.getAnimatedValue(), v.getX(), endX));
                v.setY(linear((Float) valueAnimator.getAnimatedValue(), v.getY(), endY));
            }
        });
        va.start();
    }
}
