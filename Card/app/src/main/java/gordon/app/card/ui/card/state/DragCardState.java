package gordon.app.card.ui.card.state;

import gordon.app.card.ui.card.PlayerView;

/**
 * Created by 子皓 on 2014/12/23.
 */
public class DragCardState implements CardState {
    private PlayerView pv;
    private StateMachine machine;
    private int draggedCard;

    public DragCardState(PlayerView pv, StateMachine machine) {
        this.pv = pv;
        this.machine = machine;
    }

    public void setDraggedCard(int index) {
        draggedCard = index;
    }

    @Override
    public boolean actionDown(float x, float y) {
        return false;
    }

    @Override
    public boolean actionMove(float x, float y) {
        pv.cardDragged(draggedCard, x, y);
        return false;
    }

    @Override
    public boolean actionUp(float x, float y) {
        if (pv.inPutArea(x,y) && pv.isTurn()) {
            pv.putCardOnTable(draggedCard);
        } else {
            pv.cardNotDragged(draggedCard);
        }
        machine.setState(machine.getNormal());
        return false;
    }
}
