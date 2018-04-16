package com.maintenancesolution.ems.Misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Views.CustomerRequestForm;
import com.maintenancesolution.ems.Views.OrderDetail;

import java.util.ArrayList;

public class WorkTypeAdapater extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mDataSource;
    private CustomerRequestForm customerRequestForm = null;
    private OrderDetail orderDetail = null;

    private WorkTypeAdapater workTypeAdapater;

    public WorkTypeAdapater(Context context, ArrayList<String> items, CustomerRequestForm customerRequestForm, OrderDetail orderDetail) {
        this.mContext = context;
        this.mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.customerRequestForm = customerRequestForm;
        this.orderDetail = orderDetail;
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
        if (customerRequestForm != null) {
            workTypeAdapater = customerRequestForm.getWorkTypeAdapater();
        } else {
            workTypeAdapater = orderDetail.getWorkTypeAdapater();
        }
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.remove(position);
                workTypeAdapater.notifyDataSetChanged();
                if (mDataSource.size() == 0) {
                    if (orderDetail != null) {
                        if (mDataSource.size() == 0) {

                            orderDetail.getListViewSelectedJobs().setVisibility(View.GONE);
                            orderDetail.setSpinnerUi();
                        } else {
                            orderDetail.getListViewSelectedJobs().setVisibility(View.VISIBLE);
                            orderDetail.setSpinnerUi();
                        }
                    }

                    if (customerRequestForm != null) {
                        if (mDataSource.size() == 0) {

                            customerRequestForm.getListViewSelectedJobs().setVisibility(View.GONE);
                            customerRequestForm.setSpinnerUi();
                        } else {
                            customerRequestForm.getListViewSelectedJobs().setVisibility(View.VISIBLE);
                            customerRequestForm.setSpinnerUi();
                        }
                    }
                }


            }
        });


        return rowView;
    }


}
