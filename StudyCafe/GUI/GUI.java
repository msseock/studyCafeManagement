import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GUI {

	public static void main(String[] args) {
		Admin admin;
		
		try {
//			// 파일 입력받기  		
//    		ObjectInputStream in3 = new ObjectInputStream(new FileInputStream("/Users/seogminsol/OneDrive - 서울여자대학교/수업/2-2/6_JAVA응용/Projects/studyCafe/StudyCafe/roomData.dat"));
//    		ObjectInputStream in4 = new ObjectInputStream(new FileInputStream("/Users/seogminsol/OneDrive - 서울여자대학교/수업/2-2/6_JAVA응용/Projects/studyCafe/StudyCafe/incomeData.dat"));
//        	admin = new Admin(in3, in4);
			admin = new Admin();
        	
			new MainMenu(admin);
			
//			// 파일 출력(저장)
//	        ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
//	        admin.writeRoomInfos(out3);
//	        ObjectOutputStream out4 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
//	        admin.writeIncomeInfo(out4);
//	        out3.close();
//	        out4.close();
	        
//		} catch (FileNotFoundException fnfe) {
//			System.out.println("ERROR: File not found");
//			System.out.println(fnfe.getMessage());
//        } catch (IOException ioe) {
//        	System.out.println("ERROR: I/O excption");
//        	System.out.println(ioe.getMessage());
        } catch (Exception e) {
			System.out.println(e.getMessage());
        }
		
	}

}
