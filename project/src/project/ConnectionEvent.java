package project;

import java.sql.Connection;

public class ConnectionEvent extends java.util.EventObject {
    private Connection conn;

    public ConnectionEvent(Object source, Connection conn) {
        super(source);
        this.conn = conn;
    }
    public Connection getConnection() {
        return conn;
    }
}