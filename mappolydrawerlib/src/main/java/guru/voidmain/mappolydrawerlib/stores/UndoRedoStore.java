package guru.voidmain.mappolydrawerlib.stores;

import java.util.Stack;

/**
 * Created by voidmain on 16/6/15.
 */
public class UndoRedoStore {
    private static class SingletonHolder {
        private static final UndoRedoStore INSTANCE = new UndoRedoStore();
    }

    public static UndoRedoStore getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Stack<ApplicationState> mUndoStack = null;
    private Stack<ApplicationState> mRedoStack = null;

    private UndoRedoStore() {
        mUndoStack = new Stack<>();
        mRedoStack = new Stack<>();
    }

    public boolean canUndo() {
        return !mUndoStack.isEmpty();
    }

    public boolean canRedo() {
        return !mRedoStack.isEmpty();
    }

    public void addState(ApplicationState state) {
        mUndoStack.push(state);
        mRedoStack.removeAllElements();
    }

    public ApplicationState performUndo(ApplicationState now) {
        mRedoStack.push(now);
        return mUndoStack.pop();
    }

    public ApplicationState performRedo(ApplicationState now) {
        mUndoStack.push(now);
        return mRedoStack.pop();
    }
}
