package gordon.app.card.game.card;

import java.util.Comparator;

import gordon.app.card.ui.card.CardView;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class CardComparator implements Comparator<Card> {

    private int max;

    public CardComparator(int max) {
        this.max = max;
    }

    @Override
    public int compare(Card a, Card b) {
        if (a.getSuit() != b.getSuit()) return a.getSuit() - b.getSuit();
        int x = a.getNum();
        int y = b.getNum();
        if (x <= max) x += 13;
        if (y <= max) y += 13;
        return x-y;
    }
}
