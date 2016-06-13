package guru.voidmain.mappolydrawerlib.stores;

import com.esotericsoftware.kryo.Kryo;

import java.util.ArrayList;
import java.util.List;

import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;
import guru.voidmain.mappolydrawerlib.models.MapPolygon;

/**
 * Created by voidmain on 16/6/12.
 */
public class ApplicationState {
    private static Kryo kryo = new Kryo();

    public static ApplicationState stateByAddingPolygon(ApplicationState state, MapPolygon polygon) {
        List<MapPolygon> polygons = kryo.copy(state.polygons);
        polygons.add(polygon);

        return new ApplicationState(polygons, polygons.size() - 1);
    }

    public static ApplicationState stateByAddUserPoint(ApplicationState state, LatLngWrapper point) {
        List<MapPolygon> polygons = kryo.copy(state.polygons);
        polygons.get(state.getCurrentPolyIndex()).addUserPoint(point);

        return new ApplicationState(polygons, state.getCurrentPolyIndex());
    }

    public static ApplicationState stateByMovePoint(ApplicationState state, int index, LatLngWrapper point) {
        List<MapPolygon> polygons = kryo.copy(state.polygons);
        polygons.get(state.getCurrentPolyIndex()).moveUserPoint(index, point);

        return new ApplicationState(polygons, state.getCurrentPolyIndex());
    }

    public static ApplicationState stateByConfirmPoint(ApplicationState state, int index) {
        List<MapPolygon> polygons = kryo.copy(state.polygons);
        polygons.get(state.getCurrentPolyIndex()).confirmControlPoint(index);

        return new ApplicationState(polygons, state.getCurrentPolyIndex());
    }

    public static ApplicationState stateByClearPoints(ApplicationState state) {
        return stateByDeletePolygon(state, state.getCurrentPolyIndex());
    }

    public static ApplicationState stateByClosePolygon(ApplicationState state) {
        List<MapPolygon> polygons = kryo.copy(state.polygons);
        polygons.get(state.getCurrentPolyIndex()).setClosePolygon(true);
        return new ApplicationState(polygons, state.getCurrentPolyIndex());
    }

    public static ApplicationState stateBySelectPolygon(ApplicationState state, int polygonIndex) {
        return new ApplicationState(state.polygons, polygonIndex);
    }

    public static ApplicationState stateByDeletePolygon(ApplicationState state, int polygonIndex) {
        List<MapPolygon> polygons = kryo.copy(state.polygons);
        polygons.remove(polygonIndex);
        return new ApplicationState(polygons, -1);
    }

    private List<MapPolygon> polygons;
    private int currentPolyIndex;

    public ApplicationState() {
        this(new ArrayList<MapPolygon>(), -1);
    }

    public ApplicationState(List<MapPolygon> polygons, int currentPolyIndex) {
        setPolygons(polygons);
        setCurrentPolyIndex(currentPolyIndex);
    }

    public List<MapPolygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<MapPolygon> polygons) {
        this.polygons = kryo.copy(polygons);
    }

    public int getCurrentPolyIndex() {
        return currentPolyIndex;
    }

    public void setCurrentPolyIndex(int currentPolyIndex) {
        this.currentPolyIndex = currentPolyIndex;
    }
}
