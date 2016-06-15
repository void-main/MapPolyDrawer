package guru.voidmain.mappolydrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    protected ListView mMapsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> bmapItem = new HashMap<>();
        bmapItem.put("displayName", "百度地图");
        bmapItem.put("exampleClass", BaiduMapDrawerActivity.class);
        items.add(bmapItem);

        mMapsList = (ListView)findViewById(R.id.listView);
        mMapsList.setAdapter(new SimpleAdapter(this,
                items,
                android.R.layout.simple_list_item_1,
                new String[] { "displayName" },
                new int[] { android.R.id.text1 }));


        mMapsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                Intent exampleIntent = new Intent(MainActivity.this, (Class) item.get("exampleClass"));
                MainActivity.this.startActivity(exampleIntent);
            }
        });
    }
}
