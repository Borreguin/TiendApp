package com.borreguin.tiendapp.Class;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.borreguin.tiendapp.Act_NewClient;
import com.borreguin.tiendapp.Act_SearchClient;

/**
 * Created by Roberto on 9/2/2017.
 * com.borreguin.tiendapp.Class
 */

// Here the general variables

public class Global {
    // NOTE: Test User or Temporal id=0
    public String prefix = "#TEMP_";
    public int id_temp = 1;

    public void goto_SearchClient(View v){
        Context context = v.getContext();
        Intent NextPage = new Intent(context, Act_SearchClient.class);
        context.startActivity(NextPage);
    }
    public void goto_CreateClient(View v){
        Context context = v.getContext();
        Intent NextPage = new Intent(context, Act_NewClient.class);
        context.startActivity(NextPage);
    }
}
