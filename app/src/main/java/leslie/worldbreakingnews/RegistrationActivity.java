package leslie.worldbreakingnews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Leslie on 5/21/2021.
 */

public class RegistrationActivity extends AppCompatActivity {
    private EditText userName,userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private String email ,name,password;
    private Button btn_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                userPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                userEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if(validate())
                {
                    //upload to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            }
        });
        btn_finish.setOnClickListener(btn_finish_Lis);
    }

    private void setupUIViews(){
        userName = (EditText) findViewById(R.id.User_Name);
        userPassword = (EditText) findViewById(R.id.User_Password);
        userEmail = (EditText) findViewById(R.id.User_Email);
        regButton = (Button) findViewById(R.id.User_Register);
        btn_finish = (Button) findViewById(R.id.btn_finish);
    }
    private boolean validate(){
        boolean result = false;
        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email is not valid.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }
        return  result;
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData(){
        String following_data = "0000000000000000";
        String firebaseDB = "https://cem-android-3c4e5-default-rtdb.asia-southeast1.firebasedatabase.app/";
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(firebaseDB);
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserData userData = new UserData(following_data);
        myRef.setValue(userData);
    }
    private View.OnClickListener btn_finish_Lis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}

