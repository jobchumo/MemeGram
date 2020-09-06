package com.jobchumo.memegram;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MyVolleyRequest {
    private static MyVolleyRequest mInstance;
    private RequestQueue requestQueue;
    private Context context;
    private IVolley iVolley;
    private ImageLoader imageLoader;

    private MyVolleyRequest (Context context, IVolley iVolley) {
        this.context = context;
        this.iVolley = iVolley;
        requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
           private LruCache<String, Bitmap> cache = new LruCache<>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    private MyVolleyRequest (Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    public static synchronized MyVolleyRequest getInstance(Context context) {
        mInstance = new MyVolleyRequest(context);
        return mInstance;
    }


    public static synchronized MyVolleyRequest getInstance(Context context, IVolley iVolley) {

        mInstance = new MyVolleyRequest(context, iVolley);
        return mInstance;
    }
    private RequestQueue getRequestQueue() {

        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public <T> void addToRequestQueue (Request<T> request) {
        getRequestQueue().add(request);
    }

    public void postRequest (String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iVolley.onResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iVolley.onResponse(error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", "rickastley@gmail.com");
                params.put("password", "rickrolled");
                return super.getParams();
            }
        };
        addToRequestQueue(stringRequest);
    }

//    public void putRequest (String url) {
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        iVolley.onResponse(response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                iVolley.onResponse(error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("Email_Address", "rickastley@gmail.com");
//                params.put("Password", "rickrolled");
//                return super.getParams();
//            }
//        };
//        addToRequestQueue(stringRequest);
//    }

    public void deleteRequest (String url) {
        StringRequest delete = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iVolley.onResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iVolley.onResponse(error.getMessage());
            }
        });
        addToRequestQueue(delete);
    }
}
