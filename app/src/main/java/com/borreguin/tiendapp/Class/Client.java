package com.borreguin.tiendapp.Class;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Roberto on 8/29/2017.
 * com.borreguin.tiendapp.Class
 */

public class Client {
    private int id;
    private String name;
    private String description;
    private float toPay;
    private boolean toRely;
    private int account;
    private Date date_update;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public Client(){
        this.id = 1;
        this.name = "";
        this.description = "";
        this.toPay = 0;
        this.toRely = true;
        this.account =0;
        this.date_update = Calendar.getInstance().getTime();
    }

    public Client(int id, String name, String description, float toPay)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.toPay = toPay;
        this.toRely = true;
        this.account = 0;
        this.date_update = Calendar.getInstance().getTime();
    }
    public Client(String name,String description)
    {
        this.name=name;
        this.description=description;
        this.toPay = 0;
        this.toRely = true;
        this.account = 0;
        this.date_update = Calendar.getInstance().getTime();
    }

    public Client(int i, String name, String description, float toPay,
                  int toRely, int account, String date_update) throws ParseException {
        this.id = i;
        this.name=name;
        this.description=description;
        this.toPay= toPay;
        this.toRely = (toRely>=1);
        this.account = account;
        this.date_update = formatter.parse(date_update);
    }

    public Client(String name, String description, float toPay)
    {
        this.name=name;
        this.description = description;
        this.toPay = toPay;
        this.toRely = true;
        this.account = 0;
        this.date_update = Calendar.getInstance().getTime();
    }


    public boolean isToRely() {
        return toRely;
    }


    public int getToRely_int(){
        if(this.toRely){

            return 1;
        }else {
            return 0;
        }
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }


    public String getDate_update_string() {
        return formatter.format(date_update);
    }

    public void setDate_update(Date date_update) {
        this.date_update = date_update;
    }

    public void setDate_update(String str_date_update) {
        try {
            this.date_update = formatter.parse(str_date_update);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setToRely(boolean toRely) {
        this.toRely = toRely;
    }
    public void setToRely(int toRely) {
        this.toRely = (toRely >=1);
    }


    public float getToPay() {
        return toPay;
    }

    public void setToPay(float toPay) {
        this.toPay = toPay;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {

        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Boolean isEmpty(){
        return this.getName().isEmpty();
    }
}
