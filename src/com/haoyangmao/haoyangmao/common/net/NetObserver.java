package com.haoyangmao.haoyangmao.common.net;

import java.io.IOException;
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
     * @return 读取完全的对象
     */
    Object onInputStream(InputStream in) throws IOException;
    
    /**
     * 在UI线程中返回
     * @param result
     */
    void onPostExecute(Object result);
    
    /**
     * 请求过程中遇到异常
     * @param ex
     */
    void onException(Exception ex);
}
