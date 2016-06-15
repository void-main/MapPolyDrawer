package guru.voidmain.mappolydrawerlib.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;
import guru.voidmain.mappolydrawerlib.virtualoverlay.BaseVirtualOverlay;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayDiffContainer;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayMarker;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayPolygon;
import guru.voidmain.mappolydrawerlib.virtualoverlay.VirtualOverlayPolyline;

/**
 * Created by voidmain on 16/6/14.
 */
public class MapRenderer<PointClass, MarkerClass, PolylineClass, PolygonClass> {
    protected IDrawableMap<PointClass, MarkerClass, PolylineClass, PolygonClass> mDrawableMap;

    protected Map<VirtualOverlayMarker, MarkerClass> mMarkerMap = new HashMap<>();
    protected Map<VirtualOverlayPolyline, PolylineClass> mPolylineMap = new HashMap<>();
    protected Map<VirtualOverlayPolygon, PolygonClass> mPolygonMap = new HashMap<>();

    public MapRenderer(IDrawableMap<PointClass, MarkerClass, PolylineClass, PolygonClass> drawableMap) {
        this.mDrawableMap = drawableMap;
    }

    public void render(VirtualOverlayDiffContainer diffContainer) {
        for (BaseVirtualOverlay overlay :
                diffContainer.getAddedVirtualOverlays().getVirtualOverlays()) {
            addOverlayForVirtualOverlay(overlay);
        }

        for (BaseVirtualOverlay overlay:
                diffContainer.getRemovedVirtualOverlays().getVirtualOverlays()) {
            removeOverlayForVirtualOverlay(overlay);
        }

        for (BaseVirtualOverlay overlay:
                diffContainer.getUpdatedVirtualOverlays().getVirtualOverlays()) {
            updateOverlayForVirtualOverlay(overlay);
        }
    }

    public void addOverlayForVirtualOverlay(BaseVirtualOverlay overlay) {
        if (overlay instanceof VirtualOverlayMarker) {
            VirtualOverlayMarker markerOverlay = (VirtualOverlayMarker) overlay;
            MarkerClass marker = mDrawableMap.addMarkerAtPointWithType(
                    mDrawableMap.convertLatLngWrapperToPointClass(markerOverlay.getPoint()),
                    markerOverlay.getMarkerType());
            mMarkerMap.put(markerOverlay, marker);
        } else if (overlay instanceof VirtualOverlayPolyline) {
            VirtualOverlayPolyline polylineOverlay = (VirtualOverlayPolyline) overlay;
            List<PointClass> convertedPoints = convertLatLngWrappersToPoints(polylineOverlay.getMapPolygon().polygonPoints());

            if (polylineOverlay.getMapPolygon().isClosePolygon()) {
                convertedPoints.add(convertedPoints.get(0)); // 如果多边形闭合，则加上第0个
            }

            PolylineClass polyline = mDrawableMap.addPolylineWithPoints(convertedPoints);
            mPolylineMap.put(polylineOverlay, polyline);
        } else if (overlay instanceof VirtualOverlayPolygon) {
            VirtualOverlayPolygon polygonOverlay = (VirtualOverlayPolygon) overlay;
            List<PointClass> convertedPoints = convertLatLngWrappersToPoints(polygonOverlay.getMapPolygon().polygonPoints());
            PolygonClass polygon = mDrawableMap.addPolygonWithPoints(convertedPoints);
            mPolygonMap.put(polygonOverlay, polygon);
        }
    }

    public void removeOverlayForVirtualOverlay(BaseVirtualOverlay overlay) {
        if (overlay instanceof VirtualOverlayMarker) {
            VirtualOverlayMarker markerOverlay = (VirtualOverlayMarker) overlay;
            if (mMarkerMap.containsKey(markerOverlay)) {
                mDrawableMap.removeMarker(mMarkerMap.get(markerOverlay));
            }
        } else if (overlay instanceof VirtualOverlayPolyline) {
            VirtualOverlayPolyline polylineOverlay = (VirtualOverlayPolyline) overlay;
            if (mPolylineMap.containsKey(polylineOverlay)) {
                mDrawableMap.removePolyline(mPolylineMap.get(polylineOverlay));
            }
        } else if (overlay instanceof VirtualOverlayPolygon) {
            VirtualOverlayPolygon polygonOverlay = (VirtualOverlayPolygon) overlay;
            if (mPolygonMap.containsKey(polygonOverlay)) {
                mDrawableMap.removePolygon(mPolygonMap.get(polygonOverlay));
            }
        }
    }

    public void updateOverlayForVirtualOverlay(BaseVirtualOverlay overlay) {
        if (overlay instanceof VirtualOverlayMarker) {
            VirtualOverlayMarker markerOverlay = (VirtualOverlayMarker) overlay;
            if (mMarkerMap.containsKey(markerOverlay)) {
                MarkerClass marker = mMarkerMap.get(markerOverlay);
                mDrawableMap.updateMarkerToPosition(marker,
                        mDrawableMap.convertLatLngWrapperToPointClass(markerOverlay.getPoint()));
            }
        } else if (overlay instanceof VirtualOverlayPolyline) {
            VirtualOverlayPolyline polylineOverlay = (VirtualOverlayPolyline) overlay;
            if (mPolylineMap.containsKey(polylineOverlay)) {
                PolylineClass polyline = mPolylineMap.get(polylineOverlay);

                List<PointClass> convertedPoints = convertLatLngWrappersToPoints(
                        polylineOverlay.getMapPolygon().polygonPoints());

                if (polylineOverlay.getMapPolygon().isClosePolygon()) {
                    convertedPoints.add(convertedPoints.get(0)); // 如果多边形闭合，则加上第0个
                }

                mDrawableMap.updatePolylinePoints(polyline, convertedPoints);
            }
        } else if (overlay instanceof VirtualOverlayPolygon) {
            VirtualOverlayPolygon polygonOverlay = (VirtualOverlayPolygon) overlay;
            if (mPolygonMap.containsKey(polygonOverlay)) {
                PolygonClass polygon = mPolygonMap.get(overlay);
                List<PointClass> convertedPoints = convertLatLngWrappersToPoints(polygonOverlay.getMapPolygon().polygonPoints());
                mDrawableMap.updatePolygonPoints(polygon, convertedPoints);
            }
        }
    }

    public VirtualOverlayMarker getVirtualOverlayForMaker(MarkerClass marker) {
        for (VirtualOverlayMarker overlay: mMarkerMap.keySet()) {
            if (mMarkerMap.get(overlay).equals(marker)) {
                return overlay;
            }
        }

        return null;
    }

    // TODO find a better place for this
    protected List<PointClass> convertLatLngWrappersToPoints(List<LatLngWrapper> wrappers) {
        List<PointClass> points = new ArrayList<>();
        for (LatLngWrapper wrapper :
                wrappers) {
            points.add(mDrawableMap.convertLatLngWrapperToPointClass(wrapper));
        }
        return points;
    }

}
