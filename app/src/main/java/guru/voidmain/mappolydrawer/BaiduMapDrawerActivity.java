package guru.voidmain.mappolydrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

import guru.voidmain.mappolydrawer.drawablemaps.BMapDrawableMap;
import guru.voidmain.mappolydrawer.utils.Utils;
import guru.voidmain.mappolydrawerlib.drawer.Drawer;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;

public class BaiduMapDrawerActivity extends AppCompatActivity {
    private static final String TAG = BaiduMapDrawerActivity.class.getCanonicalName();
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;

    Button mCloseButton = null;
    Button mClearButton = null;
    Button mConfirmButton = null;
    Button mUndoButton = null;
    Button mRedoButton = null;
    Button mExportButton = null;

    Drawer mDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_baidu_map_drawer);
        mMapView = (MapView) findViewById(R.id.bmapView);

        mCloseButton = (Button) findViewById(R.id.btn_close);
        mClearButton = (Button) findViewById(R.id.btn_clear);
        mConfirmButton = (Button) findViewById(R.id.btn_confirm);
        mUndoButton = (Button) findViewById(R.id.btn_undo);
        mRedoButton = (Button) findViewById(R.id.btn_redo);
        mExportButton = (Button) findViewById(R.id.btn_export);

        mDrawer = new Drawer(new BMapDrawableMap(mMapView));
        mDrawer.setOnStateChangedListener(new Drawer.OnStateChangedListener() {
            @Override
            public void onStateChanged(ApplicationState state) {
                // TODO update UI here
            }
        });

        mBaiduMap = mMapView.getMap();

        MapStatusUpdate mapUpdate = MapStatusUpdateFactory.newLatLngZoom(new LatLng(39.914492, 116.403694), 15);
        mBaiduMap.animateMapStatus(mapUpdate);


        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mDrawer.onMapClicked(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                mDrawer.onMapClicked(mapPoi.getPosition());
                return false;
            }
        });

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Utils.vibratePhoneShort(BaiduMapDrawerActivity.this);
                mDrawer.onDragMarkerStart(marker);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                mDrawer.onDragMarkerMove(marker, marker.getPosition());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mDrawer.onDragMarkerEnd(marker);
            }
        });

        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mDrawer.onMapLongClicked(latLng);
            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.onClosePolyline();
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.onClearPoints();
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.onCompletePolygon();
            }
        });

        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.onUndo();
            }
        });

        mRedoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.onRedo();
            }
        });

        mExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaiduMapDrawerActivity.this, mDrawer.getJsonString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}

