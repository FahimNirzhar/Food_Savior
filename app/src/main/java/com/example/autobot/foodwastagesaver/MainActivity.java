package com.example.autobot.foodwastagesaver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    Button SignIn;
    TextView SignUp;
    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    EditText etemail, etPass;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        //Firebase.setAndroidContext(this);
        Auth = FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        SignIn = (Button)findViewById(R.id.button);
        SignUp =(TextView)findViewById(R.id.signup);
        etemail = (EditText)findViewById(R.id.editText3);
        etPass =(EditText)findViewById(R.id.editText2);
        authStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null) {
                    Intent SuccessIntent = new Intent(MainActivity.this, HomeLayout.class);
                    startActivity(SuccessIntent);
                }
            }
        };

            if(!isConnected(MainActivity.this))
                buildDialog(MainActivity.this).show();

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               StartSignIn();
            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onPause() {
        firebaseAuth.removeAuthStateListener(authStateListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void StartSignIn()
    {
        String uname = etemail.getText().toString();
        String pass = etPass.getText().toString();
        if(TextUtils.isEmpty(uname) || TextUtils.isEmpty(pass) )
        {
            Toast.makeText(MainActivity.this, "Fields are empty",Toast.LENGTH_LONG).show();
        }else
        {
            Auth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser firebaseUser = Auth.getCurrentUser();

                    if(task.isSuccessful())
                    {
                        Intent SuccessIntent = new Intent(MainActivity.this, HomeLayout.class);
                        startActivity(SuccessIntent);



                    }
                    else
                    {
                        // Toast.makeText(MainActivity.this, "Signed in Successfully", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Email Password mismatch",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) { // connected to the internet
           return true;
        } else {
            // not connected to the internet
            return false;
        }
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }


    public void contactus(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "foodsavior@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Food Savior");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, "Choose Your Email App"));

    }
}
