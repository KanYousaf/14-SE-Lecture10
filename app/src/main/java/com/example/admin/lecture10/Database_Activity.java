package com.example.admin.lecture10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class Database_Activity extends AppCompatActivity {
    private OwnDBHandler myOwnDBHandler;
    private EditText SeasonName, SeasonNewName;
    private RatingBar seasonRate;
    private TextView season_details_display;
    private String season_name_string, season_new_name_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_);

        SeasonName = (EditText) this.findViewById(R.id.season_name);
        SeasonNewName = (EditText) this.findViewById(R.id.season_name_new);
        seasonRate = (RatingBar) this.findViewById(R.id.season_rating_bar);
        season_details_display = (TextView) this.findViewById(R.id.displayList);
        // instantiate the database adapter class
        myOwnDBHandler = new OwnDBHandler(Database_Activity.this);

        //print whole database data on start of activity
        printDatabase();

    }

    public void inputData() {
        season_name_string = SeasonName.getText().toString();
        season_new_name_string = SeasonNewName.getText().toString();
    }

    public void onInsertClicked(View view) {
        inputData();
        if (season_name_string.equals("")) {
            ToastMessage.message(Database_Activity.this, "Enter Season Name");
        } else {
            long id = myOwnDBHandler.insertSeasonData(season_name_string, seasonRate.getRating());
            if (id > 0) {
                ToastMessage.message(Database_Activity.this, "Data Entered");
            } else {
                ToastMessage.message(Database_Activity.this, "Data Not Entered");
            }
        }
        printDatabase();
    }

    public void printDatabase() {
        //print complete table data
        season_details_display.setText(myOwnDBHandler.printDatabase());
        SeasonName.setText("");
        SeasonNewName.setText("");
        seasonRate.setRating(2.5f);

    }

    public void onSearchClicked(View view) {
        inputData();
        if (season_name_string.equals("")) {
            ToastMessage.message(Database_Activity.this, "Enter Season Name");
        } else {
            season_details_display.setText(myOwnDBHandler.searchData(season_name_string));
        }
    }

    public void onUpdateClicked(View view) {
        inputData();
        if (season_name_string.equals("") || season_new_name_string.equals("")) {
            ToastMessage.message(Database_Activity.this, "Enter Season Names");
        } else {
            int count = myOwnDBHandler.updateData(season_name_string, season_new_name_string, seasonRate.getRating());
            if (count > 0) {
                ToastMessage.message(Database_Activity.this, "Data Updated");
            } else {
                ToastMessage.message(Database_Activity.this, "Data Not Updated");
            }
        }
        printDatabase();
    }

    public void onDeleteClicked(View view) {

        inputData();
        int count = myOwnDBHandler.deleteData(season_name_string);
        if (count > 0) {
            ToastMessage.message(Database_Activity.this, "Data Deleted");
        } else {
            ToastMessage.message(Database_Activity.this, "Data Not Deleted");
        }
        printDatabase();
    }
}
