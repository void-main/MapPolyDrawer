package guru.voidmain.mappolydrawerlib.actions;

import com.brianegan.bansa.Action;

import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/12.
 */
public class AddPoint implements Action {
    public LatLngWrapper point;

    public AddPoint(LatLngWrapper point) {
        this.point = point;
    }
}
