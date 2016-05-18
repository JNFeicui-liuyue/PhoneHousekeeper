package contacts.feicui.edu.phonehousekeeper.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import contacts.feicui.edu.phonehousekeeper.R;
import contacts.feicui.edu.phonehousekeeper.bean.HardwareBean;
import static contacts.feicui.edu.phonehousekeeper.R.layout.item_hardware_info;
/**
 * Created by liuyue on 2016/5/18.
 */
public class Info extends AppCompatActivity{

    ListView mListView;

    ArrayList<HardwareBean> mData;

    private int[] pic = {R.drawable.setting_info_icon_version,
            R.drawable.setting_info_icon_space,
            R.drawable.setting_info_icon_cpu,
            R.drawable.setting_info_icon_camera,
            R.drawable.setting_info_icon_root};
    String title[];
    String info[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(item_hardware_info);

        mListView = (ListView) findViewById(R.id.lv_hardware_info);

        mData = new ArrayList<>();

        title = new String[]{"设备名称：" + Build.BRAND,
                "全部运行内存" + MemoryManager.getFileSize(MemoryManager.getPhoneTotalRamMemory()),
                "CPU名称：" + MemoryManager.getPhoneCpuName(),
                "手机分辨率：" + MemoryManager.getResolution(Info.this),
                "基带版本：" + MemoryManager.getPhoneSystemBasebandVersion()};

        info = new String[]{"系统版本：" + Build.VERSION.RELEASE,
                "剩余运行内存：" + MemoryManager.getPhoneFreeRamMemory(this) / 1024 / 1024 + "M",
                "cpu数量：" + String.valueOf(MemoryManager.getPhoneCpuNumber()),
                "相机分辨率：1111",
                "是否ROOT：" + (MemoryManager.isRoot() ? "是" : "否")};

        for (int i = 0; i < pic.length; i++) {
            HardwareBean information = new HardwareBean(pic[i], title[i], info[i]);
            mData.add(information);
            Log.d("啊", "onCreate: " + pic[i]);
            mListView.setAdapter(new InfoAdapter());

        }

    }

    private class InfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mData != null){
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = View.inflate(Info.this, R.layout.item_hardware_info,null);
            }
            ImageView pic = (ImageView) findViewById(R.id.iv_phonemgr_icon);
            TextView title = (TextView) findViewById(R.id.tv_phonemgr_title);
            TextView info = (TextView) findViewById(R.id.tv_phonemgr_text);
            HardwareBean bean = (HardwareBean) getItem(position);
            pic.setImageResource(bean.pic);
            if (position % 2 == 0) {
                pic.setBackgroundColor(Color.parseColor("#FF4081"));
            } else {
                if (position == 1) {
                    pic.setBackgroundColor(Color.parseColor("#00CD00"));
                } else {
                    pic.setBackgroundColor(Color.parseColor("#CDCD00"));
                }
            }
            title.setText(bean.title);
            info.setText(bean.info);
            return convertView;
        }
    }
}
