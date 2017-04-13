package com.example.admin.lecture10;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Admin on 4/13/2017.
 */

public class ToastMessage {

    public static void message(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
