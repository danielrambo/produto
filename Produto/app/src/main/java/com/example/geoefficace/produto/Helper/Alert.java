package com.example.geoefficace.produto.Helper;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoefficace.produto.DataStorage.IAsyncCallback;
import com.example.geoefficace.produto.R;

/**
 * Created by geoefficace on 04/04/2019.
 */

public class Alert {

    private Context context;
    private AlertDialog dialog;
    private View view;

    public Alert(Context context) {
        this.context                = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        this.dialog                 = builder.create();
    }

    private void createView(String title, String alertText){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = layoutInflater.inflate(R.layout.popup_with_alert, null);

        TextView textView_title = view.findViewById(R.id.title);
        textView_title.setText(title);

        TextView textView_alert = view.findViewById(R.id.alert_text);
        textView_alert.setText(alertText);
    }

    public View getView(){
        return this.view;
    }

    public void show(String title, String alertText, final IAsyncCallback<AlertDialog> callback){

        this.createView(title, alertText);
        this.actions(view, callback);

        dialog.setView(view);
        dialog.show();

    }

    private void actions(View view, final IAsyncCallback<AlertDialog> callback){

        Button confirmar = view.findViewById(R.id.confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.receive(null, dialog);
            }
        });

        Button cancel = view.findViewById(R.id.cancelar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.receive(null, null);
            }
        });

    }

}