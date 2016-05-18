package contacts.feicui.edu.phonehousekeeper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import contacts.feicui.edu.phonehousekeeper.R;
import contacts.feicui.edu.phonehousekeeper.bean.HomeBean;
import contacts.feicui.edu.phonehousekeeper.utils.Constants;
import contacts.feicui.edu.phonehousekeeper.utils.SpUtils;

/**
 * Created by liuyue on 2016/5/18.
 */
public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static final int SJFD = 0;
    public static final int TXWS = 1;
    public static final int RJGL = 2;
    public static final int JCGL = 3;
    public static final int HCQL = 4;
    public static final int SZZX = 5;
    public static final String TAG = "HomeActivity";

    private GridView mGridView;

    private ArrayList<HomeBean> mDatas;

    private String[] desc = {"手机防盗", "通讯卫士",
            "软件管理", "进程管理",
            "缓存清理", "设置中心"};

    private int[] icons = {R.mipmap.icon_phonemgr, R.mipmap.icon_telmgr,
            R.mipmap.icon_softmgr, R.mipmap.icon_rocket,
            R.mipmap.icon_sdclean, R.mipmap.icon_filemgr};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //初始化数据
        initData();

//        PackageManager pm = this.getPackageManager();
//        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
//        //Log.d(TAG, "PackageManager: " + packages.toString());
//
//        for (int i = 0; i < packages.size(); i++) {
//            Pol
//            if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) == 0) {
//                // 与运算出来的结果 如果不等于0 则是 系统内置的程序
//                // 如果等于0, 则是用户安装的程序
//                Log.d(TAG, "PackageInfo: " + pi);
//
//            }
//
//        }

        mGridView = (GridView) findViewById(R.id.gv_home);
        mGridView.setAdapter(new HomeAdapter());

        mGridView.setOnItemClickListener(this);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            HomeBean bean = new HomeBean();
            bean.pic = icons[i];
            bean.desc = desc[i];
            mDatas.add(bean);

        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case SJFD:
                clickSJFD();
//              Toast.makeText(HomeActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
                break;
            case TXWS:
                Toast.makeText(HomeActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                break;
            case RJGL:
                Toast.makeText(HomeActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(HomeActivity.this, RjglActivity.class);
                startActivity(intent1);
                break;
            case JCGL:
                Toast.makeText(HomeActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                break;
            case HCQL:
                Toast.makeText(HomeActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                break;
            case SZZX:
                Toast.makeText(HomeActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, Info.class);
                startActivity(intent);
                break;
        }

    }

    private void clickSJFD() {

        //取出password
        String password = SpUtils.getString(this, Constants.SJFD_PWD);
        Log.d(TAG, "clickSJFD:" + password);

        //TextUtils.isEmpty
        if (TextUtils.isEmpty(password)) {
            showSetupDialog();
        } else {
            showEnterDialog();
        }

    }

    private void showEnterDialog() {
        // Toast.makeText(HomeActivity.this,"点击了Item，密码不为空",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_login, null);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_enter_pwd);
        Button btnLogin = (Button) view.findViewById(R.id.btn_login);
        Button btnDismiss = (Button) view.findViewById(R.id.btn_dismiss);

        builer.setView(view);
        final AlertDialog dialog = builer.show();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = etPwd.getText().toString().trim();
                //非空校验
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "输入的密码不能为空", Toast.LENGTH_SHORT).show();

                    //获得焦点
                    //etPwd.requestFocus();
                    return;
                }

                String savedPwd = SpUtils.getString(getApplicationContext(), Constants.SJFD_PWD);
                //正确校验
                if (!savedPwd.equals(password)) {
                    Toast.makeText(getApplicationContext(), "输入的密码有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();




                Intent intent = new Intent(HomeActivity.this, SjfdSetupActivity.class);
                startActivity(intent);
            }
        });

    }

    //初始化密码
    private void showSetupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.dialog_setup, null);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd1);
        final EditText etConmitPwd = (EditText) view.findViewById(R.id.et_pwd2);

        Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击以后要校验文本框的内容
                String pwd = etPwd.getText().toString().trim();
                String confirmPwd = etConmitPwd.getText().toString().trim();

                //非空判断
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //相等判断
                if (!pwd.equals(confirmPwd)) {
                    Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();

                    //etPwd.requestFocus();
                    return;
                }

//                StringBuffer sb = new StringBuffer();
//                try {
//                    MessageDigest digester = MessageDigest.getInstance("MD5");
//
//                    byte[] bytes = digester.digest(pwd.getBytes());
//
//                    for (byte b : bytes) {
//
//                        String hexString = Integer.toHexString(b);
//                        sb.append(hexString);
//                        Log.d(TAG, "hexString:" + hexString);
//                        pwd = sb.toString();
//                    }
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }

                //保存edittext里面的内容
                SpUtils.putString(getApplicationContext(), Constants.SJFD_PWD,pwd);
                Log.d(TAG, "clickSJFD:" + pwd);
                Toast.makeText(HomeActivity.this, "密码保存成功", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }

    private class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDatas != null) {
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mDatas != null) {
                return mDatas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(HomeActivity.this, R.layout.item_grid_list, null);
            }
            ImageView ivIcons = (ImageView) convertView.findViewById(R.id.item_iv_pic);
            TextView tvDesc = (TextView) convertView.findViewById(R.id.item_tv_desc);

            HomeBean bean = mDatas.get(position);
            ivIcons.setImageResource(bean.pic);
            tvDesc.setText(bean.desc);

            return convertView;
        }
    }
}
