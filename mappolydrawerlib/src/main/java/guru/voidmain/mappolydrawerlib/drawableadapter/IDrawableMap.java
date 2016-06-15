package guru.voidmain.mappolydrawerlib.drawableadapter;

import android.graphics.Point;

import java.util.List;

import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/12.
 */
public interface IDrawableMap<PointClass, MarkerClass, PolylineClass, PolygonClass> {
    enum MarkerType {
        UserPoint,
        ControlPoint
    }

    // Converters
    LatLngWrapper convertPointClassToLatLngWrapper(PointClass point);
    PointClass convertLatLngWrapperToPointClass(LatLngWrapper point);

    // UI
    void clearMap();

    MarkerClass addMarkerAtPointWithType(PointClass point, MarkerType markerType);
    PolylineClass addPolylineWithPoints(List<PointClass> points);
    PolygonClass addPolygonWithPoints(List<PointClass> points);

    void removeMarker(MarkerClass marker);
    void removePolyline(PolylineClass polyline);
    void removePolygon(PolygonClass polygon);

    void updateMarkerToPosition(MarkerClass marker, PointClass point);
    void updatePolylinePoints(PolylineClass polyline, List<PointClass> points);
    void updatePolygonPoints(PolygonClass polygon, List<PointClass> points);
}
