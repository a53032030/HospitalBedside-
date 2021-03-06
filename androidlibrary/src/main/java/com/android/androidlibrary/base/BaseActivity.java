package com.android.androidlibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.androidlibrary.event.BusFactory;
import com.android.androidlibrary.util.AppManager;
import com.android.androidlibrary.util.KnifeUtil;

import butterknife.Unbinder;




public abstract class BaseActivity extends AppCompatActivity implements UiCallback{
    protected Activity context;
    protected UiDelegate uiDelegate;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            unbinder = KnifeUtil.bind(this);
        }
        setListener();
        initData(savedInstanceState);
    }


    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(this);
        }
        return uiDelegate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUiDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getUiDelegate().pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusFactory.getBus().unregister(this);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getUiDelegate().destory();
    }

}
