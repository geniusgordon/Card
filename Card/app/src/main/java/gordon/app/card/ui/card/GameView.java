package gordon.app.card.ui.card;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import gordon.app.card.R;
import gordon.app.card.game.card.Card;
import gordon.app.card.game.card.Deck;
import gordon.app.card.game.state.StateMachine;
import gordon.app.card.ui.PositionAnimator;

/**
 * Created by 子皓 on 2014/12/25.
 */
public class GameView extends RelativeLayout {
    private StateMachine machine;
    private int screenWidth;
    private int screenHeight;
    private int cardHeight;
    private int cardWidth;

    private PlayerView pv;

    public GameView(Context context, int sw, int sh) {
        super(context);
        this.setLayoutParams(new ViewGroup.LayoutParams(sw, sh));

        screenWidth = sw;
        screenHeight = sh;

        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.poker);
        cardWidth = bd.getBitmap().getWidth();
        cardHeight = bd.getBitmap().getHeight();

        Deck deck = new Deck();
        deck.shuffle();

        pv = new PlayerView(context, sw, deck.deal(13));
        pv.setY(sh-1200);
        this.addView(new EnemyView(context, sw));
        this.addView(pv);

        DeckView d = new DeckView(context);
        d.setX(-cardHeight/2+30);
        d.setY(sh - 1200 - cardWidth);
        d.setRotation(270);
        this.addView(d);

        machine = new StateMachine();

        dealCard();
    }

    public void dealCard() {
        for (int i = 0; i < 13; i++) {
            final CardView c = new CardView(getContext(), Card.allCards[0][0]);
            c.setX(-cardHeight / 2 + 30);
            c.setY(screenHeight - 1200 - cardWidth);
            c.setRotation(270);
            ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
            va.setStartDelay(300*i);
            va.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    addView(c);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    removeView(c);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            if (i == 12) {
                va.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        pv.dealCardEnd();
                        machine.setState(machine.getBid());
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
            PositionAnimator.moveTo(c, screenWidth/2, screenHeight, 500, va);
        }
        for (int i = 0; i < 13; i++) {
            final CardView c = new CardView(getContext(), Card.allCards[0][0]);
            c.setX(-cardHeight / 2 + 30);
            c.setY(screenHeight - 1200 - cardWidth);
            c.setRotation(270);
            ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
            va.setStartDelay(300*i+100);
            va.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    addView(c);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    removeView(c);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            PositionAnimator.moveTo(c, screenWidth/2, -cardWidth-100, 500, va);
        }
    }
}
