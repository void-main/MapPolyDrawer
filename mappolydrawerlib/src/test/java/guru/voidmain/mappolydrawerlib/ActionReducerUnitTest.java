package guru.voidmain.mappolydrawerlib;

import com.brianegan.bansa.BaseStore;

import org.junit.Test;

import guru.voidmain.mappolydrawerlib.actions.AddPoint;
import guru.voidmain.mappolydrawerlib.actions.ClearPoints;
import guru.voidmain.mappolydrawerlib.actions.ClosePolygon;
import guru.voidmain.mappolydrawerlib.actions.ConfirmPoint;
import guru.voidmain.mappolydrawerlib.actions.DeletePolygon;
import guru.voidmain.mappolydrawerlib.actions.MovePoint;
import guru.voidmain.mappolydrawerlib.actions.SelectPolygon;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;
import guru.voidmain.mappolydrawerlib.reducers.DrawerReducer;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ActionReducerUnitTest {

    @Test
    public void actionAndReducer_isCorrect() throws Exception {
        BaseStore<ApplicationState> store = new BaseStore<>(new ApplicationState(), new DrawerReducer());
        store.dispatch(new AddPoint(new LatLngWrapper(1, 1)));
        ApplicationState state = store.getState();
        assertEquals("should select first polygon", 0, state.getCurrentPolyIndex());
        assertEquals("should have 1 polygon", 1, state.getPolygons().size());


        store.dispatch(new AddPoint(new LatLngWrapper(2, 2)));
        store.dispatch(new AddPoint(new LatLngWrapper(2, 1)));
        state = store.getState();
        assertEquals("should select first polygon", 0, state.getCurrentPolyIndex());
        assertEquals("should have 1 polygon", 1, state.getPolygons().size());
        assertEquals("should have 3 points",
                3,
                state.getPolygons().get(state.getCurrentPolyIndex()).polygonPoints().size());


        store.dispatch(new MovePoint(0, new LatLngWrapper(0, 0)));
        state = store.getState();
        assertEquals("first point should be 0,0",
                true,
                state.getPolygons().get(state.getCurrentPolyIndex()).polygonPoints().get(0).isPointCloseEnough(new LatLngWrapper(0, 0)));

        store.dispatch(new ClosePolygon());
        state = store.getState();
        assertEquals("first polygon should close",
                true,
                state.getPolygons().get(state.getCurrentPolyIndex()).isClosePolygon());

        store.dispatch(new MovePoint(0, new LatLngWrapper(1, 1)));
        state = store.getState();
        assertEquals("first point should be 1,1",
                true,
                state.getPolygons().get(state.getCurrentPolyIndex()).polygonPoints().get(0).isPointCloseEnough(new LatLngWrapper(1, 1)));

        store.dispatch(new ConfirmPoint(0));
        state = store.getState();
        assertEquals("should have 4 points",
                4,
                state.getPolygons().get(state.getCurrentPolyIndex()).polygonPoints().size());

        store.dispatch(new SelectPolygon(-1));
        state = store.getState();
        assertEquals("should select -1", -1, state.getCurrentPolyIndex());
        assertEquals("should have 1 polygon", 1, state.getPolygons().size());

        store.dispatch(new AddPoint(new LatLngWrapper(5, 5)));
        state = store.getState();
        assertEquals("should have 2 polygon", 2, state.getPolygons().size());
        assertEquals("should select second polygon", 1, state.getCurrentPolyIndex());

        store.dispatch(new AddPoint(new LatLngWrapper(6, 6)));
        store.dispatch(new AddPoint(new LatLngWrapper(5, 6)));
        state = store.getState();
        assertEquals("should have 3 points", 3,
                state.getPolygons().get(state.getCurrentPolyIndex()).polygonPoints().size());
        assertEquals("should select second polygon", 1,
                state.getCurrentPolyIndex());

        store.dispatch(new ClearPoints());
        state = store.getState();
        assertEquals("should have 1 polygon", 1, state.getPolygons().size());
        assertEquals("should select -1", -1, state.getCurrentPolyIndex());

        store.dispatch(new SelectPolygon(0));
        state = store.getState();
        assertEquals("should have 1 polygon", 1, state.getPolygons().size());
        assertEquals("should select first polygon", 0, state.getCurrentPolyIndex());

        store.dispatch(new SelectPolygon(-1));
        state = store.getState();
        assertEquals("should select -1", -1, state.getCurrentPolyIndex());
        assertEquals("should have 1 polygon", 1, state.getPolygons().size());
    }

}