package com.android.first1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    Button signin;
    private int myrequestcode=10000; //only integer
    FirebaseAuth auth;
  GoogleSignInClient options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin=(Button)findViewById(R.id.btn1);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin=options.getSignInIntent();
                startActivityForResult(signin,myrequestcode);
            }
        });
        auth=FirebaseAuth.getInstance();//firebase ko extension as settext
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
       //default ho as google takes the token for verification for email
        options= GoogleSignIn.getClient(this, gso);




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser =auth.getCurrentUser();
        if(currentuser!=null){
            Intent profile=new Intent(MainActivity.this,Newactivity.class);
            startActivity(profile);
        }
    }

    @Override
    public void onActivityResult(int requestcode,int resultcode, Intent data){
        super.onActivityResult(requestcode,resultcode,data);

        if(myrequestcode == myrequestcode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                FirebseSignin(account);

            }
            catch (ApiException e){
                Toast.makeText(this,"error ",Toast.LENGTH_LONG).show();
            }
        }

    }




    public void FirebseSignin(GoogleSignInAccount account){
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)//defaullt of chrome as password or email
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user =auth.getCurrentUser();

                            Intent profile=new Intent(MainActivity.this,Newactivity.class);
                            startActivity(profile);
                            Toast.makeText(MainActivity.this,"successful",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
