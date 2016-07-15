package com.rsample.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rsample.R;
import com.rsample.model.PhoneBook;

import java.util.List;

/**
 * Created by Tosin Onikute on 6/28/16.
 */
public class ListViewAdapter extends ArrayAdapter<PhoneBook> {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<PhoneBook> phoneBookList;
    private SparseBooleanArray mSelectedItemsIds;

    public ListViewAdapter(Context context, int resourceId, List<PhoneBook> phoneBookList) {
        super(context, resourceId, phoneBookList);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.phoneBookList = phoneBookList;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView name, phoneNumber, email;
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_list, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.phoneNumber = (TextView) view.findViewById(R.id.phonenumber);
            holder.email = (TextView) view.findViewById(R.id.email);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        holder.name.setText(phoneBookList.get(position).getName()+" "+phoneBookList.get(position).getLastName());
        holder.phoneNumber.setText(phoneBookList.get(position).getPhone());
        holder.email.setText(phoneBookList.get(position).getEmail());

        return view;
    }

    @Override
    public void remove(PhoneBook object) {
        //phoneBookList.remove(object);
        object.removeFromRealm();
        notifyDataSetChanged();
    }

    public List<PhoneBook> getPhoneBooks() {
        return phoneBookList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
