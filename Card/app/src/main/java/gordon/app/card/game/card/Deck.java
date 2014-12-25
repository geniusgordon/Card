package gordon.app.card.game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class Deck {
    private List<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++)
            for (int j = 1; j <= 13; j++)
                deck.add(Card.allCards[i][j]);
    }

    public void shuffle() {
        Random r = new Random();
        for (int i = 0; i < deck.size(); i++) {
            int j = r.nextInt(52);
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public Card[] deal(int num) {
        Card[] ret = new Card[num];
        for (int i = 0; i < num; i++)
            ret[i] = getTop();
        return ret;
    }

    public Card getTop() {
        Card ret = deck.get(0);
        deck.remove(0);
        return ret;
    }
}
