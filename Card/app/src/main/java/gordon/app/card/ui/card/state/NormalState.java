package gordon.app.card.ui.card.state;

import gordon.app.card.ui.card.PlayerView;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class NormalState implements CardState {
    private PlayerView pv;
    private StateMachine machine;

    public NormalState(PlayerView p, StateMachine machine) {
        this.pv = p;
        this.machine = machine;
    }

    @Override
    public boolean actionDown(float x, float y) {
        if (pv.inCardArea(x,y)) {
            int index = pv.getIndexFromX(x);
            pv.cardChose(index);
            machine.setState(machine.getChoose(index));
            return true;
        } else {
            pv.moveCardAway();
        }
        return false;
    }

    @Override
    public boolean actionMove(float x, float y) {
        return false;
    }

    @Override
    public boolean actionUp(float x, float y) {
        return false;
    }
}
