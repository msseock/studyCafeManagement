import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.io.*;

public class Admin {
	// 필드
	private ArrayList<Room> room = new ArrayList<Room>();
	private ArrayList<Integer> income = new ArrayList<Integer>();	
	
	// 생성자(for 입력)
	Admin(DataInputStream dataIn, DataInputStream dataIn2) throws Exception {
		// 입력을 위한 변수 선언
		String roomName, userName, userNo;
		int occupancy, chargePerHour, surcharge, userRoomNo;
		boolean empty;
		// roomData read
		try {
			while(true) {
				/*
				 * [0]: String roomName
				 * [1]: int occupancy
				 * [2]: int chargePerHour
				 * [3]: int surcharge
				 */
				roomName = dataIn.readUTF();
				occupancy = dataIn.readInt();
				chargePerHour = dataIn.readInt();
				surcharge = dataIn.readInt();
				
				room.add(new Room(roomName, occupancy, chargePerHour, surcharge));
				
				// [4]: boolean clean
				room.get(room.size()-1).setClean(dataIn.readBoolean());
				
				// [5]: boolean empty
				empty = dataIn.readBoolean();
				
				// 사용중이면 User 정보 입력
				if (!empty) {
					/*
					* user 정보
					* [6]: String user.getUserName()
					* [7]: String user.getUserNo()
					* [8]: int user.getRoomNo()
					*/
					userName = dataIn.readUTF();
					userNo = dataIn.readUTF();
					userRoomNo = dataIn.readInt();
					
					/*
					 * checkInDate(사용 시작 시간: GregorianCalendar 생성자를 위한 매개변수 받아오기)
					 * [9]: int year
					 * [10]: int month
					 * [11]: int dayOfMonth
					 * [12]: int hourOfDay
					 * [13]: int minute
					 */
					GregorianCalendar checkInDate = new GregorianCalendar(dataIn.readInt(), dataIn.readInt(), dataIn.readInt(), dataIn.readInt(), dataIn.readInt());
					
					// 입력받은 값으로 user 입력(체크인)
					room.get(room.size()-1).checkIn(userName, userNo, userRoomNo, checkInDate);
				}
			} // finish while
		} catch (EOFException eofe) {
			System.out.println("room data 입력 완료");
		}
		
		// incomeData read
		try {
			while(true) {
				income.add(dataIn2.readInt());
			}
		} catch (EOFException eofe) {
			System.out.println("income data 입력 완료");
		}
	}
	
	// 방을 생성하는 메소드 createRoom
	public void createRoom(String roomName, int occupancy, int chargePerHour, int surcharge) throws Exception {
		if((occupancy > 0) && (chargePerHour >= 0) && (surcharge >= 0)) {
			room.add(new Room(roomName, occupancy, chargePerHour, surcharge));
		} else throw new Exception("입력하신 방을 생성할 수 없습니다.");
		
	}	
	
	
	// 여러 개의 방을 생성하는 메소드 createRooms(수정)
	public void createRooms(int howManySameRoom, String roomName, int occupancy, int chargePerHour, int surcharge) throws Exception {
		for (int i = 0; i < howManySameRoom; i++) {
			createRoom(roomName, occupancy, chargePerHour, surcharge);
		}
	}
	
	
	// 방 삭제 메소드
	public void deleteRoom(int deleteRoomNo) throws IndexOutOfBoundsException {
		room.remove(deleteRoomNo);
	}
	

    // 정원이 people인 방 중에서 사용 가능한 방을 출력하는 메소드(수정)
	public ArrayList<Boolean> searchAvailableRoom(int occupancy) throws Exception { // 기존
//	public ArrayList<Integer> searchAvailableRoom(int occupancy) throws Exception {	// 수정

//		/* 수정 */
//		ArrayList<Integer> available = new ArrayList<Integer>();
//		Room roomToFind = new Room("", occupancy, 0, 0);
//		int lastIndex = room.lastIndexOf(roomToFind), index;
//		ArrayList<Room> subRoom;
//		for (int i = 0; i < lastIndex; i = index + 1) {
//			subRoom = (ArrayList<Room>)room.subList(i, lastIndex+1);
//			index = subRoom.indexOf(roomToFind);
//			available.add(index);
//		}
//		return available;
		
		/* 기존 */
		ArrayList<Boolean> available = new ArrayList<Boolean>();
		// 사용 가능한 방이 있는지 탐색
		for(Room currentRoom: room) {			
            // 1. room객체에서 getOcuupancy로 반환받은 정원과 매개변수로 입력된 people이 일치하고
            // 2. room이 이용가능(깨끗하고, 비어있는지를 확인 후 boolean값 반환)하다면
			if ((currentRoom.getOccupancy() == occupancy) && (currentRoom.available())) {	
				available.add(true);
			}
			else available.add(false);
		}
		
		if(available.isEmpty())
		    throw new Exception("사용 가능한 방이 없습니다.");
		
		return available;
		
	} // finish searchAvailableRoom()
	

