package com.mytaxi.android_demo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mytaxi.android_demo.models.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends ArrayAdapter {
    private Context mContext;

    public DriverAdapter(@NonNull Context context, @NonNull List drivers) {
        super(context, android.R.layout.select_dialog_item, getDrvierNamesList(drivers));
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                Toast.makeText(mContext, "Calling: " + name, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private static ArrayList<String> getDrvierNamesList(List<Driver> drivers) {
        ArrayList<String> driverNames = new ArrayList<>();
        for (Driver driver : drivers) {
            driverNames.add(driver.getName());
        }
        return driverNames;
    }
}
