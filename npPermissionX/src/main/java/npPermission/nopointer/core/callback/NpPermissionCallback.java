package npPermission.nopointer.core.callback;

import java.util.List;

/**
 * Created by nopointer on 2018/8/20.
 */

public interface NpPermissionCallback {

    //同意了所有权限
    void onGetAllPermission();

    //同意了某些权限可能不是全部
    void onPermissionsGranted(int requestCode, List<String> perms);

    //拒绝了某些权限
    void onPermissionsDenied(int requestCode, List<String> perms);

}
