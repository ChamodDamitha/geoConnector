package com.chamod.rest;

import java.util.Date;
import java.sql.*;

/**
 * Created by chamod on 7/30/17.
 */
public class DBGeoDetector implements GeoDetector {
    private final String SQL_USER = "root";
    private final String SQL_PASS = "root";

    private final String GEO_COORDINATE_TABLE = "geocoordinates";


    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;



    public double getPathDistance(double lat1, double long1, double lat2, double long2) {
        return 0;
    }

    public GeoCoordinate getCoordinate(int requestUserId, int responseUserId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost/geodetector?user=root&password=root";
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(String.format("select * from %s where user_id = %s", GEO_COORDINATE_TABLE, responseUserId));

            if(resultSet.next()){
                return new GeoCoordinate(resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"), resultSet.getString("time_stamp"));
            }
            else{
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setCoordinate(int requestUserId, int responseUserId, double latitude, double longitude) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost/geodetector?user=root&password=root";
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(String.format("select * from %s where user_id = %s", GEO_COORDINATE_TABLE, responseUserId));

            if(resultSet.next()){
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("update geocoordinates set latitude = ?, longitude = ?, " +
                                "time_stamp = ? where user_id = ?");
                preparedStatement.setDouble(1, latitude);
                preparedStatement.setDouble(2, longitude);
                preparedStatement.setString(3, new Date().toString());
                preparedStatement.setInt(4, responseUserId);
            }
            else{
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("insert into geocoordinates values (?, ? , ?, ?)");
                preparedStatement.setInt(1, responseUserId);
                preparedStatement.setDouble(2, latitude);
                preparedStatement.setDouble(3, longitude);
                preparedStatement.setString(4, new Date().toString());

            }
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
