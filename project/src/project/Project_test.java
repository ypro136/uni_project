package project;


public class Project_test {
	public static void main(String[] args) {
		
		Login_and_connection login_frame = new Login_and_connection("login");
		
		
		/*
		 // Example of Override method to add an Event Listener.
		 ConnectionEventListener connectionEventListener = new ConnectionEventListener() {
            @Override
            public void connectionEstablished(ConnectionEvent event) {
                System.out.println("Connection established: " + event.getConnection());
            }
        };
        
        */
		
		
		// Example of lambda expression method to add an Event Listener.
		ConnectionEventListener connectionEventListener = (ConnectionEvent event) -> {
                System.out.println("Connection established: " + event.getConnection());
            };
            
            
            
            login_frame.addConnectionEventListener(connectionEventListener);
           
		}
	}
