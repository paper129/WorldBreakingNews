package leslie.worldbreakingnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

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
import java.util.List;
import java.util.Map;

/**
 * Created by Leslie on 5/21/2021.
 */

public class news_subtopic extends AppCompatActivity {
    int page;
    int position;
    String pub;
    private ListView lv;
    List<Map<String, Object>> mList;
    String url="";
    private ProgressDialog pd;
    int TOTAL;


    private ImageView imageView;
    ArrayList<List_Item> arrayList;

    String name[],author[],title[],description[],img_url[],time[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_subtopic);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.lv);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        page = bundle.getInt("page");
        position=bundle.getInt("position");
        pub = bundle.getString("title");
        getSupportActionBar().setTitle(pub);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Runtime.getRuntime().gc();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        if(page==1)
        {
            switch (position)
            {
                case 0:
                    url="https://newsapi.org/v2/everything?domains=mingpao.com&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 1:
                    url="https://newsapi.org/v2/everything?domains=hk.on.cc&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 2:
                    url="https://newsapi.org/v2/everything?domains=yahoo.com&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 3:
                    url="https://newsapi.org/v2/everything?domains=ettoday.net&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 4:
                    url="https://newsapi.org/v2/everything?domains=hk01.com&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 5:
                    url="https://newsapi.org/v2/everything?domains=stheadline.com&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 6:
                    url="https://newsapi.org/v2/everything?domains=rthk.hk&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 7:
                    url="https://newsapi.org/v2/everything?domains=appledaily.com&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 8:
                    url="https://newsapi.org/v2/everything?domains=ntdtv.com&sortBy=publishedAt&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;

            }
        }
        else
        {
            switch (position)
            {
                case 0:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 1:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&category=business&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 2:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&category=entertainment&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 3:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&category=health&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 4:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&category=science&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 5:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&category=sports&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
                case 6:
                    url = "https://newsapi.org/v2/top-headlines?country=hk&category=technology&apiKey=307781e9e6ca4234a05abe536b55252d";
                    break;
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    //get from data to JSON_OBJECT
                    JSONObject JSON_oj = new JSONObject(response);
                    String STATUS = JSON_oj.getString("status");

                    //get from data to JSON_ARRAY
                    JSONArray articles_array = JSON_oj.getJSONArray("articles");
                    TOTAL = articles_array.length();
                    name=new String[TOTAL];
                    author = new String[TOTAL];
                    title=new String[TOTAL];
                    description = new String[TOTAL];
                    img_url = new String[TOTAL];
                    time = new String[TOTAL];



                    for (int i = 0; i < articles_array.length(); i++) {
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

                                Log.d("INFO",title[i]);
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
                    Log.d("------------>","ERROR1");
                    e.printStackTrace();
                    pd.dismiss();

                }
                SharedPreferences SystemInfo = getSharedPreferences("data", Context.MODE_PRIVATE);
                String data1 = SystemInfo.getString("font_size", "22");
                String data2 = SystemInfo.getString("font_Style","font1");
                CustomListAdapter adapter = new CustomListAdapter(news_subtopic.this,R.layout.list_item1,arrayList,data1,data2);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(news_subtopic.this, news_detail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title",title[position]);
                        bundle.putString("description",description[position]);
                        bundle.putString("publishedAt",time[position]);
                        bundle.putString("urlToImage",img_url[position]);
                        bundle.putString("name",name[position]);
                        bundle.putString("author",author[position]);
                        intent.putExtras(bundle);
                        Log.d("position",Integer.toString(position));
                        startActivity(intent); // start Intent
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });
        pd = new ProgressDialog(this);
        pd.setMessage("Loading..");
        pd.setTitle("Getting Data");
        pd.show();
        queue.add(strReq);

    }

}

