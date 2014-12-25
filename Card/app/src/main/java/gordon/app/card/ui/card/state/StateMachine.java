package gordon.app.card.ui.card.state;

import android.view.MotionEvent;

import gordon.app.card.ui.card.CardView;
import gordon.app.card.ui.card.PlayerView;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class StateMachine {
    private PlayerView pv;
    private CardState state;
    private NormalState normal;
    private ChooseCardState choose;
    private DragCardState drag;

    public StateMachine(PlayerView p) {
        this.pv = p;
        normal = new NormalState(pv, this);
        choose = new ChooseCardState(pv, this);
        drag = new DragCardState(pv, this);
        state = normal;
    }

    public boolean handleInput(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return state.actionMove(x,y);
            case MotionEvent.ACTION_DOWN:
                return state.actionDown(x,y);
            case MotionEvent.ACTION_UP:
                return state.actionUp(x,y);
        }
        return false;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public CardState getNormal() {
        return normal;
    }

    public CardState getChoose(int index) {
        choose.setChoseCard(index);
        return choose;
    }

    public CardState getDrag(int index) {
        drag.setDraggedCard(index);
        return drag;
    }
}
