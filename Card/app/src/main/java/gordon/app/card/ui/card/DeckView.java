package gordon.app.card.ui.card;

import android.content.Context;
import android.widget.ImageView;

import gordon.app.card.R;
import gordon.app.card.game.card.Card;

/**
 * Created by 子皓 on 2014/12/25.
 */
public class DeckView extends ImageView {
    public DeckView(Context context) {
        super(context);
        this.setImageResource(R.drawable.deck);
    }
}
