package contacts.feicui.edu.phonehousekeeper.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import contacts.feicui.edu.phonehousekeeper.R;

/**
 * Created by liuyue on 2016/5/18.
 */
public class HardwareInfoActivity extends AppCompatActivity{

    private static final String TAG = "HardWareInfoActivity";

    private ListView mListView;

    private ArrayList<String> mData;

    Context mContext;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_hardware_info);

        mListView = (ListView) findViewById(R.id.lv_hardware_info);

        mData = new ArrayList<>();

        mContext = HardwareInfoActivity.this;

        TelephonyManager telManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        //设备ID
        String deviceId = telManager.getDeviceId();
        Log.d(TAG,"设备ID==" + deviceId);
        mData.add("设备ID==" + deviceId);
        //拿到电话号码
        String line1Number = telManager.getLine1Number();
        Log.d(TAG,"电话号码=="+line1Number);
        mData.add("电话号码=="+line1Number);
        //网络状态编号
        int networkType = telManager.getNetworkType();
        Log.d(TAG,"网络状态编号=="+networkType);
        mData.add("网络状态编号=="+networkType);
        //国家代码
        String countryIso = telManager.getSimCountryIso();
        Log.d(TAG,"国家代码=="+countryIso);
        mData.add("国家代码=="+countryIso);
        //运营商名
        String operatorName = telManager.getSimOperatorName();
        Log.d(TAG,"运营商名=="+operatorName);
        mData.add("运营商名=="+operatorName);

        WifiManager wifi = (WifiManager) mContext.getSystemService(WIFI_SERVICE);

        int wifiState = wifi.getWifiState();
        Log.d(TAG,"WifiManager:WifiState=="+wifiState);

        WifiInfo wifiInfo = wifi.getConnectionInfo();
        String bssid = wifiInfo.getBSSID();
        int ipAddress = wifiInfo.getIpAddress();
        int linkSpeed = wifiInfo.getLinkSpeed();
        String macAddress = wifiInfo.getMacAddress();
        String ssid = wifiInfo.getSSID();
        int networkId = wifiInfo.getNetworkId();
        Log.d(TAG,"WifiManager:bssid=="+bssid);
        Log.d(TAG,"WifiManager:ipAddress=="+ipAddress);
        Log.d(TAG,"WifiManager:linkSpeed=="+linkSpeed);
        Log.d(TAG,"WifiManager:macAddress=="+macAddress);
        Log.d(TAG,"WifiManager:ssid=="+ssid);
        Log.d(TAG,"WifiManager:networkId=="+networkId);

        int sdkInt = Build.VERSION.SDK_INT;
        Log.d(TAG,"SDK:sdkInt=="+sdkInt);
        String os = Build.VERSION.BASE_OS;
        Log.d(TAG,"SDK:os=="+os);
        String release = Build.VERSION.RELEASE;
        Log.d(TAG,"SDK:release=="+release);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1, mData);
        mListView.setAdapter(adapter);

    }
}
