package gordon.app.card.ui.card.state;

import gordon.app.card.ui.card.PlayerView;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class ChooseCardState implements CardState {
    private PlayerView pv;
    private StateMachine machine;
    private int choseCard;
    private int lastChose;

    public ChooseCardState(PlayerView pv, StateMachine machine) {
        this.pv = pv;
        this.machine = machine;
    }

    public void setChoseCard(int index) {
        choseCard = index;
    }

    @Override
    public boolean actionDown(float x, float y) {
        return false;
    }

    @Override
    public boolean actionMove(float x, float y) {
        if (pv.inCardArea(x,y)) {
            int index = pv.getIndexFromX(x);
            if (index != choseCard) {
                pv.cardNotChose(choseCard);
                lastChose = choseCard;
                choseCard = index;
                pv.cardChose(choseCard);
                machine.setState(machine.getChoose(choseCard));
            }
        } else {
            pv.cardStartDrag(choseCard);
            machine.setState(machine.getDrag(choseCard));
        }
        return false;
    }

    @Override
    public boolean actionUp(float x, float y) {
        pv.cardNotChose(choseCard);
        machine.setState(machine.getNormal());
        return false;
    }
}
