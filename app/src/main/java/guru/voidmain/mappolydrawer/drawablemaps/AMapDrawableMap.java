package guru.voidmain.mappolydrawer.drawablemaps;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;

import java.util.List;

import guru.voidmain.mappolydrawer.R;
import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/15.
 */
public class AMapDrawableMap implements IDrawableMap<LatLng, Marker, Polyline, Polygon> {

    MapView mMapView = null;
    AMap mAMap = null;

    public AMapDrawableMap(MapView mapView) {
        mMapView = mapView;
        mAMap = mapView.getMap();
    }

    @Override
    public LatLngWrapper convertPointClassToLatLngWrapper(LatLng point) {
        return new LatLngWrapper(point.latitude, point.longitude);
    }

    @Override
    public LatLng convertLatLngWrapperToPointClass(LatLngWrapper point) {
        return new LatLng(point.latitude, point.longitude);
    }

    @Override
    public void clearMap() {
        mAMap.clear();
    }

    @Override
    public Marker addMarkerAtPointWithType(LatLng point, MarkerType markerType) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(markerType == MarkerType.UserPoint ? R.drawable.box_black : R.drawable.box_grey);
        MarkerOptions options = new MarkerOptions().position(point).icon(bitmap).zIndex(1000).draggable(true).anchor(0.5f, 0.5f);
        return mAMap.addMarker(options);
    }

    @Override
    public Polyline addPolylineWithPoints(List<LatLng> points) {
        PolylineOptions option = new PolylineOptions().addAll(points).zIndex(999);
        return mAMap.addPolyline(option);
    }

    @Override
    public Polygon addPolygonWithPoints(List<LatLng> points) {
        PolygonOptions option = new PolygonOptions().addAll(points).zIndex(999);
        return mAMap.addPolygon(option);
    }

    @Override
    public void removeMarker(Marker marker) {
        marker.remove();
    }

    @Override
    public void removePolyline(Polyline polyline) {
        polyline.remove();
    }

    @Override
    public void removePolygon(Polygon polygon) {
        polygon.remove();
    }

    @Override
    public void updateMarkerToPosition(Marker marker, LatLng point) {
        marker.setPosition(point);
    }

    @Override
    public void updatePolylinePoints(Polyline polyline, List<LatLng> points) {
        polyline.setPoints(points);
    }

    @Override
    public void updatePolygonPoints(Polygon polygon, List<LatLng> points) {
        polygon.setPoints(points);
    }
}
