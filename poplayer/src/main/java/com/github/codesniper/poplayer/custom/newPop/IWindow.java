package com.github.codesniper.poplayer.custom.newPop;

import android.content.Context;
import android.view.View;


/**
 * 统一弹窗接口 只需要自定义类继承T 再实现IWindow即可 例如Dialog为PopDialog
 * @param <T>
 */
public interface IWindow<T> {

    public void setContent(View view);

    public void showPoupo();

    public void cancelPoupo();

    public void dismissPoupo();

    public T getPoupo();

    public Context getEnvironment();

    public boolean isPoupoShowing();

}
