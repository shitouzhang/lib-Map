package zp.com.baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zpan.gaodelocation.LocationLibManager;
import com.zpan.gaodelocation.bean.LocationFailedBean;
import com.zpan.gaodelocation.bean.LocationSuccessBean;
import com.zpan.gaodelocation.callback.GpsLocationCallback;
import com.zpan.othermap.bean.StartAndEndInfo;
import com.zpan.othermap.utils.GpsUtil;
import com.zpan.othermap.bean.Location;
import com.zpan.othermap.OtherMapManager;

/**
 * @author zpan
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initOtherMap();
            }
        });

        initLocation();
    }

    private void initOtherMap() {
        Location end = new Location(121.53, 31.22);
        Location start = new Location(121.45, 31.23);
        StartAndEndInfo entity = new StartAndEndInfo();
        entity.setStartName("当前位置");
        entity.setStartLocation(start);
        entity.setEndName("上海");
        entity.setEndLocation(end);

        //百度坐标系转成火星坐标系
        double[] d = GpsUtil.bd09_To_Gcj02(entity.getEndLocation().getLatitude(), entity.getEndLocation().getLongitude());
        entity.getEndLocation().setLatitude(d[0]);
        entity.getEndLocation().setLongitude(d[1]);
        double[] d2 = GpsUtil.bd09_To_Gcj02(entity.getStartLocation().getLatitude(), entity.getStartLocation().getLongitude());
        entity.getStartLocation().setLatitude(d2[0]);
        entity.getStartLocation().setLongitude(d2[1]);

        OtherMapManager otherMapManager = new OtherMapManager(MainActivity.this, entity);
        otherMapManager.navigation();
    }

    private void initLocation() {
        LocationLibManager.getInstance().getLocation(this, new GpsLocationCallback() {
            @Override
            public void success(LocationSuccessBean response) {
                // 定位成功
                Log.e("shitou", "success");
            }

            @Override
            public void failed(LocationFailedBean response) {
                // 定位失败
                Log.e("shitou", "failed");
            }

            @Override
            public void complete() {
                // 定位完成
                Log.e("shitou", "complete");
            }
        });
    }
}
