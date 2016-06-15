package guru.voidmain.mappolydrawer.drawablemaps;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import guru.voidmain.mappolydrawer.R;
import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/12.
 */
public class BMapDrawableMap implements IDrawableMap<LatLng, Marker, Polyline, Polygon> {
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;

    public BMapDrawableMap(MapView mapView) {
        mMapView = mapView;
        mBaiduMap = mapView.getMap();
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
        mBaiduMap.clear();
    }

    @Override
    public Marker addMarkerAtPointWithType(LatLng point, MarkerType markerType) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(markerType == MarkerType.UserPoint ? R.drawable.box_black : R.drawable.box_grey);
        OverlayOptions options = new MarkerOptions().position(point).icon(bitmap).zIndex(1000)
                .draggable(true).anchor(0.5f, 0.5f);

        Marker marker = (Marker) (mBaiduMap.addOverlay(options));
        return marker;
    }

    @Override
    public Polyline addPolylineWithPoints(List<LatLng> points) {
        OverlayOptions option = new PolylineOptions().points(points).zIndex(999);
        Polyline polyline = (Polyline) mBaiduMap.addOverlay(option);
        return polyline;
    }

    @Override
    public Polygon addPolygonWithPoints(List<LatLng> points) {
        PolygonOptions option = new PolygonOptions().points(points).zIndex(999);
        Polygon polygon = (Polygon) mBaiduMap.addOverlay(option);
        return polygon;
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
