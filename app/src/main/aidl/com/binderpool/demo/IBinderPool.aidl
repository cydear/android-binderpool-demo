// IBinderPool.aidl
package com.binderpool.demo;

// Declare any non-default types here with import statements

interface IBinderPool {
   /**
    * @param binderCode
    * @return Binder
    */
   IBinder queryBinder(int binderCode);
}
