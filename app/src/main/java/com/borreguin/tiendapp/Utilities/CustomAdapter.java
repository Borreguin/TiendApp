package com.borreguin.tiendapp.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.borreguin.tiendapp.Class.Client;
import com.borreguin.tiendapp.R;

import java.util.List;

/**
 * Created by Roberto on 8/30/2017.
 * com.borreguin.tiendapp.Utilities
 */

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private List<Client> clientList;

    public CustomAdapter(Context mContext, List<Client> clientList){
        this.mContext = mContext;
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row,null);

        TextView txtClient = (TextView)view.findViewById(R.id.txtClient);
        txtClient.setText(clientList.get(position).getName());

        return view;
    }
}
