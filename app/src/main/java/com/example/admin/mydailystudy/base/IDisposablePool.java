package com.example.admin.mydailystudy.base;

import io.reactivex.disposables.Disposable;

/**
 * Description : 连接池
 */

public interface IDisposablePool {


    void addDisposable(Disposable disposable);

    /**
     * 丢弃连接 在view销毁时调用
     */
    void clearPool();
}