package guru.voidmain.mappolydrawerlib.virtualoverlay;

import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayMarker extends BaseVirtualOverlay {
    protected LatLngWrapper mPoint;
    protected IDrawableMap.MarkerType mMarkerType;

    public VirtualOverlayMarker(LatLngWrapper point, IDrawableMap.MarkerType markerType) {
        this.mPoint = point;
        this.mMarkerType = markerType;
    }

    public LatLngWrapper getPoint() {
        return mPoint;
    }

    public void setPoint(LatLngWrapper point) {
        this.mPoint = point;
    }

    public IDrawableMap.MarkerType getMarkerType() {
        return mMarkerType;
    }

    public void setMarkerType(IDrawableMap.MarkerType markerType) {
        this.mMarkerType = markerType;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VirtualOverlayMarker) {
            VirtualOverlayMarker marker = (VirtualOverlayMarker) o;
            return marker.getMarkerType().equals(getMarkerType()) &&
                    marker.getPoint().equals(getPoint());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isContentEquals(BaseVirtualOverlay overlay) {
        if (overlay instanceof VirtualOverlayMarker) {
            VirtualOverlayMarker markerOverlay = (VirtualOverlayMarker) overlay;

            return markerOverlay.getPoint().isPointCloseEnough(getPoint());
        }
        return false;
    }
}
