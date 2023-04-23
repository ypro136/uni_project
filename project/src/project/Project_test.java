package project;


public class Project_test {
	public static void main(String[] args) {
		
		Login_and_connection login_frame = new Login_and_connection("login");
		
		login_frame.addConnectionEventListener(new ConnectionEventListener() {
            @Override
            public void connectionEstablished(ConnectionEvent event) {
                System.out.println("Connection established: " + event.getConnection());
            }
        });
        
		
		

}
}
