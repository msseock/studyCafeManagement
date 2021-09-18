// Studycafe UI class
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    public static void main(String[] args) {
        Admin admin = new Admin(); // 카페의 전체적 관리를 위한 Admin 객체 admin 생성
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        
        
        System.out.println("Welcome!");
        
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
                            boolean moreCheckIn = false;

                            // 가능한 좌석 출력
                            Room availableRoom[] = admin.searchAvailableRoom(size);	// Admin 클래스의 searchAvailableRoom 메소드 사용해서 반환받은 Room 배열을 availableRoom[]에 대입
                           
                            for (int i = 0; i < admin.getLastRoomNumber(); i++) {                       // 마지막 방번호인 assignNumber까지 반복
                                Room currentAvailableRoom = availableRoom[i];
                                if (currentAvailableRoom == null) {
                                    continue;															// currentAvailableRoom이 비어있다면 다음 반복으로 continue
                                }
                                else {																	// currentAvailableRoom이 존재한다면
                                    System.out.println(i + "번: " + currentAvailableRoom.getRoomName());// 방 이름과 번호 출력
                                    System.out.println("시간당 요금: " + currentAvailableRoom.getChargePerHour());
                                    System.out.println("10분당 추가요금: " + currentAvailableRoom.getSurcharge());
                                    System.out.println();
                                }
                            }
                            System.out.println("사용 가능합니다.");

                            // 좌석 선택
                            System.out.print("좌석 번호를 선택해주세요: ");
                            int roomNo = sc.nextInt();
                            
                            // 사용자가 입력한 size가 좌석 번호의 size(occupancy)와 불일치할 때 case문 빠져나가기
                            if (admin.getRoom()[roomNo] == null) {
                            	System.out.println(roomNo + "번 좌석은 없습니다.");
                            	break;
                            }
                            else if (admin.getRoom()[roomNo].getOccupancy() != size) {
                            	System.out.println("해당 번호의 좌석은 " + size + "인석이 아닙니다.");
                            	break;
                            }
                            
                            // 2인용 좌석 이상이면 체크인 반복 용이하도록
                            do {
                                // 정보 입력받기
                                // 이름 입력받기
                                System.out.print("이름: ");
                                String userName = sc.next();

                                // 전화번호(사용자번호) 입력받기
                                System.out.print("전화번호: ");
                                String userNo = sc.next();

                                // 입력받은 정보로 User 객체 만들고, 체크인하기
                                admin.checkIn(userName, userNo, roomNo);
                                
                                // 추가인원 있는지 검사(while문, 없으면 조건 false로 바꾸기)
                                if (size > 2) {
                                	System.out.print("추가 인원이 있습니까?(y/Y 또는 아무거나): ");
                                    String more = sc.next();
                                    if (more.equals("y") || more.equals("Y")) moreCheckIn = true;
                                    else moreCheckIn = false;
                                }
                            } while (size > 2 && moreCheckIn);
                            
                            break; // finish 1. 사용자 > 1. 좌석 선택(체크인)
                            
                        case 2: // 1. 사용자 > 2. 이용완료(체크아웃)
                            // 이름 입력
                            System.out.print("이름: ");
                            String userName = sc.next();
                            
                            // 전화번호 입력
                            System.out.print("전화번호: ");
                            String userNo = sc.next();

                            // 입력받은 정보와 일치하는 사용자 찾아서 해당하는 room에서 checkOut() => 가격 출력
                            System.out.println("이용 금액: " + admin.checkOut(userName, userNo) + "원");
                            
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
                                    Room roomsToClean[] = admin.findRoomsToClean();
                                    
                                    // roomsToClean이 비어있는지 확인
                                    boolean noRoomsToclean = true;
                                    for (int i = 0; i < roomsToClean.length; i++) {
                                    	if (roomsToClean[i] != null) noRoomsToclean = false;
                                    }
                                    
                                    // dirtyRooms 상태 따라서 출력
                                    if (noRoomsToclean) {
                                        System.out.println("null");
                                    }
                                    else {
                                        int lastRoomNumber = admin.getLastRoomNumber();
                                        for (int i = 0; i < lastRoomNumber; i++) {
                                            if (roomsToClean[i] == null) continue;
                                            else {
                                                System.out.println(roomsToClean[i].getOccupancy() + "인석 " + i + "번 방");
                                            }
                                        }
                                    }
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
                            
                        case 2: // 2. 관리자 > 2. 방 전체 상태 조회
                            int lastRoomNumber = admin.getLastRoomNumber();
                            Room roomArray[] = admin.getRoom();
                            for (int i = 0; i < lastRoomNumber; i++) {
                                Room room = roomArray[i];
                                if(room != null) {
                                    System.out.println("----room" + i + ": " + room.getRoomName() + "-----");
                                    System.out.println("occupancy: " + room.getOccupancy());
                                    System.out.println("ChargePerHour: " + room.getChargePerHour());
                                    System.out.println("Surcharge: " + room.getSurcharge());
                                    System.out.println("UserCount: " + room.getUserCount());
                                    System.out.println("available: " + room.available());
                                    // checkInTime 출력
                                    System.out.print("check-in time: ");
                                    if (room.getUserCount() == 0)		// 방이 비어있는 경우
                                    	System.out.println("null");
                                    else								// 방이 사용중인 경우
                                    	System.out.println(room.getCheckInTime());
                                    // users 출력
                                    System.out.print("User: ");
                                    if (room.getUserCount() == 0)		// 방이 비어있는 경우
                                    	System.out.println("null");
                                    else {								// 방이 사용중인 경우
                                		User currentUser = room.getUser();
                                    	String userName = currentUser.getUserName();
                                    	String userNo = currentUser.getUserNo();
                                        System.out.println(userName + ", " + userNo);	// user 출력
                                    }
                                }
                                else continue;
                            }
                            break; // finish 2. 관리자 > 2. 방 전체 상태 조회
                            
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
                        		
                        		System.out.println(month + "월의 수익: " + admin.getMonthlyIncome(month - 1));
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
                        break;

                    default: // ** 잘못된 번호 입력 **
                        System.out.println("잘못된 번호를 입력하였습니다. 1. 사용자 || 2. 관리자를 입력해주세요.");
                        break;
                } // finish switch(user || admin || exit)
            
	        } catch (InputMismatchException e) {
	            System.out.println("정수값을 입력해주세요");
				sc = new Scanner(System.in); // 재초기화
	        } catch (Exception e) {
				System.out.println(e.getMessage());
	        }
        } // finish while
        
        sc.close();
    } // finish main()
} // finish Studycafe UI class
