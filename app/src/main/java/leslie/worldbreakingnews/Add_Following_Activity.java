package leslie.worldbreakingnews;

/**
 * Created by Leslie on 5/21/2021.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Add_Following_Activity extends AppCompatActivity {
    private TextView tx1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private CheckBox chb[] = new CheckBox[16];
    private String following_data="";
    private int chb_id[] = {R.id.chb1,R.id.chb2,R.id.chb3,R.id.chb4,R.id.chb5,R.id.chb6,R.id.chb7,R.id.chb8,R.id.chb9,R.id.chb10,R.id.chb11,R.id.chb12,R.id.chb13,R.id.chb14,R.id.chb15,R.id.chb16,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__following_);
        //setup toolbar and anim
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Topic");
        //setup database
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://cem-android-3c4e5-default-rtdb.asia-southeast1.firebasedatabase.app");
        tx1 = (TextView) findViewById(R.id.tx1);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/OldNewspaperTypes.ttf");
        tx1.setTypeface(custom_font);
        findid();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                String selected_data = userData.getFollowing_msg();

                for(int i=0;i<chb.length;i++) {
                    char selected_char = selected_data.charAt(i);
                    if(selected_char=='1'){chb[i].setChecked(true);}
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Add_Following_Activity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }

        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //update data
                CheckBoxToString();
                sendUserData();

            }
        });
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://cem-android-3c4e5-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserData userData = new UserData(following_data);
        myRef.setValue(userData);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
    private void findid(){
        SharedPreferences SystemInfo = getSharedPreferences("data", Context.MODE_PRIVATE);
        String data1 = SystemInfo.getString("font_size", "22");
        String data2 = SystemInfo.getString("font_Style","font1");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/"+data2+".ttf");
        for(int i=0; i<chb_id.length;i++)
        {
            chb[i]=(CheckBox) findViewById(chb_id[i]);
            chb[i].setTextSize(Integer.parseInt(data1));
            chb[i].setTypeface(custom_font);
        }


    }

    private void CheckBoxToString(){
        for(int i=0; i<chb_id.length;i++)
        {
            if(chb[i].isChecked()){following_data+=1;}else{following_data+=0;}
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        CheckBoxToString();
        sendUserData();
    }

}
