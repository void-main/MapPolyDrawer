package guru.voidmain.mappolydrawerlib.utils;

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
}
