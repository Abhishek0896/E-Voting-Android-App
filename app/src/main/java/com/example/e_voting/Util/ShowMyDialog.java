package com.example.e_voting.Util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_voting.MainActivity;
import com.example.e_voting.R;

public class ShowMyDialog {
    Context context;

    public ShowMyDialog(Context context) {
        this.context = context;
    }

    public void showPositivedialog(String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ok_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btn = dialog.findViewById(R.id.postivebtn);
        TextView textView = dialog.findViewById(R.id.tvpositive);
        textView.setText(message);

        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showNegativeDialog(String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.negative_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btn = dialog.findViewById(R.id.negativeBtn);
        TextView textView = dialog.findViewById(R.id.tvNegative);
        textView.setText(message);
        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showPositivedialogRegistration(String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ok_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btn = dialog.findViewById(R.id.postivebtn);
        TextView textView = dialog.findViewById(R.id.tvpositive);
        textView.setText(message);

        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    public void showNegativeDialogRegistration(String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.negative_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btn = dialog.findViewById(R.id.negativeBtn);
        TextView textView = dialog.findViewById(R.id.tvNegative);
        textView.setText(message);
        dialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

}
