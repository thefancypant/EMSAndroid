package com.maintenancesolution.ems.Views.ListActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Models.Order;

import java.util.List;

/**
 * Created by kalyan on 1/2/18.
 */

public class OrderListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Order> mDataSource;

    public OrderListAdapter(Context context, List<Order> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
// Get view for row item
        View rowView = mInflater.inflate(R.layout.order_item, viewGroup, false);

        // Get title element
        TextView textViewDate = rowView.findViewById(R.id.textViewDate);
        TextView textViewTime = rowView.findViewById(R.id.textViewTime);
        TextView textViewJob = rowView.findViewById(R.id.textViewJob);
        TextView textViewAddress = rowView.findViewById(R.id.textViewAddress);
        TextView textViewLocation = rowView.findViewById(R.id.locationTextView);
        TextView textViewCode = rowView.findViewById(R.id.textViewCode);
        TextView textViewDescription = rowView.findViewById(R.id.descriptionTextView);



        Order order = (Order) getItem(i);
        // 2
        if (order.getCode() != null) {
        textViewCode.setText(order.getCode());
        }
        if (order.getDateTime() != null) {

            textViewDate.setText(order.getDateTime().getDate());
        }
        if (order.getDateTime() != null) {

            textViewTime.setText(order.getDateTime().getTime());
        }

        if (order.getProject() != null) {

            textViewJob.setText(order.getProject().getName());
        }
        if (order.getNotes() != null) {

            textViewDescription.setText(order.getNotes());
        }
        if (order.getLocation() != null) {

            textViewLocation.setText(order.getLocation());
        }
        if (order.getAddress() != null) {

            textViewAddress.setText(order.getAddress());
        }

        return rowView;
    }


}
