package com.sandiplayek.sqlitedbhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sandiplayek.sqlitedb.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONArray jsonArray = fetchDummyData();

        //CREATE FULL STRUCTURED TABLE
        SQLiteHelper sqLiteHelperTableData = new SQLiteHelper(MainActivity.this,"DBMaster","master",jsonArray);
        if(sqLiteHelperTableData.createFullStructuredTable()){
            Toast.makeText(this, "SUCCESSFULLY CREATED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }

    public JSONArray fetchDummyData(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name","SANDIP");
            jsonObject.put("address","BANKURA");
            jsonObject.put("pincode",722101);
            jsonObject.put("mobile","8927281743");
            jsonObject.put("age",11);
            jsonArray.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("name","ARUNAVA");
            jsonObject.put("address","KOLKATA");
            jsonObject.put("pincode",722121);
            jsonObject.put("mobile","1234567890");
            jsonObject.put("age",10);
            jsonArray.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("name","MUGDHO");
            jsonObject.put("address","KOLKATA");
            jsonObject.put("pincode",722121);
            jsonObject.put("mobile","9876543210");
            jsonObject.put("age",5);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}