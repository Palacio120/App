package com.example.alejandropalacio.prueba1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText TextEmail;
    private  EditText TextPass;
    private Button Registrar;

    private ProgressDialog Progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        TextEmail= (EditText) findViewById(R.id.TxtMail);
        TextPass= (EditText) findViewById(R.id.TxtPass);
        Registrar= (Button) findViewById(R.id.Entrar);
        Progress= new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUser();
            }
        });
    }

    private void CreateUser() {
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

        Progress.setMessage("Realizando registro en linea...");
        Progress.show();


        mAuth.createUserWithEmailAndPassword(email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Registro.this,"Se ha registrado", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent( Registro.this , Principal.class );
                            startActivity(intent);

                        } else {
                           if (task.getException() instanceof FirebaseAuthUserCollisionException){// en caso de usario existente
                               Toast.makeText(Registro.this,"El usario ya existe", Toast.LENGTH_LONG).show();
                           }else{
                               Toast.makeText(Registro.this,"No se ha podido registrar", Toast.LENGTH_LONG).show();
                           }

                        }

                        Progress.dismiss();
                    }
                });
    }





}
