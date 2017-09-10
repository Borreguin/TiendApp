package com.borreguin.tiendapp.DB_Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.borreguin.tiendapp.Class.Account;
import com.borreguin.tiendapp.Class.Client;
import com.borreguin.tiendapp.Class.Global;
import com.borreguin.tiendapp.Class.Note;

import java.text.ParseException;

/**
 * Created by Roberto on 9/9/2017.
 * com.borreguin.tiendapp.DB_Handlers
 */

public class DBHandler_Accounts extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "DB_infoAccounts";

    // Table names
    private static final String TABLE_NOTES = "tbl_notes";
    private static final String TABLE_METADATA = "tbl_metadata";

    // Clients Table Columns names
    private static final String KEY_ID_NOTE = "id_note";
    private static final String KEY_CLIENT_NAME = "client_name";
    private static final String KEY_NOTE_DESCRIPTION = "note_description";
    private static final String KEY_ACCOUNT = "no_account";
    private static final String KEY_VALUE_DEFT = "debt_value";
    private static final String KEY_DATE_UPDATE = "dateUpdate";

    // Metadata values:
    private static final String KEY_ID = "id";
    private static final String KEY_KEY = "key";
    private static final String KEY_VALUE = "value";

    // Id for getting values in string constructor
    private static final int Idx_id_0 = 0, Idx_name_1 = 1, Idx_dscrp_2 = 2,
            Idx_no_account_3 = 3, Idx_value_deft_4 = 4,Idx_date_update_5 =5;



    // Constructor
    public DBHandler_Accounts(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TABLE OF CLIENTS
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID_NOTE +             " INTEGER PRIMARY KEY,"
                + KEY_CLIENT_NAME +         " TEXT,"
                + KEY_NOTE_DESCRIPTION +    " TEXT,"
                + KEY_ACCOUNT +             " INTEGER,"
                + KEY_VALUE_DEFT +          " REAL,"
                + KEY_DATE_UPDATE +         " TEXT"
                + ")";

        /*                  TYPE        Primary     Description
            ID_note:        Integer     yes         ID de la nota de compra
            client_name:    text                    Nombre del cliente
            description:    text                    descripción de la nota de venta
            account    :    Integer                 cuenta actual valida
            date_update:    Text                    Fecha de actualización/creación
         */

        // For key and value table, KEY_ID is unique
        // TABLE METADATA_CLIENTS
        String CREATE_METADATA = "CREATE TABLE " + TABLE_METADATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_KEY + " STRING, "
                + KEY_VALUE + " STRING "
                + ")";
        /*                  TYPE        Primary     Description
            ID:             text     yes         ID del member
            detail:         text                    detail document
         */

        try {
            db.execSQL(CREATE_METADATA);
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            db.execSQL(CREATE_CONTACTS_TABLE);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        // Creating tables again
        onCreate(db);
    }

    // adding notes to the database
    public void addNote(Note note, Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_NAME, client.getName());                    // Client Name
        values.put(KEY_NOTE_DESCRIPTION, note.getDescription());        // Note Description
        values.put(KEY_ACCOUNT, client.getAccount());                   // Valid account
        values.put(KEY_VALUE_DEFT, note.getValue());                         // Get value of this note
        values.put(KEY_DATE_UPDATE, client.getDate_update_string());    // Save date when it was created
        // Inserting Row
        db.insert(TABLE_NOTES, null, values);
        // Closing database connection
        db.close();
    }

    // Getting one client using the name of the client
    public Account getClientAccount(Client client) {
        SQLiteDatabase db = this.getReadableDatabase();
        Account clientAccount = new Account();

        Cursor cursor = db.query(TABLE_NOTES, new String[]{KEY_ID_NOTE,
                        KEY_CLIENT_NAME, KEY_NOTE_DESCRIPTION, KEY_ACCOUNT,
                        KEY_VALUE_DEFT, KEY_DATE_UPDATE},
                KEY_ACCOUNT + "=?" + " AND " + KEY_CLIENT_NAME + "=?",
                new String[]{ String.valueOf(client.getAccount()), client.getName()},
                null, null, null, null);

        if (cursor == null || cursor.getCount() == 0)
            return clientAccount;

        try {

            if (cursor.moveToFirst()) {
                do {
                    clientAccount.addNote(new Note(
                            Integer.parseInt(cursor.getString(Idx_id_0)),
                            Float.parseFloat(cursor.getString(Idx_value_deft_4)),
                            cursor.getString(Idx_dscrp_2),
                            cursor.getString(Idx_date_update_5)
                            )
                    );
                } while (cursor.moveToNext());
            }
            clientAccount.setIdAccount(client.getAccount());
            cursor.close();
            return clientAccount;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        cursor.close();
        return clientAccount;
    }

    public void deleteAccounts(Client client){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.delete(TABLE_NOTES, KEY_CLIENT_NAME + " = ?",
                    new String[] { client.getName() });
            db.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
