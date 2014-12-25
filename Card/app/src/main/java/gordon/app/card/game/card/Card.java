package gordon.app.card.game.card;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class Card {
    public static final int SPADE = 0;
    public static final int HEART = 1;
    public static final int DIAMOND = 2;
    public static final int CLUB = 3;
    public static final Card[][] allCards = new Card[4][14];

    static {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 13; j++)
                allCards[i][j] = new Card(i, j);
    }

    private int suit;
    private int num;

    private Card(int suit, int num) {
        this.suit = suit;
        this.num = num;
    }

    public int getSuit() {
        return suit;
    }

    public int getNum() {
        return num;
    }
}
