package guru.voidmain.mappolydrawerlib.actions;

import com.brianegan.bansa.Action;

import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/12.
 */
public class MovePoint implements Action {
    public int index;
    public LatLngWrapper point;
    public boolean shouldAddToUndo;

    public MovePoint(int index, LatLngWrapper point, boolean shouldAddToUndo) {
        this.index = index;
        this.point = point;
        this.shouldAddToUndo = shouldAddToUndo;
    }
}
