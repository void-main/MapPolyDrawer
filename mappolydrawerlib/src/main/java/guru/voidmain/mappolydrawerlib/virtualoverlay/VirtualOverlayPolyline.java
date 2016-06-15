package guru.voidmain.mappolydrawerlib.virtualoverlay;

import guru.voidmain.mappolydrawerlib.models.MapPolygon;

/**
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayPolyline extends BaseVirtualOverlay {
    protected MapPolygon mMapPolygon;

    public VirtualOverlayPolyline(MapPolygon mapPolygon) {
        this.mMapPolygon = mapPolygon;
    }

    public MapPolygon getMapPolygon() {
        return mMapPolygon;
    }

    public void setMapPolygon(MapPolygon mapPolygon) {
        this.mMapPolygon = mapPolygon;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VirtualOverlayPolyline) {
            VirtualOverlayPolyline other = (VirtualOverlayPolyline) o;
            return other.getMapPolygon().getUid().equals(getMapPolygon().getUid());
        }

        return false;
    }
}
