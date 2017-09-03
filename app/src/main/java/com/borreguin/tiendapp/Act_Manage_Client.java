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

import java.util.List;

public class Act_Manage_Client extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnDeleteClient;
    private Button btnEditClient;
    private Button btnNewClient;


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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_manage_client);
        btnDeleteClient = (Button)findViewById(R.id.btnDeleteClient);
        btnEditClient = (Button)findViewById(R.id.btnEditClient);
        btnNewClient = (Button)findViewById(R.id.btnNewClient);


        /*mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/

       /*
        DBHandler db = new DBHandler(this);

        // Inserting Client/Rows
        Log.d("Insert: ", "Inserting ..");
        db.addClient(new Client("User x", "description x"));
        db.addClient(new Client("User 2", "description 2"));
        db.addClient(new Client("User 3", "description 3"));
        db.addClient(new Client("User 4", "description 4"));

        // Reading all clients
        Log.d("Reading: ", "Reading all clients..");
        List<Client> clients = db.getAllClients();

        for (Client client : clients) {
            String log = "Id: " + client.getId()
                    + " ,Name: " + client.getName()
                    + " ,Description: " + client.getDescription()
                    + " ,toPay: " + client.getToPay()
                    + " ,toRely: " + client.isToRely();
            // Writing clients  to log
            Log.d("Client: : ", log);
        }*/
    }

    // Buttons for navigation:

    /*public void gotoNewClient(View view){
       // Intent NextPage = new Intent(Act_Manage_Client.this, Act_NewClient.class);
       // startActivity(NextPage);
    };
    public void gotoSearchClient(View view){
        //Intent NextPage = new Intent(Act_Manage_Client.this, Act_SearchClient.class);
        //startActivity(NextPage);
    };
    public void gotoEditClient(){

    }
    public void gotoDeleteClient(){

    }*/


}
