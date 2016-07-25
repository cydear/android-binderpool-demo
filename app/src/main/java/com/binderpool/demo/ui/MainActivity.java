package com.binderpool.demo.ui;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.binderpool.demo.ICompute;
import com.binderpool.demo.ISecurityCenter;
import com.binderpool.demo.R;
import com.binderpool.demo.binder.BinderPool;
import com.binderpool.demo.binder.ComputeImpl;
import com.binderpool.demo.binder.SecurityCenterImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    private Button mBtnDoWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    private void initView() {
        mBtnDoWork = (Button) findViewById(R.id.btn_binder_pool);
    }

    private void initClick() {
        mBtnDoWork.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int _id = view.getId();
        if (_id == R.id.btn_binder_pool) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doWork();
                }
            }).start();
        }
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getsInstance(MainActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloword-安卓";
        System.out.println("content:" + msg);
        try {
            String password = mSecurityCenter.decrypt(msg);
            System.out.println("encrypt:" + password);
            System.out.println("decrypt:" + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
