package gordon.app.card.game.state;

/**
 * Created by 子皓 on 2014/12/24.
 */
public class StateMachine {
    private GameState state;
    private DealCardState deal;
    private BidState bid;
    private ChangeState change;
    private PlayState play;

    public StateMachine() {
        deal = new DealCardState(this);
        bid = new BidState(this);
        change = new ChangeState();
        play = new PlayState();
        state = deal;
    }

    public void handle(StateConst e) {
        state.handle(e);
    }

    public void setState(GameState s) {
        state = s;
    }

    public GameState getDeal() {
        return deal;
    }

    public GameState getBid() {
        return bid;
    }

    public GameState getChange() {
        return change;
    }

    public GameState getPlay() {
        return play;
    }
}
