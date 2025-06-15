package com.s22010695.chamuditha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //create objects
    DatabaseHelper db;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //create sqlite database using database helper class
        db = new DatabaseHelper(this);

        //get username and password using id
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    //insert data method
    public void insertData(View view) {
        //convert user inputs to string
        String user = username.getText().toString();
        String pass = password.getText().toString();

        //empty input validation
        if(user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean response = db.insertData(user, pass);

        //check response and display toast
        if(response){
            Toast.makeText(this, "User saved successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not saved! Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    //navigate to google map activity using intent
    public void navToGoogleMapActivity(View view) {
        Intent intent = new Intent(this, GoogleMapActivity.class);
        startActivity(intent);
    }
}