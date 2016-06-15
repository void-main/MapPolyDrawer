package guru.voidmain.mappolydrawerlib.reducers;

import com.brianegan.bansa.Action;
import com.brianegan.bansa.Reducer;

import guru.voidmain.mappolydrawerlib.actions.AddPoint;
import guru.voidmain.mappolydrawerlib.actions.ClearPoints;
import guru.voidmain.mappolydrawerlib.actions.ClosePolygon;
import guru.voidmain.mappolydrawerlib.actions.ConfirmPoint;
import guru.voidmain.mappolydrawerlib.actions.DeletePolygon;
import guru.voidmain.mappolydrawerlib.actions.Init;
import guru.voidmain.mappolydrawerlib.actions.MovePoint;
import guru.voidmain.mappolydrawerlib.actions.Redo;
import guru.voidmain.mappolydrawerlib.actions.SelectPolygon;
import guru.voidmain.mappolydrawerlib.actions.Undo;
import guru.voidmain.mappolydrawerlib.models.MapPolygon;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;
import guru.voidmain.mappolydrawerlib.stores.UndoRedoStore;

/**
 * Main Reducer
 * Created by voidmain on 16/6/12.
 */
public class DrawerReducer implements Reducer<ApplicationState> {

    @Override
    public ApplicationState reduce(ApplicationState state, Action action) {
        if (action instanceof Init) {
            return new ApplicationState();
        } else if (action instanceof AddPoint) {
            AddPoint addPointAction = (AddPoint)action;
            if (state.getCurrentPolyIndex() != -1) { // Currently selecting polygon
                MapPolygon polygon = state.getPolygons().get(state.getCurrentPolyIndex());
                if (polygon.isClosePolygon()) {
                    return state;
                } else {
                    return ApplicationState.stateByAddUserPoint(state, addPointAction.point);
                }
            } else {
                MapPolygon polygon = new MapPolygon();
                polygon.addUserPoint(addPointAction.point);
                return ApplicationState.stateByAddingPolygon(state, polygon);
            }
        } else if (action instanceof MovePoint) {
            if (state.getCurrentPolyIndex() != -1) {
                MovePoint movePointAction = (MovePoint)action;
                return ApplicationState.stateByMovePoint(state, movePointAction.index, movePointAction.point);
            }
        } else if (action instanceof ConfirmPoint) {
            if (state.getCurrentPolyIndex() != -1) {
                ConfirmPoint confirmPointAction = (ConfirmPoint)action;
                return ApplicationState.stateByConfirmPoint(state, confirmPointAction.index);
            }
        } else if (action instanceof ClearPoints) {
            if (state.getCurrentPolyIndex() != -1) {
                return ApplicationState.stateByClearPoints(state);
            }
        } else if (action instanceof ClosePolygon) {
            if (state.getCurrentPolyIndex() != -1) {
                return ApplicationState.stateByClosePolygon(state);
            }
        } else if (action instanceof SelectPolygon) {
            return ApplicationState.stateBySelectPolygon(state, ((SelectPolygon) action).polygonIndex);
        } else if (action instanceof DeletePolygon) {
            if (state.getCurrentPolyIndex() != -1) {
                return ApplicationState.stateByDeletePolygon(state, ((DeletePolygon) action).polygonIndex);
            }
        } else if (action instanceof Undo) {
            if (UndoRedoStore.getInstance().canUndo()) {
                return UndoRedoStore.getInstance().performUndo(state);
            }
        } else if (action instanceof Redo) {
            if (UndoRedoStore.getInstance().canRedo()) {
                return UndoRedoStore.getInstance().performRedo(state);
            }
        }

        return state;
    }
}
