package com.example.android.intellischeduler;

/**
 * Created by AKANKSH on 3/31/2018.
 */

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Locale;

public class NewActivity extends AppCompatActivity {

    private Button reset,note_it;
    private EditText name,priority,duration;
    Date currentTime=null;
    String ctime=null;
    final Context context = this;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference schedule_db;
    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }

    };

    public NewActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        reset=(Button)findViewById(R.id.Reset);
        note_it=(Button)findViewById(R.id.Note_it);

        name=(EditText)findViewById(R.id.Name);
        priority=(EditText)findViewById(R.id.Priority);
        duration=(EditText)findViewById(R.id.Duration);

        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                name.setText("");
                priority.setText("");
                duration.setText("");
            }
        });
        note_it.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                     currentTime = Calendar.getInstance().getTime();
                     ctime=currentTime.toString();
                }
                mAuth=FirebaseAuth.getInstance();
                FirebaseUser user=mAuth.getCurrentUser();
                String email_id=user.getEmail();
                Log.v("Test","Doing the firebase thing");
                String name1=name.getText().toString();
                String priority1=priority.getText().toString();
                String extent1=duration.getText().toString();
                ToDoList item=new ToDoList(name1,priority1,extent1,ctime);
                schedule_db = mFirebaseDatabase.getReference();
                String user_name[]=email_id.split("@");
                schedule_db.child(user_name[0]).push().setValue(item);
                Toast toast=Toast.makeText(getApplicationContext(),"Noted !!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
