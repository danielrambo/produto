package com.example.geoefficace.produto.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.geoefficace.produto.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by geoefficace on 04/04/2019.
 */

public class ListSampleWithMenuAdapter<T> extends BaseAdapter {

    private List<T> list_sample;
    private Context context;
    private IListWithMenu<T> object;
    private List<String> action_view;

    public ListSampleWithMenuAdapter(List<T> list_sample, Context context, IListWithMenu<T> object) {
        this.list_sample = list_sample;
        this.context     = context;
        this.object      = object;
        this.action_view = new ArrayList<>();
    }

    public void addListActionView(String action){
        this.action_view.add(action);
    }

    private boolean is_action_view(String action){

        if(this.action_view.size() == 0)
            return true;

        boolean is_view = true;
        for(String obj : action_view){
            if(obj.equals(action))
                is_view = false;
        }

        return is_view;

    }

    @Override
    public int getCount() {
        return list_sample.size();
    }

    @Override
    public Object getItem(int position) {
        return list_sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if(view == null){ view = inflater.inflate(R.layout.cell_sample_with_menu, parent, false);}

        TextView textView = view.findViewById(R.id.value);
        textView.setText(list_sample.get(position).toString());

        ImageView imageView = view.findViewById(R.id.icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_list_view, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.edit:
                                object.edit(list_sample.get(position));
                                return false;

                            case R.id.remove:
                                object.remove(list_sample.get(position));
                                return false;

                            default:
                                return false;

                        }

                    }

                });

                popupMenu.show();

            }
        });

        return view;
    }
}
