// Studycafe UI class
import java.util.InputMismatchException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        Admin admin;
        
        System.out.println("Welcome!");
        
    	try {
//    		// 파일 입력받기(DataInputSteam ver.)
//        	File roomData = new File("./roomData.dat");
//        	roomData.createNewFile();
//        	File incomeData = new File("./incomeData.dat");
//        	incomeData.createNewFile();
//        	DataInputStream in = null, in2 = null;
//        	in = new DataInputStream(new FileInputStream(roomData));
//        	in2 = new DataInputStream(new FileInputStream(incomeData));
//        	admin = new Admin(in, in2);
        	
        	// 파일 입력받기(ObjectInputSteam ver.)    		
    		ObjectInputStream in3 = new ObjectInputStream(new FileInputStream("./roomData.dat"));
    		ObjectInputStream in4 = new ObjectInputStream(new FileInputStream("./incomeData.dat"));
        	admin = new Admin(in3, in4);
        	
	    	// 여기부터 while문 범위
	    	while (!exit) {
	    		try {
	    			System.out.println();
	                System.out.println("1. 사용자 || 2. 관리자 || 3. 종료");
	                int menuNumber = sc.nextInt();
	
	                switch (menuNumber) {
	                    case 1: // ** 사용자 **
	                        System.out.println("사용자메뉴입니다.");
	                        System.out.println("1. 좌석 선택 || 2. 이용완료 || 3. 메인메뉴로");
	                        int userMenu = sc.nextInt();
	
	                        switch (userMenu) {
	                        case 1: // 1. 사용자 > 1. 좌석 선택(체크인)
	                            // 몇인용 좌석?
	                            System.out.print("이용할 좌석이 몇인용 좌석인지 입력하세요: ");
	                            int size = sc.nextInt();
		                            
	                            // 이용 가능여부 boolean ArrayList로 받아오기	
	                            ArrayList<Boolean> available = admin.searchAvailableRoom(size);
	                            int lastRoomNumber = admin.getLastRoomNumber(), falseCount = 0;
	                            for (int i = 0; i < lastRoomNumber; i++) {
	                            	// 이용 가능한 방이라면 정보 출력
	                                if (available.get(i)) {
	                                	Room currentAvailableRoom = admin.getRoom(i);
	                                	System.out.println(i + "번: " + currentAvailableRoom.getRoomName());
	                                    System.out.println("시간당 요금: " + currentAvailableRoom.getChargePerHour());
	                                    System.out.println("10분당 추가요금: " + currentAvailableRoom.getSurcharge());
	                                    System.out.println();
	                                }
	                                else {
	                                	falseCount++;
	                                	continue;
	                                }
	                            }
	                            
	                            // 사용 가능한 방이 있는 경우
	                            if (falseCount != lastRoomNumber) {
	                            	System.out.println("사용 가능합니다.");
		
		                            // 좌석 선택
		                            System.out.print("좌석 번호를 선택해주세요: ");
		                            int roomNo = sc.nextInt();
		                            
		                            // 사용자가 입력한 좌석 번호가 배정 불가능한 경우
		                            if (roomNo > lastRoomNumber) {
		                            	System.out.println(roomNo + "번 좌석은 없습니다.");
		                            	break;
		                            }
		                            else if (admin.getRoom(roomNo).getOccupancy() != size) {
		                            	System.out.println("해당 번호의 좌석은 " + size + "인석이 아닙니다.");
		                            	break;
		                            }
		                            
		                            // 정보 입력받기
//		                            System.out.print("이름: ");
//		                            String userName = sc.next();
		                            System.out.print("전화번호: ");
		                            String userNo = sc.next();
		
		                            // 입력받은 정보로 User 객체 만들고, 체크인하기
		                            admin.checkIn(userNo, roomNo);
	                            }
	                            // 사용 가능한 방이 하나도 없는 경우
	                            else {
	                            	System.out.println("사용 가능한 방이 없습니다.");
	                            }
	                            
	                            break; // finish 1. 사용자 > 1. 좌석 선택(체크인)
	                            
	                        case 2: // 1. 사용자 > 2. 이용완료(체크아웃)
	                        	// 체크아웃을 위한 정보 입력
	                            System.out.print("이름: ");
	                            String userName = sc.next();
	                            System.out.print("전화번호: ");
	                            String userNo = sc.next();
	
	                            // 입력받은 정보와 일치하는 사용자 찾아서 해당하는 room에서 checkOut() => 가격 출력
	                            System.out.println("이용 금액: " + admin.checkOut(userNo) + "원");
	                            
	                            break; // finish 1. 사용자 > 2. 이용 완료(체크아웃)
	                            
	                        case 3: // 1. 사용자 > 3. 종료
	                        	break;
	                        	
	                        default: // 1. 사용자 > 번호 잘못 입력
	                            System.out.println("잘못된 번호를 입력하였습니다. 1. 좌석 선택 || 2. 이용 완료를 입력해주세요.");
	                            break; // finish userMenu
	                        }
	                        break; // finish ** 사용자 **
	
	                    case 2: // ** 관리자 **
	                        System.out.println("관리자 모드입니다.");
	                        System.out.println("1. 방 청소 || 2. 방 전체 조회 || 3. 수익 조회 || 4. 방 생성 || 5. 방 삭제 || 6. 메인메뉴로");
	                        int adminMenu = sc.nextInt();
	                        
	                        switch (adminMenu) {
	                        case 1: // 2. 관리자 > 1. 방 청소
	                            System.out.println("방 청소 메뉴입니다.");
	                            System.out.println("1. 청소할 방 조회 || 2. 청소한 방 입력 || 3. 메인메뉴로");
	                            int cleanMenu = sc.nextInt();
	
	                            switch (cleanMenu) {
	                                case 1: // 2. 관리자 > 1. 방 청소 > 1. 청소해야할 방 조회
	                                    System.out.print("청소해야하는 방: ");
	                                    // 청소가 필요한 인덱스의 방은 true로 설정한 배열 반환
	                                    ArrayList<Boolean> roomsToClean = admin.findRoomsToClean();
	                                    int falseCount = 0;
	
	                                    int lastRoomNumber = admin.getLastRoomNumber();
	                                    for (int i = 0; i < lastRoomNumber; i++) {
	                                        if (!roomsToClean.get(i)) {
	                                        	falseCount++;
	                                        	continue;
	                                        }
	                                        else {
	                                            System.out.println(i + "번 방");
	                                        }
	                                    }
	                                    
	                                    if (falseCount == lastRoomNumber)
	                                    	System.out.println("null");
	                                    
	                                    break;
	
	                                case 2: // 2. 관리자 > 1. 방 청소 > 2. 청소한 방 입력
	                                    boolean moreInput = true;
	                                    while (moreInput) {
	                                        System.out.print("청소한 방의 번호를 입력하세요: ");
	                                        int roomNo = sc.nextInt();
	
	                                        admin.cleanRoom(roomNo);
	                                        
	                                        System.out.print("추가로 입력하시겠습니까?(y/Y or n/N): ");
	                                        String more = sc.next();
	                                        
	                                        if (more.equals("y") || more.equals("Y"))
	                                            moreInput = true;
	                                        else if (more.equals("n") || more.equals("N"))
	                                            moreInput = false;
	                                        else {
	                                            System.out.println("잘못된 값을 입력했습니다.");
	                                            moreInput = false;
	                                        }
	                                    }
	                                    break;
	                                case 3:  // 2. 관리자 > 1. 방 청소 > 3. 메인메뉴로
	                                	break;
	
	                                default: // 2. 관리자 > 1. 방 청소 > 잘못된 번호 입력
	                                    System.out.println("잘못된 번호를 입력하였습니다. 1. 청소할 방 조회 || 2. 청소한 방 입력 || 3. 메인메뉴로 중 하나를 입력해주세요.");
	                                    break;
	                            } // finish cleanMenu
	                            
	                            break; // finish 2. 관리자 > 1. 방청소
	                            
	                        case 2: // 2. 관리자 > 2. 방 전체 조회
	                            int lastRoomNumber = admin.getLastRoomNumber();
	                            for (int i = 0; i < lastRoomNumber; i++) {
	                                Room room = admin.getRoom(i);
	                                if(room != null) {
	                                    System.out.println("----room" + i + ": " + room.getRoomName() + "-----");
	                                    System.out.println("occupancy: " + room.getOccupancy());
	                                    System.out.println("ChargePerHour: " + room.getChargePerHour());
	                                    System.out.println("Surcharge: " + room.getSurcharge());
	                                    System.out.println("Empty: " + room.isEmpty());
	                                    System.out.println("available: " + room.available());
	                                    // checkInTime 출력
	                                    System.out.print("check-in time: ");
	                                    if (room.isEmpty())
	                                    	System.out.println("null");
	                                    else
	                                    	System.out.println(room.getCheckInTime());
	                                    // users 출력
	                                    System.out.print("User: ");
	                                    if (room.isEmpty())		// 방이 비어있는 경우
	                                    	System.out.println("null");
	                                    else {								// 방이 사용중인 경우
	                                		User currentUser = room.getUser();
//	                                    	String userName = currentUser.getUserName();
	                                        System.out.println(currentUser.getUserNo());	// user 출력
	                                    }
	                                }
	                                else continue;
	                            }
	                            break; // finish 2. 관리자 > 2. 방 전체 조회
	                            
	                        case 3: // 2. 관리자 > 3. 수익 조회
	                        	System.out.println("수익을 조회합니다.");
	                        	
	                        	System.out.println("1. 일별 수익 조회 || 2. 월별 수익 조회");
	                        	int incomeMenu = sc.nextInt();
	                        	
	                        	switch (incomeMenu) {
	                        	case 1: // 2. 관리자 > 3. 수익 조회 > 1. 일별 수익 조회
	                        		System.out.print("조회하려는 달을 입력하세요(1~12): ");
	                        		int month = sc.nextInt();
	                        		System.out.print("조회하려는 날짜를 입력하세요(1~31): ");
	                        		int day = sc.nextInt();
	                        		
	                        		System.out.println(month + "월 " + day + "일의 수익: " + admin.getDailyIncome(month - 1, day - 1));
	                        		break;
	                        		
	                        	case 2: // 2. 관리자 > 3. 수익 조회 > 2. 월별 수익 조회
	                        		System.out.print("조회하려는 달을 입력하세요(1~12): ");
	                        		month = sc.nextInt();
	                        		
	                        		System.out.println(month + "월의 수익: " + admin.getMonthlyIncome(month - 1) + "원");
	                        		break;
	                        		
	                    		default:
	                    			System.out.println("숫자를 잘못 입력하셨습니다.");
	                        	}
	                        	break; // finish 2. 관리자 > 3. 수익 조회
	                        	
	                        case 4: // 2. 관리자 > 4. 방 생성
	                            System.out.println("방을 생성합니다.");
	                    
	                            // 방을 생성하기 위한 매개변수를 입력받기
	                            System.out.print("방 이름: ");						// roomName(방 이름)
	                            String roomName = sc.next();
	                            
	                            System.out.print("정원(1이상의 숫자를 입력하세요): ");		// occupancy(정원)
	                            int occupancy = sc.nextInt();
	                            
	                            System.out.print("시간당 금액(0이상의 숫자를 입력하세요): ");// chargePerHour(1시간당 금액)
	                            int chargePerHour = sc.nextInt();
	                            
	                            System.out.print("추가금액(0이상의 숫자를 입력하세요): ");	// surcharge(10분당 추가금액)
	                            int surcharge = sc.nextInt();
	                            
	                            System.out.print("동일한 유형의 방을 몇개 만들 것인지 입력(0이상의 숫자를 입력하세요): ");	// howManySameRoom(방 개수)
	                            int howManySameRoom = sc.nextInt();
	                            
	                            // Admin클래스의 createRoom(), createRooms() 메소드 사용해서 해당하는 매개변수에 맞는 방(들) 생성
	                            if (howManySameRoom == 1) {
	                                admin.createRoom(roomName, occupancy, chargePerHour, surcharge);
	                            } else if (howManySameRoom > 1) {
	                                admin.createRooms(howManySameRoom, roomName, occupancy, chargePerHour, surcharge);
	                            }
	
	                            break; // finish 2. 관리자 > 4. 방 생성
	                        case 5: // 2. 관리자 > 5. 방 삭제
	                            System.out.println("룸을 삭제합니다.");
	                    
	                            System.out.print("삭제시킬 방의 번호를 입력하십시오: ");
	                            int deleteRoomNo = sc.nextInt();	// 삭제시킬 방 번호 입력받기
	                            
	                            admin.deleteRoom(deleteRoomNo);		// deleteRoomNo의 방번호를 가지는 방을 admin 객체의 room배열에서 삭제
	                           
	                            break;
	                        case 6: // 2. 관리자 > 6. 종료
	                        	break;
	                        default: // 2. 관리자 > 잘못된 번호 입력
	                            System.out.println("잘못된 번호를 입력하였습니다. 1. 방 청소 || 2. 방 전체 조회 || 3. 방 생성 || 4. 방 삭제 || 5. 종료를 입력해주세요."); 
	                            break;
	                        }  // finish adminMenu
	                        
	                        break;  // finish admin
	                    
	                    case 3: // ** 종료 **
	                        exit = true;
	                        System.out.println("스터디카페 키오스크 프로그램을 종료합니다.");
	                        break;
	
	                    default: // ** 잘못된 번호 입력 **
	                        System.out.println("잘못된 번호를 입력하였습니다. 1. 사용자 || 2. 관리자를 입력해주세요.");
	                        break;
	                } // finish switch(user || admin || exit)
	                
	    		} catch (InputMismatchException ime) {
		            System.out.println("정수값을 입력해주세요");
					sc = new Scanner(System.in); // 재초기화
		        } catch (Exception e) {
					System.out.println(e.getMessage());
		        }
	    	} // finish while (!exit)
	    	
	    	// inputStream close
	    	in3.close();
	    	in4.close();
        
//	        // 파일 출력(DataOutputStream)
//	        DataOutputStream out = new DataOutputStream(new FileOutputStream("./roomData.dat"));
//	        admin.writeRoomInfos(out);
//	        DataOutputStream out2 = new DataOutputStream(new FileOutputStream("./incomeData.dat"));
//	        admin.writeIncomeInfo(out2);
//	        out.close();
//	        out2.close();
        
    		// 파일 출력(ObjectOutputStream)
	        ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
	        admin.writeRoomInfos(out3);
	        ObjectOutputStream out4 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
	        admin.writeIncomeInfo(out4);
	        out3.close();
	        out4.close();
        
        
    	// finish I/O try
        } catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: File not found");
			System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
        	System.out.println("ERROR: I/O excption");
        	System.out.println(ioe.getMessage());
        } catch (Exception e) {
			System.out.println(e.getMessage());
        }        
        sc.close();
    } // finish main()
} // finish Studycafe UI class
