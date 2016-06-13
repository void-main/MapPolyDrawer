package guru.voidmain.mappolydrawerlib.models;

/**
 * Wrapper class that holds latitude and longitude
 * Used for multi-map compatibility
 * Created by voidmain on 16/6/12.
 */
public class LatLngWrapper {
    public double latitude;
    public double longitude;

    private static final double EPSILON = 1e-5;

    public LatLngWrapper() {
        this.latitude = 0;
        this.longitude = 0;
    }

    public LatLngWrapper(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LatLngWrapper) {
            LatLngWrapper item = (LatLngWrapper) o;
            return Math.abs(item.latitude - latitude) < EPSILON &&
                    Math.abs(item.longitude - longitude) < EPSILON;
        }
        return false;
    }
}
