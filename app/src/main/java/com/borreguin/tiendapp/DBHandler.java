package com.borreguin.tiendapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.borreguin.tiendapp.Class.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto on 8/29/2017.
 * com.borreguin.tiendapp
 */

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DB_clientsInfo";

    // Contacts table name
    private static final String TABLE_CLIENTS = "tb_clients";

    // Clients Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DSCRP = "description";
    private static final String KEY_ToPAY = "toPay";
    private static final String KEY_ToRELY = "toRely";


    // Id for getting values in string constructor
    private static final int Idx_id_0 = 0, Idx_name_1 = 1, Idx_dscrp_2 = 2,
            Idx_toPay_3 = 3, Idx_toRely_4 =4;

    // Constructor
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CLIENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DSCRP + " TEXT,"
                + KEY_ToPAY + " FLOAT,"
                + KEY_ToRELY + " INTEGER"
                + ")";

        /*                  TYPE        Primary     Description
            ID:             Integer     yes         ID del cliente
            name:           text                    Nombre del cliente
            description:    text                    descripción del cliente
            toPay      :    Float                   Deuda del cliente
            toRely     :    Integer                 Se puede fiar al cliente? ( 0 -> false)
         */

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new client
    public void addClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, client.getName()); // Client Name
        values.put(KEY_DSCRP, client.getDescription()); // Client Description
        values.put(KEY_ToPAY, client.getToPay()); // to Pay
        values.put(KEY_ToRELY, client.getToRely_int()); // to Give products

        // Inserting Row
        db.insert(TABLE_CLIENTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one client
    public Client getClient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLIENTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_DSCRP, KEY_ToPAY, KEY_ToRELY},
                        KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Client contact = new Client(
                Integer.parseInt(cursor.getString(Idx_id_0)),
                cursor.getString(Idx_name_1),
                cursor.getString(Idx_dscrp_2),
                Float.parseFloat(cursor.getString(Idx_toPay_3)),
                Integer.parseInt(cursor.getString(Idx_toRely_4)));

        cursor.close();
        // return client
        return contact;
    }

    // Getting All Clients
    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<Client>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(Integer.parseInt(cursor.getString(Idx_id_0)));
                client.setName(cursor.getString(Idx_name_1));
                client.setDescription(cursor.getString(Idx_dscrp_2));
                client.setToPay(Float.parseFloat(cursor.getString(Idx_toPay_3)));
                client.setToRely(Integer.parseInt(cursor.getString(Idx_toRely_4)));

                // Adding contact to list
                clientList.add(client);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return clientList;
    }

    // Getting clients Count
    public int getClientsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a client
    public int updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, client.getName());
        values.put(KEY_DSCRP, client.getDescription());
        values.put(KEY_ToPAY, client.getToPay());
        values.put(KEY_ToRELY, client.getToRely_int());

        // updating row
        return db.update(TABLE_CLIENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(client.getId())});
    }

    // Deleting a client
    public void deleteClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(client.getId()) });
        db.close();
    }
    
}
