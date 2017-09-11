package com.borreguin.tiendapp.Class;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.borreguin.tiendapp.Act_ClientDetails;
import com.borreguin.tiendapp.Act_DetailsNote;
import com.borreguin.tiendapp.Act_NewClient;
import com.borreguin.tiendapp.Act_SearchClient;
import com.borreguin.tiendapp.MainActivity;
import com.borreguin.tiendapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Roberto on 9/2/2017.
 * com.borreguin.tiendapp.Class
 */

// Here the general variables

public class Global {
    // NOTE: Test User or Temporal id=0
    public String prefix = "#TEMP_";
    public int id_temp = 1;
    public int number_of_accounts = 2;  //is the number of accounts that is able to manage per client
    Locale locale = new Locale( "es" , "ES" );
    public SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
    public SimpleDateFormat formatter2 = new SimpleDateFormat("EEE dd - MMM - yyyy", locale);

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
    public void goto_MainMenu(View v){
        Context context = v.getContext();
        Intent NextPage = new Intent(context, MainActivity.class);
        context.startActivity(NextPage);
    }
    public void goto_DetailsClient(View v){
        Context context = v.getContext();
        Intent NextPage = new Intent(context, Act_ClientDetails.class);
        context.startActivity(NextPage);
    }

    public void goto_DetailsNote(View v){
        Context context = v.getContext();
        Intent NextPage = new Intent(context, Act_DetailsNote.class);
        context.startActivity(NextPage);
    }

    public Boolean checkClient(List<Client> clients, String NameClient){

        Boolean unique = true;
        if(NameClient.isEmpty()){
            return false;
        }

        // Check if the user is already created
        String NameClient_ax = NameClient.replace(" ", "").toLowerCase();
        for(Client client : clients){
            String clients_ax = client.getName().replace(" ", "").toLowerCase();
            if(clients_ax.equals(NameClient_ax)){
                unique = false;
            }
        }
        return unique;
    }

    public float parseStringToFloat(String toFloat){
        float ans = 0.0f;
        try{
            ans = Float.parseFloat(toFloat);
            return ans;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ans;
    }

    public String getlines(int numberLines){
        String ans = "";

        for(int i =0; i<numberLines; i++){
            ans += "\n ";
        }
        return ans;
    }
}
