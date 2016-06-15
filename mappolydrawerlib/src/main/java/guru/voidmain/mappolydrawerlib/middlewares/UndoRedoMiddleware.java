package guru.voidmain.mappolydrawerlib.middlewares;

import android.util.Log;

import com.brianegan.bansa.Action;
import com.brianegan.bansa.Middleware;
import com.brianegan.bansa.NextDispatcher;
import com.brianegan.bansa.Store;

import guru.voidmain.mappolydrawerlib.actions.ConfirmPoint;
import guru.voidmain.mappolydrawerlib.actions.MovePoint;
import guru.voidmain.mappolydrawerlib.actions.Redo;
import guru.voidmain.mappolydrawerlib.actions.Undo;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;
import guru.voidmain.mappolydrawerlib.stores.UndoRedoStore;

/**
 * Created by voidmain on 16/6/15.
 */
public class UndoRedoMiddleware implements Middleware<ApplicationState> {

    @Override
    public void dispatch(Store<ApplicationState> store, Action action, NextDispatcher next) {
        if (!(action instanceof Undo) &&
                !(action instanceof Redo) &&
                !(action instanceof ConfirmPoint) &&
                !(action instanceof MovePoint && !((MovePoint) action).shouldAddToUndo)) {
            UndoRedoStore.getInstance().addState(store.getState());
        }

        next.dispatch(action);
    }
}
