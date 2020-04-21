package demo.nopointer.npPermissionX;

import android.Manifest;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import demo.nopointer.npPermissionX.base.BasePermissionCheckFragment;
import npPermission.nopointer.core.NpRequestPermissionInfo;
import npPermission.nopointer.log.ycPerLog;
import npPermission.nopointer.utils.PermissionPageUtils;

public class MainFragment extends BasePermissionCheckFragment {

    @Override
    protected int loadLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        super.initView();
//        requestPermission(loadPermissionsConfig());

        rootView.findViewById(R.id.permission_setting_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionPageUtils.jumpPermissionPage(getActivity());
            }
        });
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
        ycPerLog.e("获取到了部分的权限" + new Gson().toJson(perms) + this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        ycPerLog.e("拒绝了部分的权限" + new Gson().toJson(perms) + this);
    }

    @Override
    public void onGetAllPermission() {
        super.onGetAllPermission();
        ycPerLog.e("获取到了所有的权限" + this);
    }


}
