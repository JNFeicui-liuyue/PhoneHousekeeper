package contacts.feicui.edu.phonehousekeeper.engine;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import contacts.feicui.edu.phonehousekeeper.bean.AppBean;
import contacts.feicui.edu.phonehousekeeper.ui.RjglActivity;

/**
 * Created by liuyue on 2016/5/18.
 */
public class RjglProvider {
    //自定义一个方法，拿到需要的应用信息，传入当前的上下文
    public static List<AppBean> getAppInfo(RjglActivity context) {
        //上下文拿到packagemanager的对象
        PackageManager pm = context.getPackageManager();
        //通过对象拿到集合
        List<PackageInfo> packages = pm.getInstalledPackages(0);

        //定义一个数据集合
        List<AppBean> mData = new ArrayList<>();

        //遍历pm的集合 拿到里面的数据
        for (PackageInfo info : packages) {
            //拿到图标
            Drawable icon = info.applicationInfo.loadIcon(pm);
            //拿到应用程序的名称
            String name = info.applicationInfo.loadLabel(pm).toString();
            //拿到应用程序的包名
            String packageName = info.applicationInfo.packageName;
            //拿到应用程序的版本号
            String versionName = info.versionName;

            AppBean bean = new AppBean();
            bean.icon = icon;
            bean.name = name;
            bean.packageName = packageName;
            bean.versionName = versionName;
            //Log.d(TAG, "getAppInfo: "+name+"/"+packageName+"/"+versionName);
            mData.add(bean);
        }

        return mData;
    }
}
