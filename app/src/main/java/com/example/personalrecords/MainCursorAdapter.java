package com.example.personalrecords;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MainCursorAdapter extends CursorAdapter {
    public MainCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.items_accueil, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(R.id.label);
        TextView tvPriority = (TextView) view.findViewById(R.id.description);
        TextView btnFilled = (TextView) view.findViewById(R.id.fill);
        // Extract properties from cursor
        String label = cursor.getString(cursor.getColumnIndexOrThrow("label"))+" -";
        String type = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        int filled = cursor.getInt(cursor.getColumnIndexOrThrow("nbrSaisie"));
        // Populate fields with extracted properties
        tvBody.setText(label);
        tvPriority.setText(type);
        if(filled == 0){
            Drawable drawable = context.getDrawable(R.drawable.rounded_red_button);
            btnFilled.setBackground(drawable);
        }


    }
}
