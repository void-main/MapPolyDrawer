package guru.voidmain.mappolydrawerlib.drawer;

import android.graphics.Point;
import android.util.Log;

import com.brianegan.bansa.Action;
import com.brianegan.bansa.BaseStore;
import com.brianegan.bansa.Middleware;
import com.brianegan.bansa.NextDispatcher;
import com.brianegan.bansa.Store;
import com.brianegan.bansa.Subscriber;

import java.util.List;

import guru.voidmain.mappolydrawerlib.actions.AddPoint;
import guru.voidmain.mappolydrawerlib.actions.ClearPoints;
import guru.voidmain.mappolydrawerlib.actions.ClosePolygon;
import guru.voidmain.mappolydrawerlib.actions.ConfirmPoint;
import guru.voidmain.mappolydrawerlib.actions.MovePoint;
import guru.voidmain.mappolydrawerlib.actions.Redo;
import guru.voidmain.mappolydrawerlib.actions.SelectPolygon;
import guru.voidmain.mappolydrawerlib.actions.Undo;
import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;
import guru.voidmain.mappolydrawerlib.middlewares.UndoRedoMiddleware;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;
import guru.voidmain.mappolydrawerlib.models.MapPolygon;
import guru.voidmain.mappolydrawerlib.reducers.DrawerReducer;
import guru.voidmain.mappolydrawerlib.renderer.MapRenderer;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;
import guru.voidmain.mappolydrawerlib.utils.GeoUtils;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayBuilder;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayContainer;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayDiffComputer;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayDiffContainer;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayMarker;

/**
 * Created by voidmain on 16/6/12.
 */

public class Drawer<PointClass, MarkerClass, PolylineClass, PolygonClass> {
    public static final String TAG = Drawer.class.getCanonicalName();

    protected IDrawableMap<PointClass, MarkerClass, PolylineClass, PolygonClass> mDrawableMap;
    BaseStore<ApplicationState> mStore = null;

    protected MapRenderer mRenderer;

    private VirtualOverlayContainer mLastVirtualOverlay = new VirtualOverlayContainer();

    public interface OnStateChangedListener {
        void onStateChanged(ApplicationState state);
    }

    private OnStateChangedListener mStatedChangedListener = null;

    public void setOnStateChangedListener(OnStateChangedListener stateChangedListener) {
        mStatedChangedListener = stateChangedListener;
    }

    public Drawer(IDrawableMap drawableMap) {
        mDrawableMap = drawableMap;
        mRenderer = new MapRenderer(mDrawableMap);

        mStore = new BaseStore<>(new ApplicationState(), new DrawerReducer(), new UndoRedoMiddleware());
        mStore.subscribe(new Subscriber<ApplicationState>() {
            @Override
            public void onStateChange(ApplicationState state) {
                if (mStatedChangedListener != null) {
                    mStatedChangedListener.onStateChanged(state);
                }

                VirtualOverlayContainer newContainer = VirtualOverlayBuilder.build(state);
                VirtualOverlayDiffContainer diff = VirtualOverlayDiffComputer.computerDiffs(newContainer, mLastVirtualOverlay);
                mRenderer.render(diff);
                mLastVirtualOverlay = newContainer;
            }
        });
    }

    // Event Handling
    public void onMapClicked(PointClass point) {
        LatLngWrapper wrapperPoint = mDrawableMap.convertPointClassToLatLngWrapper(point);
        mStore.dispatch(new AddPoint(wrapperPoint));
    }

    public void onClosePolyline() {
        mStore.dispatch(new ClosePolygon());
    }

    public void onClearPoints() {
        mStore.dispatch(new ClearPoints());
    }

    public void onDragMarkerStart(MarkerClass marker) {
        VirtualOverlayMarker overlay = mRenderer.getVirtualOverlayForMaker(marker);
        if (overlay != null && overlay.getMarkerType() == IDrawableMap.MarkerType.ControlPoint) {
            MapPolygon currentPolygon = mStore.getState().getPolygons().get(
                    mStore.getState().getCurrentPolyIndex());

            mStore.dispatch(new ConfirmPoint(
                    currentPolygon.allPoints().indexOf(overlay.getPoint()) / 2));
        }
    }

    public void onDragMarkerEnd(MarkerClass marker, PointClass toPosition) {
        emitMoveAction(marker, toPosition, true);
    }

    public void onDragMarkerMove(MarkerClass marker, PointClass toPosition) {
        emitMoveAction(marker, toPosition, false);
    }

    protected void emitMoveAction(MarkerClass marker, PointClass toPosition, boolean shouldAddToUndo) {
        VirtualOverlayMarker overlay = mRenderer.getVirtualOverlayForMaker(marker);
        if (overlay != null) {
            MapPolygon currentPolygon = mStore.getState().getPolygons().get(
                    mStore.getState().getCurrentPolyIndex());
            int userPointIndex = currentPolygon.allPoints().indexOf(overlay.getPoint()) / 2;
            mStore.dispatch(new MovePoint(userPointIndex,
                    mDrawableMap.convertPointClassToLatLngWrapper(toPosition),
                    shouldAddToUndo
            ));
        }
    }

    public void onSelectPolygon(int index) {
        mStore.dispatch(new SelectPolygon(index));
    }

    public void onCompletePolygon() {
        mStore.dispatch(new SelectPolygon(-1));
    }

    public void onMapLongClicked(PointClass point) {
        if (mStore.getState().getCurrentPolyIndex() != -1) return;

        LatLngWrapper wrapper = mDrawableMap.convertPointClassToLatLngWrapper(point);
        List<MapPolygon> polygons = mStore.getState().getPolygons();
        for (int index = 0; index < polygons.size(); index++) {
            MapPolygon polygon = polygons.get(index);
            if (GeoUtils.isPointInPolygon(wrapper, polygon.polygonPoints())) {
                onSelectPolygon(index);
                return;
            }
        }
    }

    // UNDO REDO
    public void onUndo() {
        mStore.dispatch(new Undo());
    }

    public void onRedo() {
        mStore.dispatch(new Redo());
    }

    public String getJsonString() {
        List<MapPolygon> polygons = mStore.getState().getPolygons();
        StringBuilder sb = new StringBuilder("[");

        for (MapPolygon polygon :
                polygons) {
            sb.append(polygon.toJSON());
        }

        sb.append("]");

        return sb.toString();
    }
}
