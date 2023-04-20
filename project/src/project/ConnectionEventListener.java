package project;

public interface ConnectionEventListener extends java.util.EventListener {
    void connectionEstablished(ConnectionEvent event);
}