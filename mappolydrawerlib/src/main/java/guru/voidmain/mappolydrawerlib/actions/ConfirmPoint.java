package guru.voidmain.mappolydrawerlib.actions;

import com.brianegan.bansa.Action;

/**
 * Created by voidmain on 16/6/12.
 */
public class ConfirmPoint implements Action {
    public int index;

    public ConfirmPoint(int index) {
        this.index = index;
    }
}
