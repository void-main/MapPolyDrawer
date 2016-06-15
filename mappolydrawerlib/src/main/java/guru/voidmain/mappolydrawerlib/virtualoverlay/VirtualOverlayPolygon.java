package guru.voidmain.mappolydrawerlib.virtualoverlay;

import guru.voidmain.mappolydrawerlib.models.MapPolygon;

/**
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayPolygon extends BaseVirtualOverlay {
    protected MapPolygon mMapPolygon;

    public VirtualOverlayPolygon(MapPolygon mapPolygon) {
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
        if (o instanceof VirtualOverlayPolygon) {
            VirtualOverlayPolygon other = (VirtualOverlayPolygon) o;
            return other.getMapPolygon().getUid().equals(getMapPolygon().getUid());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isContentEquals(BaseVirtualOverlay overlay) {
        if (overlay instanceof VirtualOverlayPolygon) {
            VirtualOverlayPolygon polygonOverlay = (VirtualOverlayPolygon) overlay;
            return polygonOverlay.getMapPolygon().isContentEquals(getMapPolygon());
        }
        return false;
    }
}
