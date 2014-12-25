package gordon.app.card.ui.card;

import android.content.Context;
import android.widget.ImageView;

import gordon.app.card.game.card.Card;

/**
 * Created by 子皓 on 2014/12/22.
 */
public class CardView extends ImageView {
    private float posX;
    private float posY;
    private Card card;

    public CardView(Context c, Card card) {
        super(c);
        this.setImageResource(CardResource.cardRes[card.getSuit()][card.getNum()]);
        this.card = card;
    }

    public void setXY(float x, float y) {
        this.setX(x);
        this.setY(y);
        posX = x;
        posY = y;
    }

    public void setPosX(float x) {
        posX = x;
    }

    public void setPosY(float y) {
        posY = y;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public Card getCard() {
        return card;
    }
}