    // room ArrayList 배열 전체 리턴
	public ArrayList<Room> getRooms() {
		return room;
	}

	
	// roomNumber인덱스의 room 객체 리턴
	public Room getRoom(int roomNumber) throws IndexOutOfBoundsException {
		return room.get(roomNumber);
		
	}
	
	// checkIn & out
	public void checkIn(String userName, String userNo, int roomNo) throws Exception {
		room.get(roomNo).checkIn(userName, userNo, roomNo);
	}
	
	    

	// checkOut()(수정)
	public int checkOut(String userName, String userNo) throws Exception, IndexOutOfBoundsException {
		int pay = 0;
		
		/* 수정 */

		// userName, userNo와 일치하는 user가 있는지 탐색
		int roomNo = room.indexOf(new Room(userName, userNo));
		
		if (roomNo != -1) {
	   	Room currentRoom = room.get(roomNo);	// User가 있는 방을 찾아서 currentRoom으로
	   	
	   	pay = currentRoom.checkOut();			// 체크아웃
		addDailyIncome(currentRoom.getMonth() - 1, currentRoom.getDay() - 1, pay);	// 요금을 수익에 더하기
		currentRoom.resetCheckInTime();
		
		return pay;
	 	} else throw new Exception("해당하는 조건에 맞는 user가 존재하지 않습니다.");
		
//		/* 기존 */
//		// userName, userNo와 일치하는 user가 있는지 탐색
//		boolean found = false;	// 조건에 맞는 roomNo가 있을 때 true로 변환
//		for(int i = 0; i < room.size(); i++) {
//			if(room.get(i).isEmpty())	// user가 null이면 continue
//				continue;
//			
//			// room.indexOf(userName, userNo);
//			User user = room.get(i).getUser();
//			if (user.getUserName().equals(userName) && user.getUserNo().equals(userNo)) { // userName과 userNo가 모두 일치하는 user가 있는지 검사
//				found = true;
//				Room currentRoom = room.get(i);
//			
//				pay = currentRoom.checkOut();			// 체크아웃
//				addDailyIncome(currentRoom.getMonth() - 1, currentRoom.getDay() - 1, pay);	// 요금을 수익에 더하기
//				currentRoom.resetCheckInTime();	// 체크인 시간 초기화
//				break;
//			}
//		}
//		if (found) return pay;
//		else throw new Exception("해당하는 조건에 맞는 user가 존재하지 않습니다.");
		
	} // finish checkOut()

	
	
	// findRoomsToClean()(수정)
	public ArrayList<Boolean> findRoomsToClean() {
		ArrayList<Boolean> dirtyRoom = new ArrayList<Boolean>();
		
		for (Room currentRoom: room) {
			if (!currentRoom.isClean())			// 방이 깨끗하지 않은지 검사(치워야 하는지 검사)
				dirtyRoom.add(true);			// 치워야 한다면 true로 set
			else dirtyRoom.add(false);			// 아니라면 false로 set
		}
		
		return dirtyRoom;						// roomsToClean 배열 반환
	}
	
	
	// 방 청소 메소드(수정)
	public void cleanRoom(int roomNo) throws IndexOutOfBoundsException {
		room.get(roomNo).cleanRoom();
	}
	
	// lastRoomNumber에 대한 getter 메소드
	public int getLastRoomNumber() {
		return room.size();
	}
	
	
	// income관리 메소드
	public int getDailyIncome(int month, int day) throws Exception, IndexOutOfBoundsException {
		if (!income.isEmpty()) {
			return income.get(dateCalc(month, day));	// 수정
		}
		else throw new Exception("수입이 없습니다.");
	}
	
	public void addDailyIncome(int month, int day, int money) throws Exception, IndexOutOfBoundsException {
		// income 배열이 존재할 경우
		if (!income.isEmpty()) {
			int date = dateCalc(month, day);
			income.set(date, income.get(date) + money);
		}
		// income이 없을 경우 익셉션 발생 방지를 위해
		else {
			for (int i = 0; i < 12*31; i++) {
				income.add(0);
			}
			income.set(dateCalc(month, day), money);
		}
	}
	
	public int getMonthlyIncome(int month) throws Exception, IndexOutOfBoundsException {
		if (!income.isEmpty()) {
			int sum = 0;
			for (int i = 0; i < 31; i++) {
				sum += income.get((month * 31) + i);
			}
			return sum;
		} else throw new Exception("수입이 없습니다.");
	}
	
	
	public int dateCalc(int month, int day) throws Exception {
		if ((month >= 0 && month <= 11) && (day >= 0 && day <= 30)) {
			return (month * 31) + day;
		} else throw new Exception("잘못된 값을 입력하였습니다.");
	}
	
	// room 데이터필드 출력, DataOutputStream은 UI에서 생성 후 넘어옴
	void writeRoomInfos(DataOutputStream dataOut) throws Exception {
		for (int i = 0; i < room.size(); i++)
			room.get(i).writeRoomInfo(dataOut);
	}
	
	void writeIncomeInfo(DataOutputStream dataOut) throws Exception {
		for (int i = 0; i < income.size(); i++)
			dataOut.writeInt(income.get(i));
	}
	
} // finish Admin
