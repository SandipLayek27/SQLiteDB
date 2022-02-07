package com.sandiplayek.sqlitedb;

public class HelperClass {

    public static String fetchDataWithType(String data, String dataType){

        String dataTypeResponse = "";

        if(dataType.equals("")){
            return dataTypeResponse;
        }

        switch (dataType){
            case "INTEGER":
                dataTypeResponse = data+" INTEGER";
                break;

            case "VARCHAR":
                dataTypeResponse = data+" VARCHAR";
                break;

            case "TEXT":
                dataTypeResponse = data+" TEXT";
                break;

            case "DATE":
                dataTypeResponse = data+" DATE";
                break;

            case "TINYINT":
                dataTypeResponse = data+" TINYINT";
                break;

            case "SMALLINT":
                dataTypeResponse = data+" SMALLINT";
                break;

            case "MEDIUMINT":
                dataTypeResponse = data+" MEDIUMINT";
                break;

            case "BIGINT":
                dataTypeResponse = data+" BIGINT";
                break;

            case "DECIMAL":
                dataTypeResponse = data+" DECIMAL";
                break;

            case "FLOAT":
                dataTypeResponse = data+" FLOAT";
                break;

            case "DOUBLE":
                dataTypeResponse = data+" DOUBLE";
                break;

            case "REAL":
                dataTypeResponse = data+" REAL";
                break;

            case "BIT":
                dataTypeResponse = data+" BIT";
                break;

            case "BOOLEAN":
                dataTypeResponse = data+" BOOLEAN";
                break;

            case "SERIAL":
                dataTypeResponse = data+" SERIAL";
                break;

            case "DATETIME":
                dataTypeResponse = data+" DATETIME";
                break;

            case "TIMESTAMP":
                dataTypeResponse = data+" TIMESTAMP";
                break;

            case "TIME":
                dataTypeResponse = data+" TIME";
                break;

            case "YEAR":
                dataTypeResponse = data+" YEAR";
                break;

            case "CHAR":
                dataTypeResponse = data+" CHAR";
                break;

            case "TINYTEXT":
                dataTypeResponse = data+" TINYTEXT";
                break;

            case "MEDIUMTEXT":
                dataTypeResponse = data+" MEDIUMTEXT";
                break;

            case "LONGTEXT":
                dataTypeResponse = data+" LONGTEXT";
                break;

            case "BINARY":
                dataTypeResponse = data+" BINARY";
                break;

            case "VARBINARY":
                dataTypeResponse = data+" VARBINARY";
                break;

            case "TINYBLOB":
                dataTypeResponse = data+" TINYBLOB";
                break;

            case "MEDIUMBLOB":
                dataTypeResponse = data+" MEDIUMBLOB";
                break;

            case "LONGBLOB":
                dataTypeResponse = data+" LONGBLOB";
                break;

            case "BLOB":
                dataTypeResponse = data+" BLOB";
                break;

            case "ENUM":
                dataTypeResponse = data+" ENUM";
                break;

            case "SET":
                dataTypeResponse = data+" SET";
                break;

            case "GEOMETRY":
                dataTypeResponse = data+" GEOMETRY";
                break;

            case "POINT":
                dataTypeResponse = data+" POINT";
                break;

            case "LINESTRING":
                dataTypeResponse = data+" LINESTRING";
                break;

            case "POLYGON":
                dataTypeResponse = data+" POLYGON";
                break;

            case "MULTIPOINT":
                dataTypeResponse = data+" MULTIPOINT";
                break;

            case "MULTILINESTRING":
                dataTypeResponse = data+" MULTILINESTRING";
                break;

            case "MULTIPOLYGON":
                dataTypeResponse = data+" MULTIPOLYGON";
                break;

            case "GEOMETRYCOLLECTION":
                dataTypeResponse = data+" GEOMETRYCOLLECTION";
                break;

            case "JSON":
                dataTypeResponse = data+" JSON";
                break;

            default:
                dataTypeResponse = "";
                break;
        }
        return dataTypeResponse;
    }

    public static String formattedQuery(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
    }

}
