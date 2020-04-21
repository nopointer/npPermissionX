package npPermission.nopointer.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import npPermission.nopointer.log.ycPerLog;

/**
 * 权限工具，用于跳转各个平台的权限管理页面
 */
public class PermissionPageUtils {
    private PermissionPageUtils() {
    }


    public static void jumpPermissionPage(Context context) {
        String name = Build.MANUFACTURER.toUpperCase();
        String packageName = context.getPackageName();
        ycPerLog.e("jumpPermissionPage --- name : " + name);
        try {
            switch (name) {
                case "HUAWEI":
                    goHuaWeiMainager(packageName, context);
                    break;
                case "VIVO":
                    //贱商,fuck vivo的软件管家包名：com.iqoo.secure
                    goVivoMainager(context);
                    break;
                case "OPPO":
                    //贱商,fuck oppo的软件管家包名：com.oppo.safe
                    goOppoMainager(context);
                    break;
                case "COOLPAD":
                    goCoolpadMainager(context);
                    break;
                case "MEIZU":
                    goMeizuMainager(packageName, context);
                    break;
                case "XIAOMI":
                    goXiaoMiMainager(packageName, context);
                    break;
                case "SAMSUNG":
                    goSangXinMainager(context);
                    break;
                case "SONY":
                    goSonyMainager(packageName, context);
                    break;
                case "LG":
                    goLGMainager(packageName, context);
                    break;
                default:
                    goIntentSetting(context);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }

    private static void goLGMainager(String packageName, Context mContext) {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            ycPerLog.e("跳转失败" + e.getMessage());
            e.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static void goSonyMainager(String packageName, Context mContext) {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            ycPerLog.e("跳转失败" + e.getMessage());
            e.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static void goHuaWeiMainager(String packageName, Context mContext) {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            ycPerLog.e("跳转失败" + e.getMessage());
            e.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private static void goXiaoMiMainager(String packageName, Context mContext) {
        String rom = getMiuiVersion();
        ycPerLog.e("goMiaoMiMainager --- rom : " + rom);
        Intent intent = new Intent();
        if ("V6".equals(rom) || "V7".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else if ("V8".equals(rom) || "V9".equals(rom) || "V10".equals(rom) || "V11".equals(rom) || "V12".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else {
            goIntentSetting(mContext);
        }
        mContext.startActivity(intent);
    }

    private static void goMeizuMainager(String packageName, Context mContext) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static void goSangXinMainager(Context mContext) {
        //三星4.3可以直接跳转
        goIntentSetting(mContext);
    }

    private static void goIntentSetting(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void goOppoMainager(Context mContext) throws PackageManager.NameNotFoundException {
        doStartApplicationWithPackageName("com.coloros.safecenter", mContext);
    }

    /**
     * doStartApplicationWithPackageName("com.yulong.android.security:remote")
     * 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
     * startActivity(open);
     * 本质上没有什么区别，通过Intent open...打开比调用doStartApplicationWithPackageName方法更快，也是android本身提供的方法
     */
    private static void goCoolpadMainager(Context mContext) throws PackageManager.NameNotFoundException {
        doStartApplicationWithPackageName("com.yulong.android.security:remote", mContext);
      /*  Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        startActivity(openQQ);*/
    }

    private static void goVivoMainager(Context mContext) throws PackageManager.NameNotFoundException {
//        doStartApplicationWithPackageName("com.bairenkeji.icaller", mContext);
//        doStartApplicationWithPackageName("com.vivo.permissionmanager", mContext);
        doStartApplicationWithPackageName("com.iqoo.secure", mContext);
     /*   Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.vivo.securedaemonservice");
        startActivity(openQQ);*/
    }

    /**
     * 此方法在手机各个机型设置中已经失效
     *
     * @return
     */
    private static Intent getAppDetailSettingIntent(Context mContext) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        return localIntent;
    }

    private static void doStartApplicationWithPackageName(String packagename, Context mContext) throws PackageManager.NameNotFoundException {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
            if (packageinfo == null) {
                return;
            }
            // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageinfo.packageName);
            // 通过getPackageManager()的queryIntentActivities方法遍历
            List<ResolveInfo> resolveinfoList = mContext.getPackageManager()
                    .queryIntentActivities(resolveIntent, 0);
            ycPerLog.e("resolveinfoList" + resolveinfoList.size());
            for (int i = 0; i < resolveinfoList.size(); i++) {
                ycPerLog.e(resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
            }
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                // packageName参数2 = 参数 packname
                String packageName = resolveinfo.activityInfo.packageName;
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityname]
                String className = resolveinfo.activityInfo.name;
                // LAUNCHER Intent
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 设置ComponentName参数1:packageName参数2:MainActivity路径
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    goIntentSetting(mContext);
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw e;
        }

    }
}