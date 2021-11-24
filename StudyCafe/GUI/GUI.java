import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GUI {

	public static void main(String[] args) {
		Admin admin;
		
		try {
			// 파일 입력받기  		
    		ObjectInputStream in3 = new ObjectInputStream(new FileInputStream("roomData.dat"));
    		ObjectInputStream in4 = new ObjectInputStream(new FileInputStream("incomeData.dat"));
        	admin = new Admin(in3, in4);
//			admin = new Admin();
        	
			new MainMenu(admin);
			
	        
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: File not found");
			System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
        	System.out.println("ERROR: I/O excption");
        	System.out.println(ioe.getMessage());
        } catch (Exception e) {
			System.out.println(e.getMessage());
        }
		
	}

}
