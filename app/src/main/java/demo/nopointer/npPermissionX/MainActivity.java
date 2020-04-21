package demo.nopointer.npPermissionX;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import demo.nopointer.npPermissionX.base.BasePermissionCheckActivity;
import npPermission.nopointer.core.NpRequestPermissionInfo;
import npPermission.nopointer.log.ycPerLog;


public class MainActivity extends BasePermissionCheckActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int loadLayout() {
        return R.layout.activity_main;
    }

    //适配器
    private FragmentPagerAdapter fragmentPagerAdapter;



    @Override
    public void initView() {
        super.initView();

        //viewpager
        ycViewPager = findViewById(R.id.main_viewPage);

        initViewPager();

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return progressPageList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return progressPageList.get(position);
            }
        };
        ycViewPager.setAdapter(fragmentPagerAdapter);
        ycViewPager.setOffscreenPageLimit(3);



    }

    @Override
    protected NpRequestPermissionInfo loadPermissionsConfig() {
        NpRequestPermissionInfo requestPermissionInfo = new NpRequestPermissionInfo();
        requestPermissionInfo.setPermissionMessage("请授权这些权限");
        requestPermissionInfo.setPermissionCancelText("取消");
        requestPermissionInfo.setPermissionSureText("确定");
        requestPermissionInfo.setAgainPermissionMessage("需要授权啊");
        requestPermissionInfo.setAgainPermissionTitle("11");
        requestPermissionInfo.setAgainPermissionCancelText("取消");
        requestPermissionInfo.setAgainPermissionSureText("确定");

        requestPermissionInfo.setPermissionArr(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
        return requestPermissionInfo;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        ycPerLog.e("获取到了部分的权限" + new Gson().toJson(perms)+this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        ycPerLog.e("拒绝了部分的权限" + new Gson().toJson(perms)+this);
    }

    @Override
    public void onGetAllPermission() {
        super.onGetAllPermission();
        ycPerLog.e("获取到了所有的权限"+this);
    }


    //自定义Viewpager，不可左右滑动
    private ViewPager ycViewPager;
    //所有的Fragment
    private ArrayList<Fragment> progressPageList = new ArrayList<>();



    /**
     * 初始化子页面
     */
    private void initViewPager() {
        progressPageList.clear();
        //添加子组件
        progressPageList.add(new MainFragment());
//        progressPageList.add(new MainFragment());
//        progressPageList.add(new MainFragment());
    }




}
