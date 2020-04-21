package demo.nopointer.npPermissionX.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


/**
 * Created by wangquan on 18/9/3.
 */

public abstract class BaseActivity extends FragmentActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(loadLayout());
        //初始化组件
        initView();
    }


    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int loadLayout();

    /**
     * 初始化组件
     */
    public abstract void initView();

    /**
     * 是否是dark模式
     *
     * @return
     */
    protected boolean isDarkMode() {
        return true;
    }

    /**
     * Toast
     *
     * @param argMessage
     */
    public void showToast(String argMessage) {
        Toast.makeText(this, argMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast
     *
     * @param argMessageId
     */
    public void showToast(final int argMessageId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, getResources().getString(argMessageId), Toast.LENGTH_SHORT).show();
            }
        });

    }



    /**
     * 跳转界面
     *
     * @param argClass
     */
    public void startActivity(Class<?> argClass) {
        startActivity(new Intent(this, argClass));
    }

    /**
     * 跳转后关闭界面
     *
     * @param argClass
     */
    public void startActivityAndFinish(Class<?> argClass) {
        startActivity(new Intent(this, argClass));
        this.finish();
    }

    /**
     * 跳转界面，加回调
     *
     * @param argClass
     * @param argRequestCode
     */
    public void startActivityForResult(Class<?> argClass, int argRequestCode) {
        startActivityForResult(new Intent(this, argClass), argRequestCode);
    }


}
