package com.binderpool.demo.binder;

import android.os.RemoteException;

import com.binderpool.demo.ICompute;

/**
 * @author lary.huang on 16/7/25.
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
