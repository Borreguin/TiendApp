package com.borreguin.tiendapp.Utilities;

/**
 * Created by Roberto on 8/31/2017.
 * com.borreguin.tiendapp.Utilities
 */

public class ChildRow {
    private int icon;
    private String text;

    public ChildRow(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
