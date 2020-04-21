package demo.nopointer.npPermissionX.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by wangquan on 18/9/3.
 */

public abstract class BaseFragment extends Fragment {
    //上下文
    public Context context;

    //根布局
    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        //加载框
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(loadLayout(), container, false);
        context = getActivity();
        //加载框
        initView();
        return rootView;
    }


    private Handler handler = new Handler();




    protected abstract int loadLayout();


    protected abstract void initView();

    /**
     * Toast
     *
     * @param argMessage
     */
    public void showToast(String argMessage) {
        Toast.makeText(getActivity(), argMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param argMessage
     */
    public void showToast(int argMessage) {
        Toast.makeText(getActivity(), argMessage, Toast.LENGTH_SHORT).show();
    }



    /**
     * 跳转界面
     *
     * @param argClass
     */
    public void startActivity(Class<?> argClass) {
        startActivity(new Intent(getActivity(), argClass));
    }

    /**
     * 跳转后关闭界面
     *
     * @param argClass
     */
    public void startActivityAndFinish(Class<?> argClass) {
        startActivity(new Intent(getActivity(), argClass));
        getActivity().finish();
    }

    /**
     * 跳转界面，加回调
     *
     * @param argClass
     * @param argRequestCode
     */
    public void startActivityForResult(Class<?> argClass, int argRequestCode) {
        startActivityForResult(new Intent(getActivity(), argClass), argRequestCode);
    }

}
