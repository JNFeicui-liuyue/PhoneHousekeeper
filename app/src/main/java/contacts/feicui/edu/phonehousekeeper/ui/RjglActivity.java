package contacts.feicui.edu.phonehousekeeper.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import contacts.feicui.edu.phonehousekeeper.R;
import contacts.feicui.edu.phonehousekeeper.bean.AppBean;
import contacts.feicui.edu.phonehousekeeper.engine.RjglProvider;

/**
 * Created by liuyue on 2016/5/18.
 */
public class RjglActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView mLvAppList;

    List<AppBean> mData;

    private static final String TAG = "RjglActivity";

    private RjglAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigl);
        Log.d("HH","6666666666666666666666666");
        mLvAppList = (ListView) findViewById(R.id.lv_list);

        mAdapter = new RjglAdapter();

        mLvAppList.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart:start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume:start");
        mLvAppList.removeAllViewsInLayout();

        //调用自己写的方法 拿到需要的数据 赋值给List对象
        mData = RjglProvider.getAppInfo(RjglActivity.this);
//        Log.d("HH",mData.get(2).packageName);
        mLvAppList.setAdapter(new RjglAdapter());
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppBean appBean = mData.get(position);
        String packageName = appBean.packageName;
        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        startActivity(intent);
    }

    private class RjglAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(mData != null){
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

            ViewHolder holder = null;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.item_show,null);

                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_app_ico);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_app_lable);
                holder.tvPackageName = (TextView) convertView.findViewById(R.id.tv_app_packagename);
                holder.tvVersionName = (TextView) convertView.findViewById(R.id.tv_app_version);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            AppBean bean = (AppBean) getItem(position);
            holder.ivIcon.setImageDrawable(bean.icon);
            holder.tvName.setText(bean.name);
            holder.tvPackageName.setText(bean.packageName);
            holder.tvVersionName.setText(bean.versionName);

            return convertView;
        }
    }
    private class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvPackageName;
        TextView tvVersionName;
    }
}
