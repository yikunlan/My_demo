package com.example.ykhuang.mydemo.webviewcache.cache;


import android.content.Context;
import android.text.TextUtils;


import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;


public class LocalResourcesCacheIndex {
    private static LocalResourcesCacheIndex localResourcesCacheIndex;
    private ConcurrentHashMap<String ,String> assetsResourcesIndex;
    private ConcurrentHashMap<String ,String> localResourcesIndex;
    private static Context mContext;

    private boolean isInitAssetFinish = false;
    private boolean isInitLocalFinish = false;
    private LocalResourcesCacheIndex() {
        // TODO: 2017/12/20 持久化两个链表，启动的时候取出，防止每次都缓存一遍

        assetsResourcesIndex = new ConcurrentHashMap<>();

        localResourcesIndex = new ConcurrentHashMap<>();
    }

    public static LocalResourcesCacheIndex getInstance(Context context) {
        if (localResourcesCacheIndex == null) {
            localResourcesCacheIndex = new LocalResourcesCacheIndex();
            try {
                mContext = context;
                localResourcesCacheIndex.initAsssetsResourcesIndex(context);
                localResourcesCacheIndex.initLocalResourcesIndex(context);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return localResourcesCacheIndex;
    }

    /**
     * 初始化Assets下资源目录
     *
     * @param context
     */
    public void initAsssetsResourcesIndex(Context context) throws IOException {
        String[] resourcesPath = context.getAssets().list(LocalResourcesManager.ASSEST);
        for (String s : resourcesPath) {
            getAllFilePathByLoop(context, new StringBuffer(LocalResourcesManager.ASSEST).append("/").append(s));
        }
    }

    /**
     * 将缓存下所有的文件目录地址读到索引里
     * @param context
     * @param sb
     * @throws IOException
     */
    private void getAllFilePathByLoop(Context context, StringBuffer sb) throws IOException {
        if (sb.indexOf(".") > -1) {
            assetsResourcesIndex.put(sb.toString(),sb.toString());
        } else {
            String[] temp = context.getAssets().list(sb.toString());
            for (String s : temp) {
                getAllFilePathByLoop(context, new StringBuffer(sb).append("/").append(s));
            }
        }

    }


    /**
     * 初始化本地下载的缓存目录
     *
     * @param context
     */
    public void initLocalResourcesIndex(Context context) {
        File rootFile = context.getApplicationContext().getExternalFilesDir(LocalResourcesManager.LOCAL_CACHE);
        this.getAllfilePathByLoop(localResourcesIndex, rootFile);
    }

    /**
     * 递归遍历缓存目录
     *
     * @param file
     */
    private void getAllfilePathByLoop(LinkedList<String> list, File file) {
        File[] listFile = file.listFiles();
        for (File tempFile : listFile) {
            if (tempFile.isDirectory()) {
                getAllfilePathByLoop(list, tempFile);
            }
            if (tempFile.isFile()) {
                list.add(tempFile.getAbsolutePath());
//                isDeleFile(tempFile);
            }
        }
    }
    private void getAllfilePathByLoop(ConcurrentHashMap<String,String> map, File file) {
        File[] listFile = file.listFiles();
        if(listFile == null){
            //这边会出现空的情况是用户拒绝了读写SD卡的授权，虽然程序可以运行但是会变得特别卡
            return;
        }
        for (File tempFile : listFile) {
            if (tempFile.isDirectory()) {
                getAllfilePathByLoop(map, tempFile);
            }
            if (tempFile.isFile()) {
                map.put(tempFile.getAbsolutePath(),tempFile.getAbsolutePath());
            }
        }
    }

    /**
     * 判断超过30天没有用就删掉这个文件缓存
     * @param file
     */
//    private void isDeleFile(File file){
//        long fileLastUseTime = SPUtil.getInstance(mContext).getFileLastSaveTime(file.getName());
//        //30天的超时时间
//        int lastUseTime = (int) ((System.currentTimeMillis() - fileLastUseTime)/(1000*60*60*24));
//        if(lastUseTime >= 30){
//            file.delete();
//        }
//    }


    /**
     * 判断外部资源在assets下是否有缓存
     *
     * @param outerResourcePath
     * @return
     */
    public boolean isExistAssetsCache(String outerResourcePath) {
        String assetPath = this.assetsResourcesIndex.get(outerResourcePath);
        if(!TextUtils.isEmpty(assetPath)){
            //如果缓存文件被使用了，更新一下使用的时间
//            SPUtil.getInstance(mContext).putFileUseTime(assetPath,System.currentTimeMillis());
            return true;
        }
        return false;
    }

    /**
     * 判断外部资源在本地是否有缓存
     *
     * @param outerResourcePath
     * @return
     */
    public boolean isExistLocalCache(String outerResourcePath) {
        String tempPath = this.localResourcesIndex.get(outerResourcePath);
        if(!TextUtils.isEmpty(tempPath)){
//            SPUtil.getInstance(mContext).putFileUseTime(tempPath,System.currentTimeMillis());
            return true;
        }
        return false;
    }

    /**
     * 增加文件索引
     * @param localPath
     */
    public void addLocalResourcesIndex(String localPath){
        this.localResourcesIndex.put(localPath,localPath);
    }
}
