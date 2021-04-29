package cat.itb.redditapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.microedition.khronos.egl.EGLDisplay;

import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;
import cat.itb.redditapp.helper.DatabaseHelper;

public class LoginFragment extends Fragment {

    TextView signUp;

    EditText editEmail;
    EditText editPassword;

    MaterialButton continueButton;

    private String email= "";
    private String password = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        signUp = v.findViewById(R.id.textViewSignUp);
        editEmail = v.findViewById(R.id.editTextTextPersonName);
        editPassword = v.findViewById(R.id.editTextTextPassword);
        continueButton = v.findViewById(R.id.button3);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistroFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                MainActivity.currentFragment = fragment;
                transaction.commit();
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();
                
                if (!email.isEmpty() && !password.isEmpty()){
                    guardarPreferencias();
                    loginUser();
                }else {
                    Toast.makeText(getContext(), "Rellena los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cargarPreferencias();
        return v;
    }


    private void loginUser(){
        DatabaseHelper.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(MainActivity.currentFragment);
                    MainActivity.loginShow();
                    transaction.commit();
                }else {
                    Toast.makeText(getContext(), "No se pudo iniciar sesión, comprueba los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user = preferences.getString("user","");
        String pass = preferences.getString("pass","");

        editEmail.setText(user);
        editPassword.setText(pass);

    }

    private void guardarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", email);
        editor.putString("pass", password);

        editEmail.setText(email);
        editPassword.setText(password);

        editor.commit();
    }
}