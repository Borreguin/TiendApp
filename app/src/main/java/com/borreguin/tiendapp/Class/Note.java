package com.borreguin.tiendapp.Class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Roberto on 9/8/2017.
 * com.borreguin.tiendapp.Class
 */

public class Note {
    private int IdNote;
    private float value;
    private String description;
    private Date date_update;

    // global constant and functions
    Global global = new Global();

    public Note(float value){
        this.value = value;
        this.date_update = Calendar.getInstance().getTime();
    }

    public Note(int idNote, float value, String description) {
        this.IdNote = idNote;
        this.value = value;
        this.description = description;
        this.date_update = Calendar.getInstance().getTime();
    }

    public Note(int idNote, float value, String description, String date_update) {
        this.IdNote = idNote;
        this.value = value;
        this.description = description;
        try {
            this.date_update = global.formatter.parse(date_update);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getIdNote() {
        return IdNote;
    }

    public void setIdNote(int idNote) {
        IdNote = idNote;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
