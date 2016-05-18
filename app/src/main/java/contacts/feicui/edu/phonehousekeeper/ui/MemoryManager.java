package contacts.feicui.edu.phonehousekeeper.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by liuyue on 2016/5/18.
 */
public class MemoryManager {

    //全部运行内存
    private static DecimalFormat df = new DecimalFormat("#.00");
    public static String getFileSize(long filesize) {
        StringBuffer mstrbuf = new StringBuffer();
        if (filesize < 1024) {
            mstrbuf.append(filesize);
            mstrbuf.append(" B");
        } else if (filesize < 1048576) {
            mstrbuf.append(df.format((double) filesize / 1024));
            mstrbuf.append(" K");
        } else if (filesize < 1073741824) {
            mstrbuf.append(df.format((double) filesize / 1048576));
            mstrbuf.append(" M");
        } else {
            mstrbuf.append(df.format((double) filesize / 1073741824));
            mstrbuf.append(" G");
        }
        return mstrbuf.toString();
    }

    //获取手机内置sdcard路径，为null表示无
    public static String getPhoneInSDCardPath(){
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }

    /** 设备CPU名称 */
    public static String getPhoneCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机分辨率
     */
    public static String getResolution(Context context) {
        String resolution = "";
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        resolution = metrics.widthPixels + "*" + metrics.heightPixels;
        return resolution;
    }

    /** 设备系统基带版本 */
    public static String getPhoneSystemBasebandVersion() {
        return Build.RADIO;
    }

    /** 设备CPU数量 */
    public static int getPhoneCpuNumber() {
        class CpuFilter implements FileFilter {
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public static boolean isRoot() {
        boolean bool = false;

        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {

        }
        return bool;
    }

    //获取手机外置sdcard路径，为null表示无
    public static String getPhoneOutSDCardPath(){
        Map<String,String> map = System.getenv();
        if (map.containsKey("SECONDARY_STORAGE")){
            String paths = map.get("SECONDARY_STORAGE");
            // /storage/extSdCard
            String path[] = paths.split(":");
            if (path.length<=0){
                return null;
            }
            return path[0];
        }
        return null;
    }

    //设备外置存储SDCard全部大小 单位B，当没有外置卡时，大小为0
    public static long getPhoneOutSDCardSize(Context context){
        try {
            File path = new File(getPhoneOutSDCardPath());
            if (path == null){
                return 0;
            }
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long blockCount = stat.getBlockCount();
            return blockCount*blockSize;
        } catch (Exception e) {
            Toast.makeText(context,"外置存储卡异常",Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    //设备外置存储SDCard空闲大小 单位B
    public static long getPhoneOutSDCardFreeSize(Context context){
        try {
            File path = new File(getPhoneOutSDCardPath());
            if (path == null){
                return 0;
            }
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availaBlock = stat.getAvailableBlocks();
            return availaBlock * blockSize;
        } catch (Exception e) {
            Toast.makeText(context,"外置存储卡异常",Toast.LENGTH_SHORT).show();
            return 0;
        }

    }

    //设备自身存储全部大小 单位B
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSize(){
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockSize * blockCount;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;


        return rootFileSize + cacheFileSize;

    }

    /**
     * 设备自身存储空闲大小 单位B
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfFreeSize() {

        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getAvailableBlocksLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getAvailableBlocksLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }

    /**
     * 获取到的是手机自带的储存空间 单位是byte
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSDCardSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        return blockCount * blockSize;
    }


    /**
     * 获取到的是手机自带的储存空间的剩余空间  单位byte
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSDCardFreeSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availaBlock = stat.getAvailableBlocksLong();
        return availaBlock * blockSize;
    }


    /**
     * 获取手机总存储大小
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneAllSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;


        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long dataFileSize = blockCount * blockSize;


        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;


        return rootFileSize + dataFileSize + cacheFileSize;
    }

    /**
     * 获取手机总闲置存储大小
     */
    public static long getPhoneAllFreeSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getAvailableBlocks();
        long rootFileSize = blockCount * blockSize;


        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long dataFileSize = blockCount * blockSize;


        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }

    /**
     * 设备空闲运行内存 单位B
     */
    public static long getPhoneFreeRamMemory(Context context) {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(info);
        return info.availMem;
    }

    /**
     * 设备完整运行内存 单位B
     */
    public static long getPhoneTotalRamMemory() {
        try {
            FileReader fr = new FileReader("/proc/meminfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");
            return Long.valueOf(array[1]) * 1024; // 原为kb, 转为b
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
