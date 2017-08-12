package com.chamod.rest;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by chamod on 7/30/17.
 */
public class DBGeoDetector implements GeoDetector {
    private final String SQL_USER = "root";
    private final String SQL_PASS = "root";

    private final String USERS_TABLE = "users";
    private final String FRIENDS_TABLE = "friends";
    private final String FRIEND_REQUEST_TABLE = "friendrequests";
    private final String GEO_COORDINATE_TABLE = "geocoordinates";


    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public double getPathDistance(double lat1, double long1, double lat2, double long2) {
        return 0;
    }

    public GeoCoordinate getCoordinate(String userEmail) {
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
                    .executeQuery(String.format("select * from %s where email = '%s'",
                            GEO_COORDINATE_TABLE, userEmail));

            if (resultSet.next()) {
                return new GeoCoordinate(resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"), resultSet.getString("time_stamp"));
            } else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCoordinate(String userEmail, double latitude, double longitude) {
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
                    .executeQuery(String.format("select * from %s where email = '%s'",
                            GEO_COORDINATE_TABLE, userEmail));

            if (resultSet.next()) {
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("update geocoordinates set latitude = ?, longitude = ?" +
                                " where email = ?");
                preparedStatement.setDouble(1, latitude);
                preparedStatement.setDouble(2, longitude);
//                preparedStatement.setString(3, new Date().toString());
                preparedStatement.setString(3, userEmail);
            } else {
                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("insert into " +
                                "geocoordinates(email, latitude, longitude) values (?, ? , ?)");
                preparedStatement.setString(1, userEmail);
                preparedStatement.setDouble(2, latitude);
                preparedStatement.setDouble(3, longitude);
//                preparedStatement.setString(4, new Date().toString());

            }
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean areFriends(String requestUserEmail, String responseUserEmail) {
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
                    .executeQuery(String.format("select * from %s where " +
                                    "(email1 = '%s' and email2 = '%s') or (email1 = '%s' and email2 = '%s') LIMIT 1;",
                            FRIENDS_TABLE, requestUserEmail, responseUserEmail, responseUserEmail, requestUserEmail));

            if (resultSet.next()) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean makeFriends(String requestUserEmail, String responseUserEmail) {
        boolean alreadyFriends = areFriends(requestUserEmail, responseUserEmail);
        if (alreadyFriends) {
            return false;
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://localhost/geodetector?user=root&password=root";
                // Setup the connection with the DB
                connect = DriverManager
                        .getConnection(url);

                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("insert into " +
                                "friends(email1, email2) values (?, ?)");
                preparedStatement.setString(1, requestUserEmail);
                preparedStatement.setString(2, responseUserEmail);
                preparedStatement.execute();

                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean removeFriends(String requestUserEmail, String responseUserEmail) {
        boolean alreadyFriends = areFriends(requestUserEmail, responseUserEmail);
        if (!alreadyFriends) {
            return false;
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://localhost/geodetector?user=root&password=root";
                // Setup the connection with the DB
                connect = DriverManager
                        .getConnection(url);

                // Statements allow to issue SQL queries to the database
                statement = connect.createStatement();
                // Result set get the result of the SQL query
                statement.executeQuery(String.format("delete * from %s where " +
                                "(email1 = '%s' and email2 = '%s') or (email1 = '%s' and email2 = '%s');",
                        FRIENDS_TABLE, requestUserEmail, responseUserEmail, responseUserEmail, requestUserEmail));

                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean sendFriendRequest(String requestUserEmail, String responseUserEmail) {
        boolean alreadyFriends = areFriends(requestUserEmail, responseUserEmail);
        if (alreadyFriends) {
            return false;
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://localhost/geodetector?user=root&password=root";
                // Setup the connection with the DB
                connect = DriverManager
                        .getConnection(url);

                // PreparedStatements can use variables and are more efficient
                preparedStatement = connect
                        .prepareStatement("insert into " +
                                "friendrequests(requestUserEmail, responseUserEmail) values (?, ?)");
                preparedStatement.setString(1, requestUserEmail);
                preparedStatement.setString(2, responseUserEmail);
                preparedStatement.execute();

                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean acceptFriendRequest(String requestUserEmail, String responseUserEmail) {
        makeFriends(requestUserEmail, responseUserEmail);
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost/geodetector?user=root&password=root";
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            statement.executeQuery(String.format("delete * from %s where " +
                            "requestUserEmail = '%s' and responseUserEmail = '%s';",
                    FRIEND_REQUEST_TABLE, requestUserEmail, responseUserEmail));

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getFriendRequests(String userEmail) {
        ArrayList<String> friendRequests = new ArrayList<String>();

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
                    .executeQuery(String.format("select responseUserEmail from %s where requestUserEmail = '%s';",
                            FRIEND_REQUEST_TABLE, userEmail));

            if (resultSet.next()) {
                friendRequests.add(resultSet.getString("responseUserEmail"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
    }

    public ArrayList<User> getFriends(String userEmail) {
        ArrayList<User> friends = new ArrayList<User>();

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
                    .executeQuery(String.format("select * from %s JOIN users where email1 = '%s' or email2 = '%s' ;",
                            FRIENDS_TABLE, userEmail, userEmail));

            if (resultSet.next()) {
                friends.add(new User(
                        resultSet.getString("email"),
                        resultSet.getString("name")
                ));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
}

