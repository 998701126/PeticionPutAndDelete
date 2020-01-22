package com.example.listvieproductos.models;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.listvieproductos.MainActivity;
import com.example.listvieproductos.helpers.QueueUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Productos {
    public String precio;
    public String descuento;
    public String image;

    public String getSmallImage() {
        return this.image;
    }

    public Productos(String _precio, String _descuento, String _image) {
        this.precio = _precio;
        this.descuento = _descuento;
        this.image = _image;
    }

    public static ArrayList getCollection() {
        ArrayList<Productos> collection = new ArrayList<>();
        collection.add(new Productos("S/.23.00", "Plácido", "https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-7.jpg"));
        collection.add(new Productos("S/.15.00", "López", "https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-8.jpg"));
        collection.add(new Productos("S/.10.00", "Palomino", "https://mymodernmet.com/wp/wp-content/uploads/2019/09/100k-ai-faces-4.jpg"));
        return collection;
    }


    public static void sendRequestPOST(QueueUtils.QueueObject o, final MainActivity _interface) {
        String url = "http://rrojasen.alwaysdata.net/purchaseorders.json";
        url = "http://fipo.equisd.com/api/users/new.json";
        //url = "http://192.168.58.3:8056/api/users/new.json";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Do it with this it will work
                            JSONObject _response = new JSONObject(response);
                            if (_response.has("object")) {
                                JSONObject object_response = null;
                                try {
                                    object_response = _response.getJSONObject("object");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if ( object_response != null ) {
                                    try {
                                        System.out.println(object_response.getInt("id"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name","Jeeny");
                params.put("last_name","Yuliana");
                params.put("avatar","xxx");

                return params;
            }
        };
        o.addToRequestQueue(stringRequest);
    }


    public static void injectProductosFromCloud(final QueueUtils.QueueObject o,
                                                final ArrayList<Productos> productos,
                                                final MainActivity _interface) {
        String url = "http://fipo.equisd.com/api/users.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("objects")) {

                            try {
                                JSONArray list = response.getJSONArray("objects");
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    productos.add(new Productos(o.getString("first_name"),
                                            o.getString("last_name"),(o.getString("avatar"))));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refreshList(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }

}