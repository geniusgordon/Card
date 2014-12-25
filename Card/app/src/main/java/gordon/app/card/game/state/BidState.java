package gordon.app.card.game.state;

/**
 * Created by 子皓 on 2014/12/25.
 */
public class BidState implements GameState {
    private StateMachine machine;
    private int turn;

    public BidState(StateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void handle(StateConst e) {
        if (e == StateConst.NEXTBID) {
            turn = (turn+1) % 2;
        } else if (e == StateConst.BIDEND) {
            machine.setState(machine.getChange());
        }
    }
}
