package guru.voidmain.mappolydrawerlib.virtualoverlay;

import java.util.List;

import guru.voidmain.mappolydrawerlib.drawableadapter.IDrawableMap;
import guru.voidmain.mappolydrawerlib.models.MapPolygon;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;

/**
 * Builds virtual overlay from state
 * Created by voidmain on 16/6/14.
 */
public class VirtualOverlayBuilder {
    public static VirtualOverlayContainer build(ApplicationState state) {
        VirtualOverlayContainer container = new VirtualOverlayContainer();
        List<MapPolygon> polygons = state.getPolygons();
        int currentPolyIndex = state.getCurrentPolyIndex();
        for (int idx = 0; idx < polygons.size(); idx++) {
            MapPolygon polygon = polygons.get(idx);

            if (idx == currentPolyIndex) {
                for (int latLngIdx = 0; latLngIdx < polygon.allPoints().size(); latLngIdx++) {
                    container.addVirtualOverlay(
                            new VirtualOverlayMarker(polygon.allPoints().get(latLngIdx),
                                    latLngIdx % 2 == 0 ? IDrawableMap.MarkerType.UserPoint : IDrawableMap.MarkerType.ControlPoint));
                }

                container.addVirtualOverlay(new VirtualOverlayPolyline(polygon));
            } else {
                VirtualOverlayPolygon virtualPolygon = new VirtualOverlayPolygon(polygon);
                container.addVirtualOverlay(virtualPolygon);
            }
        }
        return container;
    }
}
