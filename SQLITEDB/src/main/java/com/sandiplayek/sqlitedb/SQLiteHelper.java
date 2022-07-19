package com.sandiplayek.sqlitedb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

public class SQLiteHelper {

    Context context;
    String tableName="";
    String dataBaseName="";
    private SQLiteDatabase db;
    JSONArray jsonArray = null;
    int id = 0;
    String key ="";
    String value = "";
    int intVal = 0;
    JSONObject jsonObject = null;

    // CREATE FULL STRUCTURED TABLE, INSERT TABLE, SAVE IMAGE WITH DATA, FETCH IMAGE USING id
    public SQLiteHelper(Context context,String dataBaseName, String tableName, JSONArray jsonArray) {
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.jsonArray = jsonArray;
        this.tableName = tableName;
    }

    // FOR FETCH ALL DATA FROM TABLE
    public SQLiteHelper(Context context,String dataBaseName, String tableName){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
    }

    // FOR FETCH,DELETE DATA USING ID FROM TABLE
    public SQLiteHelper(Context context,String dataBaseName, String tableName, int id){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
        this.id = id;
    }

    // FOR FETCH,DELETE DATA USING KEY AND VALUE(STRING) MATCHING FROM TABLE
    public SQLiteHelper(Context context,String dataBaseName, String tableName, String key, String value){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
        this.key = key;
        this.value = value;
    }

    // FOR FETCH,DELETE DATA USING KEY AND VALUE(INT) MATCHING FROM TABLE
    public SQLiteHelper(Context context,String dataBaseName, String tableName, String key, int intVal){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
        this.key = key;
        this.intVal = intVal;
    }

    // UPDATE TABLE USING ID
    public SQLiteHelper(Context context,String dataBaseName, String tableName, JSONObject jsonObject, int id){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
        this.jsonObject = jsonObject;
        this.id = id;
    }

    // UPDATE TABLE USING KEY AND VALUE(STRING) COMPARE
    public SQLiteHelper(Context context,String dataBaseName, String tableName, JSONObject jsonObject, String key, String value){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
        this.jsonObject = jsonObject;
        this.key = key;
        this.value = value;
    }

    // UPDATE TABLE USING KEY AND VALUE(INT) COMPARE
    public SQLiteHelper(Context context,String dataBaseName, String tableName, JSONObject jsonObject, String key, int intVal){
        this.context = context;
        this.dataBaseName = dataBaseName;
        this.tableName = tableName;
        this.jsonObject = jsonObject;
        this.key = key;
        this.intVal = intVal;
    }



