package gordon.app.card.ui.card.state;

/**
 * Created by 子皓 on 2014/12/23.
 */
public interface CardState {
    public boolean actionDown(float x, float y);
    public boolean actionUp(float x, float y);
    public boolean actionMove(float x, float y);
}
