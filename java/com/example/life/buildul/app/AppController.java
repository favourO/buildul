package com.example.life.buildul.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private Context mCtx;
    private RequestQueue requestQueue;

    private static AppController mInstance;

    private AppController(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized AppController getInstance(Context context){
        if (mInstance == null){
            mInstance = new AppController(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
