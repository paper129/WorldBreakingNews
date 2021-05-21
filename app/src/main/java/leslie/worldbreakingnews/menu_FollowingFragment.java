package leslie.worldbreakingnews;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leslie on 5/21/2021.
 */

public class menu_FollowingFragment extends android.support.v4.app.Fragment {
    private Button btn1;
    private String selected_data="";
    private ImageView img;
    private TextView tx1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String[ ] item1 ={"MingPao", "Google News", "Yahoo","Ettoday","HK01","SportSV","RTHK","Thinkhk","LTN","All","Business","Entertainment","Health","Science","Sports","Technology"};
    private int image1[] ={R.drawable.logo_mingpao, R.drawable.gnews, R.drawable.logo_yahoo, R.drawable.logo_ettoday, R.drawable.logo_hk01, R.drawable.sv, R.drawable.logo_rthk, R.drawable.logo_thinkhk, R.drawable.logo_ltn,R.drawable.logo_all,R.drawable.logo_business,R.drawable.logo_entertainment,R.drawable.logo_health,R.drawable.logo_science,R.drawable.logo_sports,R.drawable.logo_technology};
    private List<Map<String, Object>> mList;
    private ListView lv;
    private int[] intent_pos = new int[16];
    private int pos_pointer=0;
    private  boolean pause = false;
    public menu_FollowingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu__following, container, false);
        btn1 = (Button)view.findViewById(R.id.toolbarbtn);
        btn1.setOnClickListener(btn1_Lis);
        img = (ImageView) view.findViewById(R.id.img) ;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://cem-android-3c4e5-default-rtdb.asia-southeast1.firebasedatabase.app");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        tx1 = (TextView) view.findViewById(R.id.tx1);
        lv= (ListView) view.findViewById(R.id.lv);
        if(user != null) {
            genList();
        }
        else
        {
            android.support.v4.app.Fragment frag2;
            frag2 = new menu_LoginFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, frag2); // replace a Fragment with Frame Layout
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Login");
            transaction.commit();
        }
        return view;
    }
    private View.OnClickListener btn1_Lis =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lv.setAdapter(null);
            pos_pointer=0;
            startActivity(new Intent(getActivity(),Add_Following_Activity.class));
        }
    };
    private ListView.OnItemClickListener listViewOnItemClick1 = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            int new_postion = intent_pos[position];
            Log.d("System Info",Integer.toString(new_postion));
            if(new_postion >=0 && new_postion<=9) {
                Intent it = new Intent(getActivity(), news_subtopic.class);
                Bundle bundle = new Bundle();
                bundle.putInt("page", 1);
                bundle.putInt("position", new_postion);
                bundle.putString("title", item1[new_postion]);
                it.putExtras(bundle);
                startActivity(it);
            }
            if(new_postion >9 && new_postion<=16) {
                Intent it = new Intent(getActivity(), news_subtopic.class);
                Bundle bundle = new Bundle();
                bundle.putInt("page", 2);
                bundle.putInt("position", new_postion-9);
                bundle.putString("title", item1[new_postion]);
                it.putExtras(bundle);
                startActivity(it);
            }


        }
    };
    @Override
    public void onResume() {
        super.onResume();
        if(pause)
        {
            pause = true;
            pos_pointer=0;
            genList();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }
    private void genList()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                selected_data = userData.getFollowing_msg();
                mList = new ArrayList<Map<String,Object>>();
                for (int i = 0; i < item1.length; i++) {
                    Log.d("System Info",selected_data);
                    char selected_char = selected_data.charAt(i);
                    if(selected_char == '1') {
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("imgView", image1[i]); //data in key-value pair
                        item.put("txtView", item1[i]);
                        mList.add(item);
                        intent_pos[pos_pointer] = i;
                        pos_pointer++;
                    }
                }
                if(pos_pointer == 0)
                {
                    img.setVisibility(View.VISIBLE);
                    tx1.setVisibility(View.VISIBLE);
                }
                else
                {
                    img.setVisibility(View.GONE);
                    tx1.setVisibility(View.GONE);
                }
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), mList, R.layout.list_item2,
                        new String[] { "imgView", "txtView" },
                        new int[] { R.id.imgView ,R.id.txtView });

                lv.setAdapter(adapter);
                lv.setOnItemClickListener(listViewOnItemClick1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }



}
