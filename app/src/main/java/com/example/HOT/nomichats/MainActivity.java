package com.example.HOT.nomichats;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.HOT.nomichats.Fragments.CustomFragmentPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    ViewPager mviewPager;
    CustomFragmentPagerAdapter mFragmentPagerAdapter;
    TabLayout mtabLayout;
    DatabaseReference mDatabaseReference;
    DatabaseReference regDB;

    //Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();

        mviewPager=(ViewPager)findViewById(R.id.viewPager);

        // Adapter for tabs (Custom)
        mFragmentPagerAdapter=new CustomFragmentPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(mFragmentPagerAdapter);

        //---SETTING TAB LAYOUT WITH VIEW PAGER

        mtabLayout=(TabLayout)findViewById(R.id.tabLayout);
        mtabLayout.setupWithViewPager(mviewPager);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        regDB = FirebaseDatabase.getInstance().getReference().child("users");


//        manRegis("ahsan", "i170186@nu.edu.pk", "killwhatsapp");
    }

    //---IF USER IS NULL , THEN GOTO LOGIN PAGE----
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mauth.getCurrentUser();
//        user = null;
        if(user == null){
            startLoginActivity();
        }
        else{
            //---IF LOGIN , ADD ONLINE VALUE TO TRUE---
            mDatabaseReference.child(user.getUid()).child("online").setValue("true");
        }
    }

    // Options Menu Initialized
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.set_1){
            Log.d("DEBUG", "Start Settings 1");
        }
        if(item.getItemId()==R.id.set_2){
            Log.d("DEBUG", "Start Settings 2");
        }
        if(item.getItemId()==R.id.logout){
            mDatabaseReference.child(mauth.getCurrentUser().getUid()).child("online").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        FirebaseAuth.getInstance().signOut();
                        startLoginActivity();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Try again..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return true;
    }

    // DRY Code
    private void startLoginActivity(){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Manual Registration Only temporary
    private void manRegis(final String username, String email, String password){

        Log.d("DEBUG", "Manual Registration");
        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // Successful User Registration
                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    final String uid=current_user.getUid();
                    String token_id = FirebaseInstanceId.getInstance().getToken();
                    Map userMap=new HashMap();
                    userMap.put("device_token",token_id);
                    userMap.put("name",username);
                    userMap.put("status","Hello ");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");
                    userMap.put("online","true");

                    Log.d("DEBUG", userMap.toString());

                    regDB.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){
                                Log.d("DEBUG", "Registration Successful.");
                            }
                            else{
                                Log.d("DEBUG", "Error in task1.");
                            }

                        }
                    });


                }
                else{
                    Log.d("DEBUG", "Error in registering user.");
                }
            }
        });
    }
}
