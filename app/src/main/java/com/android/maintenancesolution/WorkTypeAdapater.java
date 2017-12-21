package com.android.maintenancesolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkTypeAdapater extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mDataSource;
    private CustomerRequestForm customerRequestForm;
    private WorkTypeAdapater workTypeAdapater;

    public WorkTypeAdapater(Context context, ArrayList<String> items, CustomerRequestForm customerRequestForm) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.customerRequestForm = customerRequestForm;

    }


    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item, viewGroup, false);

        // Get title element
        TextView titleTextView = rowView.findViewById(R.id.textViewJob);
        ImageView imageViewClose = rowView.findViewById(R.id.ivClose);
        // 1
        //WorkType workType = (WorkType) getItem(i);
        // 2
        titleTextView.setText(mDataSource.get(position));
        workTypeAdapater = customerRequestForm.getWorkTypeAdapater();

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.remove(position);
                workTypeAdapater.notifyDataSetChanged();

            }
        });


        return rowView;
    }


}
