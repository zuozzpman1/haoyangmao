
package com.haoyangmao.haoyangmao.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http连接工具类 主要负责 提供 发送 get post请求的方法
 * 
 * @author michaelzuo
 */
public class HttpUtil {

    /**
     * 网络线程池 个数
     */
    private static final int THREAD_POOL_SIZE = 7;

    /**
     * 线程池
     */
    private static ExecutorService sNetworkExecutorService = Executors
            .newFixedThreadPool(THREAD_POOL_SIZE);

    /**
     * 发送get请求
     * 
     * @param userAgent
     * @param url
     * @throws IOException
     * @return 输入流 maybe null
     */
    public static InputStream doGetSync(String userAgent, String url) throws IOException {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance(userAgent);

        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);
        if (response == null) {
            return null;
        }
        
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return null;
        }
        
        return entity.getContent();
    }

    public static void doGetAsync(final String userAgent, String url, final NetObserver observer) {
        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                try {
                    InputStream in = doGetSync(userAgent, params[0]);
                    observer.onInputStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                    observer.onException(e);
                }

                return null;
            }

        }.executeOnExecutor(sNetworkExecutorService, url);
    }

}
