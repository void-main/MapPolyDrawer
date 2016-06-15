package guru.voidmain.mappolydrawerlib.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import guru.voidmain.mappolydrawerlib.utils.GeoUtils;

/**
 * Defines polygon points in map
 * Created by voidmain on 16/6/12.
 */
public class MapPolygon implements Cloneable {
    protected String mUid = null;

    public String getUid() {
        return mUid;
    }
    /**
     * User points, the `concrete` points.
     * User points are created when user touches on the map, or drags a `control point`
     */
    private List<LatLngWrapper> mUserPoints = null;

    /**
     * Controls points, the `abstract` points.
     * Control points are created for every pair of `user points`,
     * it is located center of the two `user points`
     */
    private List<LatLngWrapper> mControlPoints = null;

    /**
     * If the polygon is closed or not
     */
    private boolean mClosePolygon = false;

    public MapPolygon() {
        mUid = UUID.randomUUID().toString();
        mClosePolygon = false;
        mUserPoints = new ArrayList<>();
        mControlPoints = new ArrayList<>();
    }

    public MapPolygon(List<LatLngWrapper> userPoints) {
        mUid = UUID.randomUUID().toString();
        mClosePolygon = true;
        mUserPoints = userPoints;
        mControlPoints = new ArrayList<>();
        refreshControlPoints();
    }

    public MapPolygon(JSONArray array) {
        this();
        for (int idx = 0; idx < array.length(); idx++) {
            String str;
            try {
                str = (String) array.get(idx);
                String[] parts = str.split(",");
                mUserPoints.add(new LatLngWrapper(Double.valueOf(parts[1]), Double.valueOf(parts[0])));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mClosePolygon = true;

        refreshControlPoints();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserPoints: ");
        for (LatLngWrapper latLng : mUserPoints) {
            sb.append("(" + latLng.latitude + "," + latLng.longitude + "), ");
        }
        sb.append("| ControlPoints: ");
        for (LatLngWrapper latLng : mControlPoints) {
            sb.append("(" + latLng.latitude + "," + latLng.longitude + "), ");
        }
        sb.append("| ClosePolygon: ");
        sb.append(mClosePolygon);

        return sb.toString();
    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < mUserPoints.size(); i++) {
            sb.append("\"");
            LatLngWrapper ll = mUserPoints.get(i);
            sb.append(ll.longitude + "," + ll.latitude);
            sb.append("\"");

            if (i != mUserPoints.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MapPolygon cloned = (MapPolygon)super.clone();
        cloned.mUserPoints = new ArrayList<>(mUserPoints);
        cloned.mControlPoints = new ArrayList<>(mControlPoints);
        cloned.mClosePolygon = mClosePolygon;

        return cloned;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MapPolygon) {
            MapPolygon other = (MapPolygon) o;
            return other.mUid.equals(mUid);
        }

        return false;
    }

    /**
     * Check if polygon is closed
     * @return true if closed false otherwise
     */
    public boolean isClosePolygon() {
        return mClosePolygon;
    }

    /**
     * Update close state for polygon
     * If set to true, also create a new control point
     * @param closePolygon
     */
    public void setClosePolygon(boolean closePolygon) {
//        if (closePolygon && !isCloseable()) {
//            throw new CloseNotClosablePolygonException("Trying to close a polygon that has less than 2 vertexes");
//        }

        this.mClosePolygon = closePolygon;
        refreshControlPoints();
    }

    /**
     * Checks if polygon is valid to close
     * for now it checks if there are more than 2 points
     * TODO checks if points are on the same line
     * @return
     */
    public boolean isCloseable() {
        return mUserPoints.size() > 2;
    }

    /**
     * Checks if the polygon is valid
     * for now it checks if the polygon is closed
     * TODO checks if edges collides
     * @return
     */
    public boolean isValid() {
        return isClosePolygon();
    }

    /**
     * Add a user point
     * @param point
     */
    public void addUserPoint(LatLngWrapper point) {
        mUserPoints.add(point);
        refreshControlPoints();
    }

    /**
     * Move a user point at index to point
     * @param index
     * @param point
     */
    public void moveUserPoint(int index, LatLngWrapper point) {
        LatLngWrapper oldPoint = mUserPoints.get(index % mUserPoints.size());
        oldPoint.latitude = point.latitude;
        oldPoint.longitude = point.longitude;
        mUserPoints.set(index % mUserPoints.size(), oldPoint);
        refreshControlPoints();
    }

    /**
     * Combines user points with control points
     * @return combined points
     */
    public List<LatLngWrapper> allPoints() {
        List<LatLngWrapper> points = new ArrayList<>();
        for (int idx = 0; idx < mUserPoints.size(); idx++) {
            points.add(mUserPoints.get(idx));

            if (idx < mControlPoints.size()) {
                points.add(mControlPoints.get(idx));
            }
        }

        if (isClosePolygon() && mUserPoints.size() > 0)
            points.add(mUserPoints.get(0));

        return points;
    }

    /**
     * Get vertexes of the polygon
     * @return vertexes of the polygon
     */
    public List<LatLngWrapper> polygonPoints() {
        return mUserPoints;
    }

    /**
     * Remove all points includes user points and control points
     */
    public void clearPoints() {
        setClosePolygon(false);
        mUserPoints.clear();
        mControlPoints.clear();
    }

    /**
     * Confirm a control point as a user point
     * which removes that control point and add it as a user point.
     * When user drags a control point, first confirm it as a user point,
     * then moves it using moveUserPoint method.
     * @param index
     */
    public void confirmControlPoint(int index) {
        LatLngWrapper point = mControlPoints.remove(index);
        mUserPoints.add(index + 1, point);
        refreshControlPoints();
    }

    /**
     * Whenever user point changes (add, remove, move)
     * call this method to update corresponding control points
     */
    private void refreshControlPoints() {
        mControlPoints.clear();

        if (mUserPoints.size() > 1) {
            for (int idx = 0; idx < mUserPoints.size() - 1; idx++) {
                LatLngWrapper startPoint = mUserPoints.get(idx);
                LatLngWrapper stopPoint = mUserPoints.get(idx + 1);
                mControlPoints.add(GeoUtils.middlePoint(startPoint, stopPoint));
            }

            if (isClosePolygon()) {
                LatLngWrapper startPoint = mUserPoints.get(0);
                LatLngWrapper stopPoint = mUserPoints.get(mUserPoints.size() - 1);
                mControlPoints.add(GeoUtils.middlePoint(startPoint, stopPoint));
            }
        }
    }

    public boolean isContentEquals(MapPolygon other) {
        if (isClosePolygon() == other.isClosePolygon() &&
                polygonPoints().size() == other.polygonPoints().size()) {
            for (int idx = 0; idx < polygonPoints().size(); idx++) {
                if (!polygonPoints().get(idx).isPointCloseEnough(other.polygonPoints().get(idx))) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
