package guru.voidmain.mappolydrawerlib.utils;

import java.util.List;

import guru.voidmain.mappolydrawerlib.common.Constants;
import guru.voidmain.mappolydrawerlib.models.LatLngWrapper;

/**
 * Created by voidmain on 16/6/12.
 */
public class GeoUtils {
    /**
     * Helper method that computes and returns the middle point of 2 LatLngWrapper
     * @param start
     * @param stop
     * @return a new created LatLngWrapper
     */
    public static LatLngWrapper middlePoint(LatLngWrapper start, LatLngWrapper stop) {
        return new LatLngWrapper(
                (start.latitude + stop.latitude) * 0.5,
                (start.longitude + stop.longitude) * 0.5);
    }


    public static boolean isPointInPolygon(LatLngWrapper point, List<LatLngWrapper> vertexes) {
        int i = 0, j = 0;
        boolean c = false;
        int nvert = vertexes.size();
        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            LatLngWrapper vert1 = vertexes.get(i);
            LatLngWrapper vert2 = vertexes.get(j);

            if (Math.abs(vert2.latitude - vert1.latitude) < Constants.EPSILON
                    &&
                    (vert1.longitude > point.longitude) != (vert2.longitude > point.longitude)) {
//				c = !c;
            } else {
                if (((vert1.latitude > point.latitude) != (vert2.latitude > point.latitude))
                        &&
                        (point.longitude < (vert2.longitude - vert1.longitude) *
                                (point.latitude - vert1.latitude) /
                                (vert2.latitude - vert1.latitude) + vert1.longitude) ) {
                    c = !c;
                }
            }
        }

        return c;
    }
}
