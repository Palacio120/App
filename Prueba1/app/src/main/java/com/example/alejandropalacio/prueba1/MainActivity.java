package com.example.alejandropalacio.prueba1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button login;
    private Button registrsr;
    private ProgressDialog Progress;
    private EditText TextEmail;
    private EditText TextPass;
    Context context= this;


    @RequiresApi(api = 28)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //logeoInicial();

        SharedPreferences SharePrefs=getSharedPreferences("ArchivoLog",context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        registrsr=(Button) findViewById(R.id.Btn_Registro);
        login=(Button) findViewById(R.id.Entrar);
        Progress= new ProgressDialog(this);
        TextEmail= (EditText) findViewById(R.id.TxtMail);
        TextPass= (EditText) findViewById(R.id.TxtPass);




        registrsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this , Registro.class );
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogFirebase();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogFirebase();
            }
        });


    }

    private void logeoInicial(){
        SharedPreferences SharePrefs=getSharedPreferences("ArchivoLog",context.MODE_PRIVATE);
        String email= SharePrefs.getString("Usario","");
        String Pass= SharePrefs.getString("Pass","");


        mAuth.signInWithEmailAndPassword(email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent( MainActivity.this , Principal.class );
                            startActivity(intent);
                        } else {
                        }

                        Progress.dismiss();
                    }
                });
    }


    private void LogFirebase() {
        String email= TextEmail.getText().toString().trim();
        String Pass= TextPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"se nececita el email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Pass)){
            Toast.makeText(this,"se nececita la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        Progress.setMessage("Buscando...");
        Progress.show();


        mAuth.signInWithEmailAndPassword(email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this,"Bienvenido", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent( MainActivity.this , Principal.class );
                            startActivity(intent);

                        } else {

                                Toast.makeText(MainActivity.this,"No se encontro el usario y contraseña", Toast.LENGTH_LONG).show();

                        }

                        Progress.dismiss();
                    }
                });
    }


}
