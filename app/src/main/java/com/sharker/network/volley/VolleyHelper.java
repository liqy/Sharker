package com.sharker.network.volley;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by liqingyi on 2016/12/29.
 */

public class VolleyHelper {

    private static volatile VolleyHelper singleton;
    private Application app;
    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueueWithSelfCertifiedSsl;
    private RequestQueue mRequestQueueWithDefaultSsl;
    private RequestQueue mRequestQueueWithHttp;

    private VolleyHelper(Application app) {
        this.app = app;
    }

    public static VolleyHelper getVolley() {
        return singleton;
    }

    public static VolleyHelper initInstance(Application app) {
        if (singleton == null) {
            synchronized (VolleyHelper.class) {
                if (singleton == null) {
                    singleton = new VolleyHelper(app);
                }
            }
        }
        return singleton;
    }

    public RequestQueue getRequestQueueWithHttp() {
        if (mRequestQueueWithHttp == null) {
            mRequestQueueWithHttp = Volley.newRequestQueue(app.getApplicationContext());
        }
        return mRequestQueueWithHttp;
    }

    public RequestQueue getRequestQueueWithDefaultSsl() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueueWithDefaultSsl == null) {

            Network network = new BasicNetwork(new HurlStack());

            Cache cache = new DiskBasedCache(app.getCacheDir(), 1024 * 1024);

            RequestQueue queue = new RequestQueue(cache, network);
            queue.start();

            mRequestQueueWithDefaultSsl = queue;  //Volley.newRequestQueue(getApplicationContext());

            SSLCertificateValidation.trustAllCertificate();
        }

        return mRequestQueueWithDefaultSsl;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueueWithSelfCertifiedSsl() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueueWithSelfCertifiedSsl == null) {
            try {
                SSLSocketFactory sslSocketFactory = VolleySSLSocketFactory.getSSLSocketFactory(app.getApplicationContext());

                Network network = new BasicNetwork(new HurlStack(null, sslSocketFactory));

                Cache cache = new DiskBasedCache(app.getCacheDir(), 1024 * 1024);

                RequestQueue queue = new RequestQueue(cache, network);
                queue.start();

                mRequestQueueWithSelfCertifiedSsl = queue;  //Volley.newRequestQueue(getApplicationContext());

            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostName, SSLSession ssls) {
                    return true;
                }
            });
        }

        return mRequestQueueWithSelfCertifiedSsl;
    }
}
