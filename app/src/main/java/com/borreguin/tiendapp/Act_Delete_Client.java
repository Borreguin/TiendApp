package com.borreguin.tiendapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.borreguin.tiendapp.Class.Client;
import com.borreguin.tiendapp.Class.Global;
import com.borreguin.tiendapp.DB_Handlers.DBHandler_Clients;

public class Act_Delete_Client extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnDeleteClient;
    private Button btnSearchClient;
    private Button btnCancel;
    private TextView clientName;
    private TextView description;
    private TextView clientDebt;
    private DBHandler_Clients db = new DBHandler_Clients(this);

    private Client tempClient;

    // Global functions and variables
    Global global = new Global();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_act_delete_client);
        btnDeleteClient = (Button)findViewById(R.id.btnDeleteClient);
        btnSearchClient = (Button)findViewById(R.id.btnSearchClient);
        btnCancel = (Button)findViewById(R.id.btnMainActivity);

        clientName = (TextView)findViewById(R.id.clientName);
        description = (TextView)findViewById(R.id.clientDescription);
        clientDebt = (TextView)findViewById(R.id.debt);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle bundle = getIntent().getExtras();
        clientName.setText(bundle.getString("NameClient"));

        LoadClient(bundle.getString("NameClient"));

        // Assigning functions for buttons ----------------------------
        btnDeleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClient(v);
                global.goto_SearchClient(v);
            }
        });

        btnSearchClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.goto_SearchClient(v);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.goto_MainMenu(v);
            }
        });

        //--------------------------------------------------------------
        super.onCreate(savedInstanceState);
    }

    protected void LoadClient(String nameClient){
        tempClient = db.getClient(nameClient);
        clientName.setText(tempClient.getName());
        description.setText(tempClient.getDescription());
        clientDebt.setText(String.valueOf(tempClient.getToPay()));
    }

    protected void deleteClient(View v){
        db.deleteClient(tempClient);
        global.goto_SearchClient(v);
    }



}