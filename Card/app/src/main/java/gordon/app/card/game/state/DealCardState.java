package gordon.app.card.game.state;

/**
 * Created by 子皓 on 2014/12/25.
 */
public class DealCardState implements GameState {
    private StateMachine machine;

    public DealCardState(StateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void handle(StateConst e) {
        if (e == StateConst.DEALCARDEND) {
            machine.setState(machine.getBid());
        }
    }
}
