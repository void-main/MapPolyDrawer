package guru.voidmain.mappolydrawerlib.models;

import java.util.UUID;

import guru.voidmain.mappolydrawerlib.common.Constants;

/**
 * Wrapper class that holds latitude and longitude
 * Used for multi-map compatibility
 * Created by voidmain on 16/6/12.
 */
public class LatLngWrapper {
    public double latitude;
    public double longitude;
    public String uid;

    public LatLngWrapper() {
        this.latitude = 0;
        this.longitude = 0;
        this.uid = UUID.randomUUID().toString();
    }

    public LatLngWrapper(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.uid = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LatLngWrapper) {
            LatLngWrapper item = (LatLngWrapper) o;
            return uid.equals(item.uid);
        }
        return false;
    }
}
