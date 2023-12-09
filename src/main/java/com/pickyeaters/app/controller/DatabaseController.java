package com.pickyeaters.app.controller;

import com.pickyeaters.app.model.Session;
import com.pickyeaters.app.utils.DatabaseControllerException;

import java.sql.*;

public class DatabaseController {
    private static final int MIN_PORT_NUMBER = 0;
    private static final int MAX_PORT_NUMBER = 65535;

    private static Connection conn = null;
    public static void init(String host, int port, String name, String user, String password) throws DatabaseControllerException {
        String url = formatURL(host, port, name);
        connect(url, user, password);
    }
    private static String formatURL(String host, int port, String name) throws DatabaseControllerException {
        if(host == null || host.isEmpty()) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Host invalid.");
        }
        if(port <= MIN_PORT_NUMBER || port >= MAX_PORT_NUMBER) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Port invalid.");
        }
        if(name == null || name.isEmpty()) {
            throw new DatabaseControllerException("Cannot create a jdbc URL. Name invalid.");
        }
        return "jdbc:postgresql://" + host + ":" + port + "/" + name;
    }
    private static void connect(String url, String user, String password) throws DatabaseControllerException {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new DatabaseControllerException("Cannot connect to database: " + url);
        }
    }

    public static Session login(String username, String password) throws DatabaseControllerException {
        if(conn == null) {
            throw new DatabaseControllerException("Connection is not ready.");
        }
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("CALL login(?,?,?)");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setNull(3, Types.VARCHAR);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot create a login call.");
        }

        try {
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot execute a login call.");
        }

        String token = null;
        try {
            token = cs.getString(3);
        } catch (SQLException e) {
            throw new DatabaseControllerException("Cannot get response from login call.");
        }
        if(token == null) {
            return new Session();
        } else {
            return new Session(token);
        }
    }

}
