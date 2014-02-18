package com.haoyangmao.haoyangmao.common.net;

import java.io.InputStream;

/**
 * 网络请求的观察者
 * @author michaelzuo
 *
 */
public interface NetObserver {
    
    /**
     * 数据返回
     * @param in
     */
    void onInputStream(InputStream in);
    
    /**
     * 请求过程中遇到异常
     * @param ex
     */
    void onException(Exception ex);

    /**
     * 被取消了
     */
    void onCancel();
}
