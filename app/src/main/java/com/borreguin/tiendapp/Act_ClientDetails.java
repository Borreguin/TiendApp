package com.borreguin.tiendapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.borreguin.tiendapp.Class.Account;
import com.borreguin.tiendapp.Class.Client;
import com.borreguin.tiendapp.Class.Global;
import com.borreguin.tiendapp.Class.Note;
import com.borreguin.tiendapp.DB_Handlers.DBHandler_Accounts;
import com.borreguin.tiendapp.DB_Handlers.DBHandler_Clients;
import com.borreguin.tiendapp.Utilities.NoteAdapter;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Act_ClientDetails extends AppCompatActivity {

    private Account clientAccount = new Account();

    // variables for data:
    private DBHandler_Clients db_client = new DBHandler_Clients(this);
    private DBHandler_Accounts db_account = new DBHandler_Accounts(this);

    //local variables:
    GridView grid;
    TextView clientName, description, debtTotal;
    EditText putDebt;
    Button btn_plusNote, btn_minusNote, btn_addDetails;
    View view;
    Client client;
    Intent NextPage;

    // global variables and functions
    Global global = new Global();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_act_client_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // connecting members of activity layout
        clientName =(TextView) findViewById(R.id.clientName);
        description = (TextView) findViewById(R.id.description);
        debtTotal = (TextView)findViewById(R.id.txt_total);
        putDebt = (EditText) findViewById(R.id.txt_enterDeft);
        btn_plusNote = (Button) findViewById(R.id.btn_addDeft);
        btn_minusNote = (Button) findViewById(R.id.btn_lessDeft);
        btn_addDetails = (Button) findViewById(R.id.btn_addDetails);
        grid=(GridView)findViewById(R.id.grid_item_values);

        // setting functions for each button
        btn_plusNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusNoteToClient();
            }
        });
        btn_minusNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusNoteToClient();
            }
        });

        // loading data of the client:
        if(LoadClient()) //if the user is not loaded then scape
        {
            update_grid();
            btn_addDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoNoteDetails(v, client.getName(), putDebt.getText().toString());
                }
            });
        }
        super.onCreate(savedInstanceState);

        // test creates some notes:
        // db_account.addNote(new Note(0,12.3f,"Test 1"), client);
        // db_account.addNote(new Note(1,2.3f,"Test 2"), client);
    }

    private Boolean LoadClient(){

        // loading data from the last view
        try {
            Bundle bundle = getIntent().getExtras();
            client = db_client.getClient(bundle.getString("NameClient"));
            clientName.setText(client.getName());
            description.setText(client.getDescription());
            debtTotal.setText(String.format("%.2f",clientAccount.getTotal()));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void plusNoteToClient(){
        float value = abs(global.parseStringToFloat(putDebt.getText().toString()));
        db_account.addNote(new Note(value), client);
        update_grid();
    }
    private void minusNoteToClient(){
        float value = -abs(global.parseStringToFloat(putDebt.getText().toString()));
        db_account.addNote(new Note(value), client);
        update_grid();
    }

    private void update_grid(){
        clientAccount = db_account.getClientAccount(client);
        NoteAdapter adapter = new NoteAdapter(Act_ClientDetails.this, clientAccount.getNotes());
        grid.setAdapter(adapter);
        debtTotal.setText(String.format("%.2f",clientAccount.getTotal()));

        // ----- updating debt of the user: ------
        client.setToPay(clientAccount.getTotal());
        db_client.updateClient(client);
        // ---------------------------------------
    }

    public void gotoNoteDetails(View v, String childText, String putDeft){

        // Pass information to the next view:
        NextPage = new Intent(Act_ClientDetails.this, Act_DetailsNote.class);
        //Create the bundle
        Bundle bundle = new Bundle();
        //Adding data to bundle
        bundle.putString("NameClient",childText);
        bundle.putString("putDeft", putDeft);
        //Add the bundle to the intent
        NextPage.putExtras(bundle);
        //Fire that second activity
        startActivity(NextPage);
    }


}
