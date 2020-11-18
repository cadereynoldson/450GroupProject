/**
 * Class for requsting and image from the queue.
 */
package com.example.groupproject_g3.io;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Request queue singleton.
 *
 * @author Charles Bryan
 * @version April 2020
 */
public class RequestQueueSingleton {

    /**Private instnace field*/
    private static RequestQueueSingleton instance;

    /**Context instance field*/
    private static Context context;

    /**Request queue object*/
    private RequestQueue mRequestQueue;

    /**Image Loader*/
    private ImageLoader mImageLoader;

    /**
     * Parameterized Contructor for image loading.
     *
     * @param context context.
     */
    private RequestQueueSingleton(Context context) {
        RequestQueueSingleton.context = context;
        mRequestQueue = getmRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Getter for the instance.
     *
     * @param context context.
     * @return instnace.
     */
    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }
        return instance;
    }

    /**
     * Getter for the request queue.
     *
     * @return mRequestQueue.
     */
    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adds a request.
     *
     * @param req req.
     * @param <T> generic.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getmRequestQueue().add(req);
    }

    /**
     * Getter for image loader.
     * @return mImageloader.
     */
    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }
}