    public boolean createFullStructuredTable(){

        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "Database Name Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "Table Name Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(jsonArray.length() <= 0){
            Toast.makeText(context, "Data Must be Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try{
            if(createOrOpenDatabase()){
                if(createTable()){
                    if(insertData()){
                        //Toast.makeText(context, "SUCCESSFULLY DATA INSERTED", Toast.LENGTH_SHORT).show();
                        return true;
                    }else{
                        Toast.makeText(context, "DATA INSERT FAILURE", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else {
                    Toast.makeText(context, "FAILED TO CREATE TABLE", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(context, "DATABASE NOT CREATED", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(){

        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "Database Name Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "Table Name Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(jsonArray.length() <= 0){
            Toast.makeText(context, "Data Must be Required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try{
            if(createOrOpenDatabase()){
                if(createTable()){
                    if(insertData()){
                        //Toast.makeText(context, "SUCCESSFULLY DATA INSERTED", Toast.LENGTH_SHORT).show();
                        return true;
                    }else{
                        Toast.makeText(context, "DATA INSERT FAILURE", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else {
                    Toast.makeText(context, "FAILED TO CREATE TABLE", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(context, "DATABASE NOT CREATED", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public JSONArray fetchAll(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Cursor c;
        try{
            createOrOpenDatabase();
            String SELECT_SQL = "SELECT "+context.getResources().getString(R.string.star)+" FROM "+tableName;
            c = db.rawQuery(SELECT_SQL, null);
            int dataCount = c.getCount();
            if(dataCount < 1){
                //Toast.makeText(context, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                return null;
            }
            int columnCount = c.getColumnCount();
            if(c.moveToFirst()){
                while(!c.isAfterLast()){
                    jsonObject = new JSONObject();
                    for(int i =0; i<columnCount; i++){
                        try{
                            switch (c.getType(i))  {
                                case Cursor.FIELD_TYPE_FLOAT:
                                    jsonObject.put(c.getColumnName(i),c.getFloat(i));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    jsonObject.put(c.getColumnName(i),c.getInt(i));
                                    break;
                                case Cursor.FIELD_TYPE_STRING:
                                    jsonObject.put(c.getColumnName(i),c.getString(i));
                                    break;
                                case Cursor.FIELD_TYPE_BLOB:
                                    jsonObject.put(c.getColumnName(i),c.getBlob(i));
                                    break;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }
                    jsonArray.put(jsonObject);
                    c.moveToNext();
                }
                c.close();
                if(jsonArray.length()>0){
                    return jsonArray;
                }else{
                    return null;
                }
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public JSONObject fetchById(){
        if(id == 0){
            Toast.makeText(context, "ID REQUIRED", Toast.LENGTH_SHORT).show();
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        Cursor c;
        try{
            createOrOpenDatabase();
            String SELECT_SQL = "SELECT "+context.getResources().getString(R.string.star)+" FROM "+tableName+" WHERE id="+id;
            c = db.rawQuery(SELECT_SQL, null);
            int dataCount = c.getCount();
            if(dataCount < 1){
                //Toast.makeText(context, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                return null;
            }
            int columnCount = c.getColumnCount();
            if(c.moveToFirst()){
                    for(int i =0; i<columnCount; i++){
                        try{
                            switch (c.getType(i))  {
                                case Cursor.FIELD_TYPE_FLOAT:
                                    jsonObject.put(c.getColumnName(i),c.getFloat(i));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    jsonObject.put(c.getColumnName(i),c.getInt(i));
                                    break;
                                case Cursor.FIELD_TYPE_STRING:
                                    jsonObject.put(c.getColumnName(i),c.getString(i));
                                    break;
                                case Cursor.FIELD_TYPE_BLOB:
                                    jsonObject.put(c.getColumnName(i),c.getBlob(i));
                                    break;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }
                    c.close();
                if(jsonObject.length()>0 || jsonObject != null){
                    return jsonObject;
                }else{
                    return null;
                }
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray fetchByKeyValue(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Cursor c;
        String SELECT_SQL = "";
        if(key.equalsIgnoreCase("")){
            Toast.makeText(context, "KEY PART REQUIRED", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(value.equalsIgnoreCase("") && intVal == 0){
            Toast.makeText(context, "PROPER PARAMETERS REQUIRED", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!value.equalsIgnoreCase("")  && value instanceof String){
            value = "'"+value+"'";
            SELECT_SQL = "SELECT "+context.getResources().getString(R.string.star)+" FROM "+tableName+" WHERE "+key+" = "+value;
        }else{
            SELECT_SQL = "SELECT "+context.getResources().getString(R.string.star)+" FROM "+tableName+" WHERE "+key+" = "+intVal;
        }

        try{
            createOrOpenDatabase();
            c = db.rawQuery(SELECT_SQL, null);
            int dataCount = c.getCount();
            if(dataCount < 1){
                //Toast.makeText(context, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                return null;
            }
            int columnCount = c.getColumnCount();
            if(c.moveToFirst()){
                while(!c.isAfterLast()){
                    jsonObject = new JSONObject();
                    for(int i =0; i<columnCount; i++){
                        try{
                            switch (c.getType(i))  {
                                case Cursor.FIELD_TYPE_FLOAT:
                                    jsonObject.put(c.getColumnName(i),c.getFloat(i));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    jsonObject.put(c.getColumnName(i),c.getInt(i));
                                    break;
                                case Cursor.FIELD_TYPE_STRING:
                                    jsonObject.put(c.getColumnName(i),c.getString(i));
                                    break;
                                case Cursor.FIELD_TYPE_BLOB:
                                    jsonObject.put(c.getColumnName(i),c.getBlob(i));
                                    break;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }
                    jsonArray.put(jsonObject);
                    c.moveToNext();
                }
                c.close();
                if(jsonArray.length()>0){
                    return jsonArray;
                }else{
                    return null;
                }
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateDataById(){
        if(id < 1){
            Toast.makeText(context, "ENTER VALID ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "TABLE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "DATABASE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(jsonObject == null || jsonObject.length() < 1){
            Toast.makeText(context, "ENTER VALID UPDATED DATA", Toast.LENGTH_SHORT).show();
            return false;
        }
        String data = "UPDATE "+tableName+" SET ";

        try{
            createOrOpenDatabase();
            Iterator<String> iter = jsonObject.keys();
            String keypartData = "";
            while (iter.hasNext()) {
                keypartData = iter.next();
                Object objDataVal = jsonObject.get(keypartData);
                if(objDataVal instanceof Integer || objDataVal instanceof Long || objDataVal instanceof Boolean || objDataVal instanceof Float){
                    data = data + keypartData+" = "+jsonObject.getString(keypartData) +",";
                }else{
                    data = data + keypartData+" = "+"'"+jsonObject.getString(keypartData)+"'" +",";
                }
            }
            data = HelperClass.formattedQuery(data);
            data = data + " WHERE id = "+id;
            db.execSQL(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDataByKey(){
        if(key.equalsIgnoreCase("")){
            Toast.makeText(context, "KEY PART REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(value.equalsIgnoreCase("") && intVal == 0){
            Toast.makeText(context, "PROPER PARAMETERS REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "TABLE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "DATABASE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(jsonObject == null || jsonObject.length() < 1){
            Toast.makeText(context, "ENTER VALID UPDATED DATA", Toast.LENGTH_SHORT).show();
            return false;
        }
        String data = "UPDATE "+tableName+" SET ";

        try{
            createOrOpenDatabase();
            Iterator<String> iter = jsonObject.keys();
            String keypartData = "";
            while (iter.hasNext()) {
                keypartData = iter.next();
                Object objDataVal = jsonObject.get(keypartData);
                if(objDataVal instanceof Integer || objDataVal instanceof Long ||objDataVal instanceof Boolean || objDataVal instanceof Float){
                    data = data + keypartData+" = "+jsonObject.getString(keypartData) +",";
                }else{
                    data = data + keypartData+" = "+"'"+jsonObject.getString(keypartData)+"'" +",";
                }
            }
            data = HelperClass.formattedQuery(data);
            if(!value.equalsIgnoreCase("")  && value instanceof String){
                data = data+ " WHERE "+key+" = "+"'"+value+"'";
            }else{
                data = data+" WHERE "+key+" = "+intVal;
            }
            db.execSQL(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteDataById(){
        if(id < 1){
            Toast.makeText(context, "ENTER VALID ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "TABLE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "DATABASE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        try{
            createOrOpenDatabase();
            String data = "DELETE FROM "+tableName;
            data = data +" WHERE id = "+id;
            db.execSQL(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDataByKeyValue(){
        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "TABLE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "DATABASE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(key.equalsIgnoreCase("")){
            Toast.makeText(context, "TABLE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(value.equalsIgnoreCase("") && intVal == 0){
            Toast.makeText(context, "PROPER PARAMETERS REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        try{
            createOrOpenDatabase();
            String data = "DELETE FROM "+tableName;
            if(!value.equalsIgnoreCase("")  && value instanceof String){
                data = data+ " WHERE "+key+" = "+"'"+value+"'";
            }else{
                data = data+" WHERE "+key+" = "+intVal;
            }
            db.execSQL(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean dropTable(){
        if(tableName.equalsIgnoreCase("")){
            Toast.makeText(context, "TABLE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dataBaseName.equalsIgnoreCase("")){
            Toast.makeText(context, "DATABASE NAME REQUIRED", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            createOrOpenDatabase();
            String data = context.getResources().getString(R.string.drop)+" "+tableName;
            db.execSQL(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //START METHODS SQLLITE DATABASE HANDLING
    public boolean createOrOpenDatabase(){
        try{
            if(dataBaseName.equalsIgnoreCase("")){
                Toast.makeText(context, "Database Name Required", Toast.LENGTH_SHORT).show();
                return false;
            }
            db=context.openOrCreateDatabase(dataBaseName, Context.MODE_PRIVATE, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTable(){
        String synOne = "CREATE TABLE IF NOT EXISTS "+tableName;
        String synTwo = synOne+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,";

        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            Iterator<String> iter = jsonObject.keys();
            String keyPart = "";
            while (iter.hasNext()) {
                keyPart = iter.next();
                Object objDataVal = jsonObject.get(keyPart);
                if(objDataVal instanceof Integer){
                    synTwo = synTwo+HelperClass.fetchDataWithType(keyPart,"INTEGER");
                }else if( objDataVal instanceof Long ){
                    synTwo = synTwo+HelperClass.fetchDataWithType(keyPart,"LONGBLOB");
                }else if( objDataVal instanceof Boolean){
                    synTwo = synTwo+HelperClass.fetchDataWithType(keyPart,"BOOLEAN");
                }else if( objDataVal instanceof Float){
                    synTwo = synTwo+HelperClass.fetchDataWithType(keyPart,"FLOAT");
                }else if( objDataVal instanceof byte[]){
                    synTwo = synTwo+HelperClass.fetchDataWithType(keyPart,"BLOB");
                }else{
                    synTwo = synTwo+HelperClass.fetchDataWithType(keyPart,"VARCHAR");
                }
                synTwo =synTwo +",";
            }

            synTwo = HelperClass.formattedQuery(synTwo)+");";
            try {
                db.execSQL(synTwo);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertData(){
        try{
            int fileCase = 0;
            String fileFieldName = null;
            byte[] fileData = new byte[0];
            if(dataBaseName.equalsIgnoreCase("")){
                Toast.makeText(context, "Database Name Required", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(tableName.equalsIgnoreCase("")){
                Toast.makeText(context, "Table Name Required", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(jsonArray.length() <= 0){
                Toast.makeText(context, "Data Must be Required", Toast.LENGTH_SHORT).show();
                return false;
            }
            String finalQuery ="";

            try{
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Iterator<String> iter = jsonObject.keys();
                String keyPart = "(";
                while (iter.hasNext()) {
                    keyPart = keyPart + iter.next() + ",";
                }
                keyPart = HelperClass.formattedQuery(keyPart);
                keyPart = keyPart + ")";

                String query1 = "INSERT INTO "+tableName+" "+keyPart+" VALUES ";
                String data = "";
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Iterator<String> iter1 = jsonObject1.keys();
                    data = data+"(";
                    String keypartData ="";
                    while (iter1.hasNext()) {
                        keypartData = iter1.next();
                        Object objDataVal = jsonObject1.get(keypartData);
                        if(objDataVal instanceof Integer || objDataVal instanceof Long ||objDataVal instanceof Boolean || objDataVal instanceof Float){
                            data = data + jsonObject1.getString(keypartData) +",";
                        }else if(objDataVal instanceof byte[] ){
                            fileFieldName = keypartData;
                            fileData = (byte[]) jsonObject1.get(keypartData);
                            fileCase = 1;
                            data = data +"''" +",";
                        }else{
                            data = data +"'"+jsonObject1.getString(keypartData)+"'" +",";
                        }
                    }
                    data = HelperClass.formattedQuery(data);
                    data = data+"),";
                }
                data = HelperClass.formattedQuery(data);

                String query2 = query1+data;

                finalQuery = query2+";";
                db.execSQL(finalQuery);

                // IF FILE DATA AVAILABLE
                if(fileCase == 1){
                    int id = getLastInsertedId(tableName);
                    if(writeFile(id,fileFieldName,fileData)){
                        return true;
                    }else{
                        return false;
                    }
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeFile(int id, String fieldName, byte[] image) throws SQLiteException{
        try{
            ContentValues cv = new  ContentValues();
            cv.put(fieldName, image);
            db.update(tableName, cv, "id = ?", new String[]{String.valueOf(id)});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFileUsingId(int id, String fieldName, byte[] image) throws SQLiteException{
        try{
            createOrOpenDatabase();
            ContentValues cv = new  ContentValues();
            cv.put(fieldName, image);
            db.update(tableName, cv, "id = ?", new String[]{String.valueOf(id)});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // GETTING IMAGE USING PRIMARY KEY
    public Bitmap getImageUsingId(int id, String fieldName) {
        createOrOpenDatabase();
        Cursor cursor = db.query(tableName, new String[] { fieldName }, "id = ?",
                new String[] { String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        byte[] image = cursor.getBlob(0);
        Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
        return bmp;
    }

    public int getLastInsertedId(String tableName){
        try{
            int lastId = 0;
            String query = "SELECT id FROM "+tableName+" ORDER BY id DESC limit 1";
            Cursor c = db.rawQuery(query,null);
            if (c != null && c.moveToFirst()) {
                lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
            }
            return lastId;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
