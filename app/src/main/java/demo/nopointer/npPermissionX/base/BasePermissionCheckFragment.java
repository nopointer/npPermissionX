package demo.nopointer.npPermissionX.base;

import android.text.TextUtils;

import java.util.List;

import androidx.annotation.NonNull;
import npPermission.nopointer.core.NpRequestPermissionInfo;
import npPermission.nopointer.core.NpPermissionRequester;
import npPermission.nopointer.core.callback.NpPermissionCallback;
import npPermission.nopointer.log.ycPerLog;


/**
 * Created by nopointer on 2018/9/1.
 * 权限检测器
 */

public abstract class BasePermissionCheckFragment extends BaseFragment implements NpPermissionCallback {

    private NpPermissionRequester ycPermissionRequester = null;

    public void requestPermission(NpRequestPermissionInfo requestPermissionInfo) {

        if (requestPermissionInfo == null) {
            ycPerLog.e("权限列表为空，不请求");
            return;
        }
        if (ycPermissionRequester == null) {
            ycPermissionRequester = new NpPermissionRequester(requestPermissionInfo);
        }
        ycPermissionRequester.requestPermission(this, this);
    }


    @Override
    protected void initView() {
//        requestPermission(loadPermissionsConfig());
    }


    protected NpRequestPermissionInfo loadPermissionsConfig() {
        return null;
    }


    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ycPermissionRequester == null) return;
        ycPermissionRequester.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }


    @Override
    public void onGetAllPermission() {
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (ycPermissionRequester == null) return;
        NpRequestPermissionInfo requestPermissionInfo = ycPermissionRequester.getPermissionInfo();
        if (requestPermissionInfo != null && !TextUtils.isEmpty(requestPermissionInfo.getAgainPermissionMessage())) {
            ycPermissionRequester.checkDeniedPermissionsNeverAskAgain(this, perms);
        }
    }

}
