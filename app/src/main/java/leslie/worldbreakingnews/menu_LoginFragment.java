package leslie.worldbreakingnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Leslie on 5/21/2021.
 */

public class menu_LoginFragment extends Fragment {
    private Button btn_register, btn_Login;
    private EditText name,password;
    private FirebaseAuth firebaseAuth;
    private TextView tx1;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_menu__login, container, false);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_Login = (Button) view.findViewById(R.id.btnLogin);
        tx1 = (TextView) view.findViewById(R.id.tx1);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OldNewspaperTypes.ttf");
        tx1.setTypeface(custom_font);
        name = (EditText) view.findViewById(R.id.name);
        password = (EditText) view.findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null)
        {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            Fragment frag2;
            frag2 = new menu_HotNewsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, frag2); // replace a Fragment with Frame Layout
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Hot News");
            transaction.commit();
            progressDialog.dismiss();
        }

        btn_register.setOnClickListener(register_Lis);
        btn_Login.setOnClickListener(login_Lis);
        return view;
    }
    private View.OnClickListener login_Lis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name.onEditorAction(EditorInfo.IME_ACTION_DONE);
            password.onEditorAction(EditorInfo.IME_ACTION_DONE);
            validate(name.getText().toString(),password.getText().toString());
        }
    };
    private View.OnClickListener register_Lis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(),RegistrationActivity.class));
        }
    };
    private void validate(String userName,String userPassword){
        progressDialog.setMessage("Loading");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    checkEmailVerifcation();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkEmailVerifcation() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            Fragment frag2;
            frag2 = new menu_HotNewsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, frag2); // replace a Fragment with Frame Layout
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Hot News");
            transaction.commit();
        }else{
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
