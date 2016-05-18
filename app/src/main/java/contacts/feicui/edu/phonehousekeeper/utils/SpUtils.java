package contacts.feicui.edu.phonehousekeeper.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**全局的SharedPreferences的工具类
 * Created by liuyue on 2016/5/18.
 */
public class SpUtils {

    public static final String NAME = "safetyApp";
    private static SharedPreferences mPreferences;

    public static void putString(Context context, String key, String value){

        SharedPreferences sp = getPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();//当Editor编辑完成后，调用该方法提交修改

    }

    private static SharedPreferences getPreferences(Context context) {
        if (mPreferences == null){
            //获取只能被本应用程序读写的Preferences对象
            mPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    /*获得一个String类型的数据，如果没有，则返回null
    * context:上下文
    * key:sp里的key
    * return 拿到返回的结果
    * */
    public static String getString(Context context, String key) {
        return getString(context,key,null);
    }

    /*获得一个String类型的数据
    * context:上下文
    * key:sp里的key
    * defValue:sp里的value
    * return 拿到返回的结果
    * */
    //重载
    private static String getString(Context context, String key, String defValue) {

        SharedPreferences sp = getPreferences(context);

        return sp.getString(key,defValue);
    }
}
