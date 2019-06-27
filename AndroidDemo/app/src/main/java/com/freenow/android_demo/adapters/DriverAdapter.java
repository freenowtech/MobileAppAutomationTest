package com.freenow.android_demo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.freenow.android_demo.models.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends ArrayAdapter<Driver> {

    private List<Driver> mDrivers;
    private final List<Driver> mDriversCompleteSet;
    private LayoutInflater mLayoutInflater;
    private OnDriverClickCallback mOnDriverClickCallback;
    private static final int sResource = android.R.layout.select_dialog_item;

    public DriverAdapter(@NonNull Context context, List<Driver> drivers, OnDriverClickCallback onDriverClickCallback) {
        super(context, sResource, drivers);
        mDrivers = new ArrayList<>(drivers);
        mDriversCompleteSet = new ArrayList<>(drivers);
        mLayoutInflater = LayoutInflater.from(context);
        mOnDriverClickCallback = onDriverClickCallback;
    }

    @Override
    public int getCount() {
        return mDrivers.size();
    }

    @Override
    public Driver getItem(int i) {
        return mDrivers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(sResource, parent, false);
        }
        Driver driver = mDrivers.get(position);
        convertView.setOnClickListener(new OnDriverClickListener(driver, mOnDriverClickCallback));
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(driver.getName());
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Driver) resultValue).getName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Driver> suggestions = new ArrayList<>();
                if (charSequence != null) {
                    for (Driver driver : mDriversCompleteSet) {
                        if (driver.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            suggestions.add(driver);
                        }
                    }
                }
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDrivers.clear();
                if (filterResults != null && filterResults.count > 0) {
                    for (Object result : (List<?>) filterResults.values) {
                        if (result instanceof Driver) {
                            mDrivers.add((Driver) result);
                        }
                    }
                    notifyDataSetChanged();
                } else {
                    mDrivers.addAll(mDriversCompleteSet);
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public class OnDriverClickListener implements OnClickListener {

        private Driver mDriver;
        private OnDriverClickCallback mOnDriverClickCallback;

        OnDriverClickListener(Driver driver, OnDriverClickCallback onDriverClickCallback) {
            mDriver = driver;
            mOnDriverClickCallback = onDriverClickCallback;
        }

        @Override
        public void onClick(View view) {
            mOnDriverClickCallback.execute(mDriver);
        }

    }

    public interface OnDriverClickCallback {

        void execute(Driver driver);

    }

}
