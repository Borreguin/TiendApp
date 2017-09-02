package com.borreguin.tiendapp.Class;

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

    public Client(int id, String name, String description, float toPay)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.toPay = toPay;
        this.toRely = true;
    }
    public Client(int id,String name,String description)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.toPay = 0;
        this.toRely = true;
    }

    public Client()
    {
        this.name="";
        this.description="";
        this.toPay= 0;
        this.toRely = true;
    }

    public Client(String name, String description, float toPay)
    {
        this.name=name;
        this.description = description;
        this.toPay = toPay;
        this.toRely = true;
    }

    public Client(int id, String name, String description, float toPay, boolean toRely)
    {
        this.id = id;
        this.name=name;
        this.description = description;
        this.toPay = toPay;
        this.toRely = toRely;
    }

    public Client(int id, String name, String description, float toPay, int toRely)
    {
        this.id = id;
        this.name=name;
        this.description = description;
        this.toPay = toPay;
        this.toRely = (toRely>=1);
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
