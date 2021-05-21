package leslie.worldbreakingnews;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import java.util.Locale;
import java.util.Map;

/**
 * Created by Leslie on 5/21/2021.
 */

public class menu_NewsMapFragment extends Fragment {

    //private TextView txtCountry;
    Geocoder geocoder;
    List<Address> addresses;
    private Criteria criteria;
    private LocationListener locationListener;
    private TextView txtcountry;
    private ListView lv;
    private ProgressDialog pd;
    private ImageView imageView;
    private ArrayList<List_Item> arrayList;
    private List<Map<String, Object>> mList;
    private String name[],author[],title[],description[],img_url[],time[];
    private int TOTAL;
    public Boolean state = false;
    CustomListAdapter adapter;
    String blank = ""; //for attach in log.d, useless in program.


    public menu_NewsMapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu__news_map, container, false);
        //txtcountry = (TextView) view.findViewById(R.id.country);
        lv = (ListView) view.findViewById(R.id.lv);

        state = true;






        return view;
    }
    public void getList(String code){

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        arrayList = new ArrayList<>();
        String url = "https://newsapi.org/v2/top-headlines?country="+code+"&apiKey=307781e9e6ca4234a05abe536b55252d";
        Log.d("System Info" , url);
        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    //get from data to JSON_OBJECT
                    JSONObject JSON_oj = new JSONObject(response);
                    String STATUS = JSON_oj.getString("status");
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
                adapter = new CustomListAdapter(
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

                error.printStackTrace();
            }
        }){
            //Header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "Mozilla/5.0");
                headers.put("dnt", "1");
                headers.put("pragma", "no-cache");
                headers.put("newsapi", "1A49BFE6DD2282BEA10A78CFA3369F683D213061CAC2A31E0228DC1EFC7BEDD3343B8E59AA89E7537520BD01DA7B964ABB54254A7B94B27969E748BF580DC8A4C9C72D279D94C8D435BFD773B1526AA32D3A6031AEBCF190301713E01E797B04");
                return headers;
            }
        };

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading..");
        pd.setTitle("Getting Data");
        pd.show();
        //strReq.setRetryPolicy(new DefaultRetryPolicy(1*100, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strReq);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = null;
        if (state) {
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();

            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setCostAllowed(false);
            String provider = locationManager.getBestProvider(criteria, false);


            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
            final Location location = locationManager.getLastKnownLocation(provider);
            try {
                double latitude = 0;
                latitude = location.getLatitude();
                double longitude = 0;
                longitude = location.getLongitude();
                String log = "Latitude : " + latitude + "  " + "Longtitude: " + longitude;
                Log.d("GPS Log String: ", String.valueOf(log));

                if (latitude != 0 && longitude != 0) {
                    //Fetch user location from GPS
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } else {
                    //If failed, set GPS LongLat to Hong Kong (22.3964N, 114.1095E)
                    latitude = 22.3964;
                    longitude = 114.1095;
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                }

                // The below string is used to get different details from the Geocoder API.

                // String address = addresses.get(0).getAddressLine(0);
                // String area = addresses.get(0).getLocality();
                // String city = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();


                Log.d("System Info:", "long " + location.getLongitude() + "\n" + "lat " + location.getLatitude() + "\n" + "country: " + country);


                String[] isoCountryCodes = Locale.getISOCountries(); //Get all countries with country code.
                String countryCode = "";
                String countryName = country;


                for (String code : isoCountryCodes) {
                    // To iterate through all country codes, we use for-loop
                    // This for-loop is to fetch all country and country codes from Locale API.
                    // Create a locale using each country code
                    Locale locale = new Locale("", code);
                    // Get country name for each code.
                    String name = locale.getDisplayCountry();

                    if (name.equals(countryName)) {
                        // if-statement: to compare Locale API and GeoCoder country name match or not.
                        // If there is same country name, fetch the country code and save to local variable countryCode.
                        countryCode = code;

                        //Log Country Code Information
                        Log.d("Country Code Info ", blank);
                        Log.d("Country ", countryName);
                        Log.d("Country Code ", countryCode);

                        //Set the textview in news map fragment to display country code

                        //geting news
                        if (countryCode.equals(null) || countryCode.equals("null")) {
                            getList("hk");
                        } else {
                            getList(countryCode);
                        }

                        break;
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }




        }
    }
}