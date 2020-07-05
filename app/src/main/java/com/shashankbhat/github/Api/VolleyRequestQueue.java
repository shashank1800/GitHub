package com.shashankbhat.github.Api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by SHASHANK BHAT on 05-Jul-20.
 */
public class VolleyRequestQueue {

    private static  RequestQueue requestQueue;
    private VolleyRequestQueue() {
    }

    public static RequestQueue getInstance(Context context) {

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}
