package com.borreguin.tiendapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.borreguin.tiendapp.Class.Client;
import java.util.List;

public class Act_NewClient extends AppCompatActivity {

    private TextView mTextMessage;
    private CheckedTextView checkText;
    private List<Client> clients;
    private EditText clientName;
    private EditText description;
    private EditText clientDebt;
    private DBHandler db = new DBHandler(this);

    //private Client SelectedClient;
    Button btnNewClient;
    Button btnSearchClient;

    // NOTE: Test User or Temporal id=0
    final String prefix = "#TEMP_";
    final int id_temp = 1;

    // Validating a new client
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // here: the validation of EditText

            clients = db.getAllClients();
            db.close();
            checkClient(clients,clientName.getText().toString());
        }
    };
    // Navigation Menu
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
        setContentView(R.layout.activity_act_new_client);
        // PART OF THE MENU
        // ------------------------------------------------
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // -------------------------------------------------
        // Read all clients:

        clients = db.getAllClients();
        db.close();
        //**************************************************
        //**************************************************

        // Validate Text
        clientName = (EditText)findViewById(R.id.clientName);
        clientName.addTextChangedListener(textWatcher);
        description = (EditText)findViewById(R.id.clientDescription);
        clientDebt = (EditText)findViewById(R.id.editDebt);

        // Init checkedTextView
        checkText = (CheckedTextView)findViewById(R.id.checkText);
        checkText.setCheckMarkDrawable(R.drawable.empty);

        //***************CREATE BUTTONS****************
        btnNewClient = (Button)findViewById(R.id.btnNewClient);
        btnSearchClient = (Button)findViewById(R.id.btnSearchClient);
        //***************************************************

        isThereTemporalClient();

    }

    // check if the user is already created
    public void checkClient(List<Client> clients, String NameClient){

        Boolean Found = false;

        if(NameClient.isEmpty()){
            checkText.setCheckMarkDrawable(R.drawable.wrong);
            checkText.setChecked(false);
            checkText.setText(R.string.empty);
            return;
        }

        // Check if the user is already created
        String NameClient_ax = NameClient.replace(" ", "").toLowerCase();
        for(Client client : clients){
            String client_ax = client.getName().replace(" ", "").toLowerCase();

            if(client_ax.equals(NameClient_ax)){
                Found = true;
            }
        }

        if(Found){
            checkText.setCheckMarkDrawable(R.drawable.wrong);
            checkText.setChecked(false);
            checkText.setText(R.string.already_created);
        }else{
            checkText.setCheckMarkDrawable(R.drawable.checked);
            checkText.setChecked(true);
            checkText.setText(R.string.accepted);
        }
    }

    public void addNewClient(View view) {

        checkClient(clients,clientName.getText().toString());

        if(clientName.getText().length() == 0){
            Toast.makeText(Act_NewClient.this,
                    getString(R.string.NewClient) + " " +
                    getString(R.string.empty) , Toast.LENGTH_LONG).show();
            return;
        }
        if(!checkText.isChecked()){
            Toast.makeText(Act_NewClient.this,
                    getString(R.string.already_created) + ": " +
                    clientName.getText(), Toast.LENGTH_LONG).show();
        }else{

            db.addClient(new Client(clientName.getText().toString(),
                    description.getText().toString(),
                    Float.parseFloat(clientDebt.getText().toString())));
            // update the list before closing DB
                clients = db.getAllClients();
                checkClient(clients,clientName.getText().toString());

            db.close();

            Toast message = Toast.makeText(Act_NewClient.this,
                    getString(R.string.Successfully) + "\n" +
                    getString(R.string.newClientCreated) + "\n" +
                            clientName.getText() , Toast.LENGTH_LONG);
            message.setGravity(0,0,0);
            message.show();

            checkText.setCheckMarkDrawable(R.drawable.ok);
            checkText.setChecked(false);
            checkText.setText(getString(R.string.Successfully) + " "
                    + getString(R.string.NewClient) + clientName.getText());
            cleanTemporalClient();
        }
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isCheck",checkText.isChecked());
        outState.putString("checkText",checkText.getText().toString());
        outState.putString("clientName",clientName.getText().toString());
        outState.putString("description", description.getText().toString());
        outState.putString("debt", clientDebt.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkText.setChecked(savedInstanceState.getBoolean("isCheck"));
        checkText.setText(savedInstanceState.getString("checkText"));
        clientName.setText(savedInstanceState.getString("clientName"));
        description.setText(savedInstanceState.getString("description"));
        clientDebt.setText("debt");
    }*/

    // Buttons for navigation:
    public void gotoSearchClient(View view){
        Intent NextPage = new Intent(Act_NewClient.this, Act_SearchClient.class);
        startActivity(NextPage);
    };

    @Override
    protected void onStop() {
        saveTemporalClient();
        super.onStop();
    }

    // Create temporal client for testing or any other purpose
    public void saveTemporalClient(){

        Client temporalClient = new Client(id_temp,
                prefix + clientName.getText().toString(),
                description.getText().toString(),
                Float.parseFloat(clientDebt.getText().toString()));
        db.updateClient(temporalClient);
        db.close();
    }
    public void cleanTemporalClient(){

        Client temporalClient = new Client(id_temp, prefix + "", "", 0);
        db.updateClient(temporalClient);
        db.close();
    }

    public Boolean isThereTemporalClient() {
        Client tempClient = db.getClient(id_temp);
        if (tempClient.isEmpty()) {
            db.addClient(new Client(0, prefix, ""));
            return false;
        }

        if (tempClient.getName().equals(prefix)) {
            return false;
        }
        else {
            clientName.setText(tempClient.getName().replace(prefix, ""));
            description.setText(tempClient.getDescription());
            clientDebt.setText(String.valueOf(tempClient.getToPay()));
            return true;
        }
    }

    public void clean_clientName(View view){
        clientName.setText("");
    }
    public void clean_description(View view){
        description.setText("");
    }

}
