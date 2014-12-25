package gordon.app.card.ui.card;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import gordon.app.card.R;
import gordon.app.card.game.card.Card;
import gordon.app.card.game.card.CardComparator;
import gordon.app.card.ui.PositionAnimator;
import gordon.app.card.ui.ScaleAnimator;
import gordon.app.card.ui.card.state.StateMachine;

/**
 * Created by 子皓 on 2014/12/22.
 */
public class PlayerView extends RelativeLayout {
    private final int screenWidth;
    private final int cardWidth;
    private final int cardHeight;

    private boolean ready;
    private boolean turn;
    private StateMachine machine;
    private List<CardView> sm_cards;
    private List<CardView> bg_cards;
    private Rect sm_rect;
    private Rect bg_rect;
    private Rect put_rect;
    private CardView cardOnTable;

    public PlayerView(Context context, int sw, Card[] cards) {
        super(context);
        screenWidth = sw;

        int h = 1200;

        this.setLayoutParams(new ViewGroup.LayoutParams(sw, h));
        this.setBackgroundColor(Color.RED);

        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.poker);
        cardWidth = bd.getBitmap().getWidth();
        cardHeight = bd.getBitmap().getHeight();

        machine = new StateMachine(this);

        sm_cards = new LinkedList<CardView>();
        bg_cards = new LinkedList<CardView>();
        sm_rect = new Rect(100, h-50-cardHeight, sw-100, h-50);
        bg_rect = new Rect(50, h-50-(int)(cardHeight*1.5), sw-50, h-50);
        put_rect = new Rect(sw/2-cardWidth/2-100, 0, sw/2+cardWidth/2+100, sm_rect.top-150);

        Arrays.sort(cards, new CardComparator(1));
        for (Card card: cards) {
            sm_cards.add(new CardView(context, card));
            CardView c = new CardView(context, card);
            c.setVisibility(INVISIBLE);
            c.setScaleX(1.5f);
            c.setScaleY(1.5f);
            bg_cards.add(c);
        }
        cardOnTable = null;
        ready = false;
        turn = false;
    }

    private void addCardsToView() {
        for (int i = 0; i < sm_cards.size(); i++) {
            float x = sm_rect.left + (sm_rect.right - cardWidth - sm_rect.left) / (sm_cards.size()-1) * i;
            sm_cards.get(i).setXY(x, sm_rect.top);
            this.addView(sm_cards.get(i));
        }
        for (int i = 0; i < bg_cards.size(); i++) {
            float x = bg_rect.left + (bg_rect.right - cardWidth - bg_rect.left) / (bg_cards.size()-1) * i;
            bg_cards.get(i).setXY(x, bg_rect.top);
            this.addView(bg_cards.get(i));
        }
    }

    public void cardChose(int index) {
        CardView sm = sm_cards.get(index);
        CardView bg = bg_cards.get(index);
        sm.setX(bg.getX());
        sm.setY(bg.getY());
        sm.setScaleX(1.5f);
        sm.setScaleY(1.5f);
        sm.setVisibility(INVISIBLE);
        bg_cards.get(index).setVisibility(VISIBLE);
    }

    public void cardNotChose(int index) {
        CardView sm = sm_cards.get(index);
        sm.setVisibility(VISIBLE);
        PositionAnimator.moveTo(sm, sm.getPosX(), sm.getPosY(), 500);
        ScaleAnimator.scaleTo(sm, 1f, 500);
        bg_cards.get(index).setVisibility(INVISIBLE);
    }

    public void cardStartDrag(int index) {
        ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
        va.setInterpolator(new AccelerateInterpolator(3));
        ScaleAnimator.scaleTo(sm_cards.get(index), 1f, 500);
        sm_cards.get(index).setVisibility(VISIBLE);
        bg_cards.get(index).setVisibility(INVISIBLE);
    }

    public void cardDragged(int index, float x, float y) {
        CardView target = sm_cards.get(index);
        if (y > cardHeight/2+30) {
            target.setX(x - cardWidth / 2);
            target.setY(y - cardHeight / 2);
        } else {
            target.setX(x - cardWidth / 2);
            target.setY(30);
        }
    }

    public void cardNotDragged(int index) {
        cardNotChose(index);
    }

    public void putCardOnTable(int index) {
        final CardView target = sm_cards.get(index);
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
                PositionAnimator.moveTo(target, screenWidth/2-cardWidth/2, 50, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ScaleAnimator.scaleTo(target, 1.2f, 500, va);
        cardOnTable = sm_cards.get(index);

        removeCard(index);
        realignCards();
    }

    public void moveCardAway() {
        if (cardOnTable == null) return;

        ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                removeView(cardOnTable);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        PositionAnimator.moveTo(cardOnTable, cardOnTable.getX(), -cardHeight, 500, va);
    }

    public void removeCard(int index) {
        sm_cards.remove(index);
        this.removeView(bg_cards.get(index));
        bg_cards.remove(index);
    }

    public void realignCards() {
        if (sm_cards.size() == 0) return;
        if (sm_cards.size() == 1) {
            PositionAnimator.moveTo(sm_cards.get(0), screenWidth/2 - cardWidth/2, sm_rect.top, 300);
            sm_cards.get(0).setPosX(screenWidth/2 - cardWidth/2);
            bg_cards.get(0).setXY(screenWidth/2 - cardWidth/2, bg_rect.top);
            return;
        }
        for (int i = 0; i < sm_cards.size(); i++) {
            float x = sm_rect.left + (sm_rect.right - cardWidth - sm_rect.left) / (sm_cards.size()-1) * i;
            PositionAnimator.moveTo(sm_cards.get(i), x, sm_rect.top, 300);
            sm_cards.get(i).setPosX(x);
            x = bg_rect.left + (bg_rect.right - cardWidth - bg_rect.left) / (bg_cards.size()-1) * i;
            bg_cards.get(i).setXY(x, bg_rect.top);
        }
    }

    public void dealCardEnd() {
        addCardsToView();
        for (CardView c: sm_cards) {
            c.setY(1200 + cardHeight);
            ValueAnimator va = ValueAnimator.ofFloat(0f,1f);
            va.setInterpolator(new DecelerateInterpolator(5));
            va.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    ready = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            PositionAnimator.moveTo(c, c.getPosX(), c.getPosY(), 1500, va);
        }
    }

    public boolean inCardArea(float x, float y) {
        return sm_rect.contains((int)x, (int)y);
    }

    public boolean inPutArea(float x, float y) {
        return put_rect.contains((int)x, (int)y);
    }

    public int getIndexFromX(float x) {
        if (sm_cards.size() == 1)
            return 0;
        int index = ((int)x - sm_rect.left) / ((sm_rect.right-cardWidth-sm_rect.left)/(sm_cards.size()-1));
        if (index >= sm_cards.size())
            return sm_cards.size()-1;
        return index;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean b) {
        turn = b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return ready && sm_cards.size() > 0 && machine.handleInput(ev);
    }
}
