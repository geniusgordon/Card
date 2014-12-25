package gordon.app.card.ui.card;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import gordon.app.card.R;
import gordon.app.card.game.card.Card;
import gordon.app.card.ui.PositionAnimator;
import gordon.app.card.ui.ScaleAnimator;

/**
 * Created by 子皓 on 2014/12/25.
 */
public class EnemyView extends RelativeLayout {
    private int screenWidth;
    private int cardWidth;
    private int cardHeight;
    private Rect card_rect;

    public EnemyView(Context context, int sw) {
        super(context);

        this.setLayoutParams(new ViewGroup.LayoutParams(sw, 700));
        this.setBackgroundColor(Color.BLUE);

        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.poker);
        cardWidth = bd.getBitmap().getWidth();
        cardHeight = bd.getBitmap().getHeight();

        screenWidth = sw;

        card_rect = new Rect(sw/2-cardWidth/2, 100, sw/2+cardWidth/2, 100+cardHeight);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                putCardOnTable(Card.allCards[0][1]);
            }
        });
    }

    public void putCardOnTable(Card c) {
        final CardView target = new CardView(this.getContext(), c);
        target.setX(screenWidth/2-cardWidth/2);
        target.setY(0);
        this.addView(target);
        ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
        va.setInterpolator(new DecelerateInterpolator());
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ValueAnimator v = ValueAnimator.ofFloat(0f, 1f);
                v.setInterpolator(new AccelerateInterpolator());
                ScaleAnimator.scaleTo(target, 0.9f, 1000, v);
                PositionAnimator.moveTo(target, screenWidth / 2 - cardWidth / 2, 600-cardHeight, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ScaleAnimator.scaleTo(target, 1.2f, 500, va);
    }
}
