package cat.itb.redditapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;
import cat.itb.redditapp.helper.DatabaseHelper;

public class RegistroFragment extends Fragment {

    TextView logIn;
    MaterialButton continueButton;
    EditText editEmail;
    EditText editUsername;
    EditText editPassword;
    CheckBox editCheckBox;


    String username = "";
    String email = "";
    String password = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registro, container, false);

        logIn = v.findViewById(R.id.textViewLog);
        continueButton = v.findViewById(R.id.button6);
        editCheckBox = v.findViewById(R.id.checkBox);
        editEmail = v.findViewById(R.id.editTextTextEmail);
        editPassword = v.findViewById(R.id.editTextTextPassword2);
        editUsername = v.findViewById(R.id.editTextTextPersonName2);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && editCheckBox.isChecked()){
                    if (password.length() >= 6){
                        registerUser();
                    }else {
                        Toast.makeText(getContext(), "Password minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(), "Rellena los campos y acepta los términos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void registerUser(){
        DatabaseHelper.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("username", username);
                    map.put("email", email);
                    map.put("password", password);

                    String id = DatabaseHelper.mAuth.getCurrentUser().getUid();

                    DatabaseHelper.mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.remove(MainActivity.currentFragment);
                                MainActivity.loginShow();
                                transaction.commit();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}