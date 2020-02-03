package app.com.common;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.com.notifyme.Register_Activity;

public class Singleton {
    private static Singleton Singleton;
    private RequestQueue requestQueue;
    private Context context;


    private Singleton(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized Singleton getInstance(Register_Activity context)
    {
        if(Singleton==null)
            Singleton = new Singleton(context);
        return Singleton;
    }
    public void addToRequestQueue(StringRequest request)
    {

        requestQueue.add(request);

    }
    public void addToJsonRequestQueue(JsonArrayRequest jsonArrayRequest)
    {
        requestQueue.add(jsonArrayRequest);
    }
}