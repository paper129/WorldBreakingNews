package leslie.worldbreakingnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Leslie on 5/21/2021.
 */

public class menu_HotNewsFragment extends Fragment {
    private ListView lv;
    private ProgressDialog pd;
    private ImageView imageView;
    private ArrayList<List_Item> arrayList;
    private List<Map<String, Object>> mList;
    private String name[],author[],title[],description[],img_url[],time[];
    private int TOTAL;
    private TextView txtView;
    public menu_HotNewsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu__hot_news, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        arrayList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.lv);
        txtView = (TextView) view.findViewById(R.id.txtView);
        final String url = "https://newsapi.org/v2/top-headlines?country=gb&apiKey=2666f39e7229477fb7f95e926a44d151";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {

            public void onResponse(String response) {

                try {
                    //get from data to JSON_OBJECT
                    JSONObject JSON_oj = new JSONObject(response);
                    Log.e("JSONObj", JSON_oj.toString());
                    String STATUS = JSON_oj.getString("status");
                    Log.e("Status", STATUS);
                    TOTAL = 20;
                    //get from data to JSON_ARRAY
                    JSONArray articles_array = JSON_oj.getJSONArray("articles");

                    name=new String[TOTAL];
                    author = new String[TOTAL];
                    title=new String[TOTAL];
                    description = new String[TOTAL];
                    img_url = new String[TOTAL];
                    time = new String[TOTAL];

                    for (int i = 0; i < TOTAL; i++) {
                        JSONObject articles_oj = articles_array.getJSONObject(i);
                        JSONObject Source_oj = articles_oj.getJSONObject("source");

                        description[i] =articles_oj.getString("description");;
                        title[i] = articles_oj.getString("title");
                        img_url[i] = articles_oj.getString("urlToImage");
                        name[i] = Source_oj.getString("name");
                        author[i] = articles_oj.getString("author");
                        time[i] = articles_oj.getString("publishedAt");
                    }

                    for(int i=0;i<TOTAL;i++)
                    {
                        if(description[i]=="null")
                        {
                            for (int j=i+1;j<TOTAL;j++)
                            {
                                description[i] = description[j];
                                title[i] = title[j];
                                img_url[i] = img_url[j];
                                name[i] = name[j];
                                author[i] = author[j];
                                time[i]=time[j];
                            }
                            TOTAL--;
                            i--;
                        }
                        else
                        {
                            int len = img_url[i].length();
                            if(len != 0) {
                                String format = img_url[i].substring(len-3, len);
                                if(format.equals("jpg") || format.equals("png")) {
                                    arrayList.add(new List_Item(img_url[i], title[i]));
                                }
                                else
                                {
                                    img_url[i]="NO_IMG";
                                    arrayList.add(new List_Item(img_url[i], title[i]));
                                }
                            }
                            else
                            {
                                img_url[i]="NO_IMG";
                                arrayList.add(new List_Item(img_url[i], title[i]));
                            }


                        }
                    }

                    pd.dismiss();
                } catch (JSONException e) {
                    pd.dismiss();
                    Log.d("------------>","ERROR1");
                    e.printStackTrace();

                }


                SharedPreferences SystemInfo = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                String data1 = SystemInfo.getString("font_size", "22");
                String data2 = SystemInfo.getString("font_Style","font1");
                CustomListAdapter adapter = new CustomListAdapter(
                        getActivity().getApplicationContext(),R.layout.list_item1,arrayList,data1,data2);
                if(adapter.equals(null))
                {

                }
                else {
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), news_detail.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", title[position]);
                            bundle.putString("description", description[position]);
                            bundle.putString("publishedAt", time[position]);
                            bundle.putString("urlToImage", img_url[position]);
                            bundle.putString("name", name[position]);
                            bundle.putString("author", author[position]);
                            intent.putExtras(bundle);
                            Log.d("position", Integer.toString(position));
                            startActivity(intent); // start Intent
                        }
                    });
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            public String getBodyContentType() {
                return "application/json; charset=" + getParamsEncoding();
            }

            //Header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String uuid = UUID.randomUUID().toString().replace("-", "");
                headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
                //headers.put("newsapi", uuid);
                //headers.put("cf-request-id", "0a27da503e0000229055b21000000001");
                //headers.put("content-type","application/json; charset=utf-8");
                return headers;
            }

        };
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading..");
        pd.setTitle("Getting Data");
        pd.show();
        //strReq.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strReq);

        return view;

    }



}
