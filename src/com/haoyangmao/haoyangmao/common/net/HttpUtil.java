
package com.haoyangmao.haoyangmao.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
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

//    /**
//     * 同步发送get请求
//     * 
//     * @param userAgent
//     * @param url
//     * @return HttpRequest 可以abort请求
//     */
//    public static HttpUriRequest doGetSync(String userAgent, String url, NetObserver netObserver) {
//        HttpGet httpGet = new HttpGet(url);
//        sendRequestImpl(userAgent, httpGet, netObserver);
//
//        return httpGet;
//    }

    /**
     * 异步发送get请求
     * 
     * @param userAgent
     * @param url
     * @param observer
     * @return HttpRequest 可以abort请求
     */
    public static HttpUriRequest doGetAsync(final String userAgent, String url, final NetObserver observer) {
        HttpGet httpGet = new HttpGet(url);
        
        new AsyncTask<HttpGet, Void, Object>() {

            @Override
            protected Object doInBackground(HttpGet... params) {
                try {
                    return sendRequestImpl(userAgent, params[0], observer);
                } catch (Exception e) {
                    e.printStackTrace();
                    observer.onException(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                observer.onPostExecute(result);
            }
            
            

        }.executeOnExecutor(sNetworkExecutorService, httpGet);
        
        return httpGet;
    }
    
    /**
     * 同步发送post请求
     * @param userAgent
     * @param url
     * @param entity post
     * @param observer
     * @return
     */
    public static HttpUriRequest doPostAsync(final String userAgent, String url, HttpEntity entity, final NetObserver observer) {
        HttpPost httpPost = new HttpPost(url);
        if (entity != null) {
            httpPost.setEntity(entity);
        }
        
        new AsyncTask<HttpPost, Void, Object>() {

            @Override
            protected Object doInBackground(HttpPost... params) {
                try {
                    return sendRequestImpl(userAgent, params[0], observer);
                } catch (Exception e) {
                    e.printStackTrace();
                    observer.onException(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                observer.onPostExecute(result);
            }
            
            

        }.executeOnExecutor(sNetworkExecutorService, httpPost);
        
        return httpPost;
    }

    /**
     * 发送http请求的实现
     * 
     * @param userAgent
     * @param httpRequest
     * @param netObserver
     * @return 得到返回的对象 may be null
     * @throws Exception
     */
    private static Object sendRequestImpl(String userAgent, HttpUriRequest httpRequest,
            NetObserver netObserver) throws Exception {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance(userAgent);
        HttpResponse response;
        try {
            response = httpClient.execute(httpRequest);
            if (response == null) {
                netObserver.onException(new IOException("http response is null"));
            } else {
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    netObserver.onException(new IOException("http entity is null"));
                } else {
                    InputStream in = entity.getContent();

                    try {
                        return netObserver.onInputStream(in);
                    } catch (IOException e) {
                        netObserver.onException(e);
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            netObserver.onException(e1);
        }
        
        return null;
    }

}
