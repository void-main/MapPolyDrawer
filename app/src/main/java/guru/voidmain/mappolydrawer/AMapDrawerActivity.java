package guru.voidmain.mappolydrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;

import guru.voidmain.mappolydrawer.drawablemaps.AMapDrawableMap;
import guru.voidmain.mappolydrawer.utils.Utils;
import guru.voidmain.mappolydrawerlib.drawer.Drawer;
import guru.voidmain.mappolydrawerlib.stores.ApplicationState;

public class AMapDrawerActivity extends AppCompatActivity {
    MapView mMapView = null;
    AMap mAMap = null;

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
        setContentView(R.layout.activity_amap_drawer);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();

        mCloseButton = (Button) findViewById(R.id.btn_close);
        mClearButton = (Button) findViewById(R.id.btn_clear);
        mConfirmButton = (Button) findViewById(R.id.btn_confirm);
        mUndoButton = (Button) findViewById(R.id.btn_undo);
        mRedoButton = (Button) findViewById(R.id.btn_redo);
        mExportButton = (Button) findViewById(R.id.btn_export);

        mDrawer = new Drawer(new AMapDrawableMap(mMapView));
        mDrawer.setOnStateChangedListener(new Drawer.OnStateChangedListener() {
            @Override
            public void onStateChanged(ApplicationState state) {
                // TODO update UI
            }
        });

        mAMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mDrawer.onMapClicked(latLng);
            }
        });

        mAMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Utils.vibratePhoneShort(AMapDrawerActivity.this);
                mDrawer.onDragMarkerStart(marker);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                mDrawer.onDragMarkerMove(marker, marker.getPosition());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mDrawer.onDragMarkerEnd(marker, marker.getPosition());
            }
        });

        mAMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
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
                Toast.makeText(AMapDrawerActivity.this, mDrawer.getJsonString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }
}
