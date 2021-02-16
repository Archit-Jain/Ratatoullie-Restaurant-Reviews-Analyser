package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class productAdapter extends ArrayAdapter<product> {
    public final Context context;
    public final ArrayList<product> values;

    public productAdapter(Context context, ArrayList<product> list) {
        super(context, R.layout.row_layout,list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout,parent,false);

        TextView textViewId = (TextView) rowView.findViewById(R.id.textViewId);
        TextView RestID = (TextView) rowView.findViewById(R.id.RestaurantID);
        TextView LocId = (TextView) rowView.findViewById(R.id.LocationID);
        TextView Cuisine = (TextView) rowView.findViewById(R.id.Cuisine);
        RestID.setText(values.get(position).getName());
        LocId.setText(values.get(position).getloc());
        Cuisine.setText(values.get(position).getCuisine());
        textViewId.setText(values.get(position).getStar());
        return rowView;
    }
}
