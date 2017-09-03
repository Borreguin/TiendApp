package com.borreguin.tiendapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.borreguin.tiendapp.Class.Client;
import com.borreguin.tiendapp.Class.Global;
import com.borreguin.tiendapp.Utilities.ChildRow;
import com.borreguin.tiendapp.Utilities.MyExpandableListAdapter;
import com.borreguin.tiendapp.Utilities.ParentRow;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;

public class Act_SearchClient extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SearchView.OnClickListener{

    // Search list:
    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;
    // Manage of clients
    private DBHandler db = new DBHandler(this);

    //Data of clients
    private List<Client> clients;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_search_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NextPage = new Intent(Act_SearchClient.this, Act_NewClient.class);
                startActivity(NextPage);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // *****************************************
        // Search clients:
        searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();


        // The app will crash if display list is not called here
        displayList();

        // This expands the List of contents
        expandAll();

        // This create buttons
        super.onCreate(savedInstanceState);
    }

    private void loadData(){
        // Structure for search list
        ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
        ParentRow parentRow = null;

        // Data
        clients = db.getAllClients();
        clients.remove(0);
        // Adding members
        for(Client client : clients){
            childRows.add(new ChildRow(R.mipmap.generic_icon, client.getName()));
        }
        parentRow = new ParentRow("First Group",childRows);
        parentList.add(parentRow);

        //
        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow(R.mipmap.generic_icon, "icon 5"));
        childRows.add(new ChildRow(R.mipmap.generic_icon, "icon 6"));
        parentRow = new ParentRow("Second Group",childRows);
        parentList.add(parentRow);

    }

    private void expandAll(){
        int count = listAdapter.getGroupCount();
        for (int i =0; i <count; i++){
            myList.expandGroup(i);
        }
    }

    private void displayList(){
        loadData();
        listAdapter = new MyExpandableListAdapter(Act_SearchClient.this, parentList);
        myList = (ExpandableListView) findViewById(R.id.expListView);
        myList.setAdapter(listAdapter);
    }

   /* private void gotoDeleteClient(){
        Intent NextPage = new Intent(Act_SearchClient.this, Act_SearchClient.class);
        startActivity(NextPage);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo
                (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        //searchView.setOnCloseListener(this);
        searchView.requestFocus();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }



}
