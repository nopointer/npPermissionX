package npPermission.nopointer.core.callback;

public abstract class NpPermissionDialogCallback {

    /**
     * 关闭回调
     *
     * @param isAgainAsk 是否是第二次询问
     */
    public abstract void onCancel(boolean isAgainAsk);

    /**
     * 确认回调
     *
     * @param isAgainAsk 是否是第二次询问
     */
    public void onSure(boolean isAgainAsk) {
    }


}
