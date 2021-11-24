import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.io.*;

public class Admin implements java.io.Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1044342505298863682L;
	// 필드
	private static ArrayList<Room> room = new ArrayList<Room>();
	private static ArrayList<Integer> income = new ArrayList<Integer>();
	
	Admin() {
		
	}
	
	
	// 생성자(DataInputStream ver.)
	Admin(DataInputStream dataIn, DataInputStream dataIn2) throws Exception {
		// 입력을 위한 변수 선언
		String roomName, userNo;
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
//					userName = dataIn.readUTF();
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
					room.get(room.size()-1).checkIn(userNo, userRoomNo, checkInDate);
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
	} // finish Admin()
	
	
	// 생성자(ObjectInputSteam ver.)
	Admin(ObjectInputStream objectIn, ObjectInputStream objectIn2) throws Exception {
		ArrayList<Room> rooms = (ArrayList<Room>) objectIn.readObject();
		ArrayList<Integer> incomeList = (ArrayList<Integer>) objectIn2.readObject();
		
		// roomData read
		int num = rooms.size();
		for (int i = 0; i < num; i++) {
			room.add(rooms.get(i));
		}
		
		// incomeData read
		num = incomeList.size();
		for (int i = 0; i < num; i++) {
			income.add(incomeList.get(i));
		}
		
	} // finish Admin()
	
	// 방을 생성하는 메소드 createRoom
	public void createRoom(String roomName, int occupancy, int chargePerHour, int surcharge) throws Exception {
		if((occupancy > 0) && (chargePerHour >= 0) && (surcharge >= 0)) {
			room.add(new Room(roomName,occupancy, chargePerHour, surcharge));
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
	public ArrayList<Boolean> searchAvailableRoom(int occupancy) throws Exception {

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
	public int checkIn(String userNo, int roomNo) throws Exception {
		int result = room.get(roomNo).checkIn(userNo, roomNo);
		return result;
	}
	
	
	// 해당 방의 함수 호출 시간의 이용금액 리턴 
	public int showCharge(String userNo) {
		int charge = 0;
		// userNo와 일치하는 user가 있는지 탐색
		int roomNo = room.indexOf(new Room(userNo));
		
		if (roomNo != -1) {
			Room currentRoom = room.get(roomNo);	// User가 있는 방을 찾아서 currentRoom으로
			charge = currentRoom.calcCharge();
			return charge;
		} else return -1;
	}
	
	    

	// checkOut()
	public int checkOut(String userNo) throws Exception, IndexOutOfBoundsException {		
	   	Room currentRoom = room.get(room.indexOf(new Room(userNo)));	// User가 있는 방을 찾아서 currentRoom으로
		
		return currentRoom.checkOut();
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
		// income이 없을 경우 익셉션 발생 방지
		else {
			for (int i = 0; i < 12*31; i++) {
				income.add(0);
			}
			income.set(dateCalc(month, day), money);
		}
	}
	
	public int subtractDailyIncome(int month, int day, int money) throws Exception {
		// income 배열이 존재할 경우
		if (!income.isEmpty()) {
			int date = dateCalc(month, day);
			// 빼는 금액이 원래 금액보다 큰지 검사
			if (income.get(date) - money >= 0) {
				income.set(date, income.get(date) - money);
				return 0;
			} else return -1;
		}
		// income이 없을 경우 익셉션 발생 방지
		else return -2;
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
	
	// 기간 입력 수익 조회
	public ArrayList<Integer> getSelectedIncome(int m1, int d1, int m2, int d2) throws Exception {
		
		ArrayList<Integer> incomeArr = new ArrayList<Integer>();

		if (!income.isEmpty()) {
			
			// m1/d1 ~ m2/d2
			if (m1 >= 1 && d1 >= 1 && m2 >= 1 && d2 >= 1) {
				int date1 = dateCalc(m1-1, d1-1);	// m1/d1
				int date2 = dateCalc(m2-1, d2-1);	// m2/d2
				return getIncomeArray(date1, date2);
				
			}
			// m1/d1 ~ 0/0	: m1/d1
			else if (m1 >= 1 && d1 >= 1 && m2 == 0 && d2 == 0) {
				int date1 = dateCalc(m1-1, d1-1);	// m1/d1
				int date2 = dateCalc(m1-1, d1-1);	// m1/d1
				return getIncomeArray(date1, date2);
			}
			// m1/d1 ~ 0/d2	: m1/d1 ~ m1/d2
			else if (m1 >= 1 && d1 >= 1 && m2 == 0 && d2 >= 1) {
				int date1 = dateCalc(m1-1, d1-1);	// m1/d1
				int date2 = dateCalc(m1-1, d2-1);	// m1/d2
				return getIncomeArray(date1, date2);

			}
			// m1/0 ~ m2/d2	: m1/1 ~ m2/d2
			else if (m1 >= 1 && d1 == 0 && m2 >= 1 && d2 >= 1) {
				int date1 = dateCalc(m1-1, 0);		// m1/1
				int date2 = dateCalc(m2-1, d2-1);	// m2/d2
				return getIncomeArray(date1, date2);

			}
			// m1/0 ~ m2/0	: m1/1 ~ m2/31
			else if (m1 >= 1 && d1 == 0 && m2 >= 1 && d2 == 0) {
				int date1 = dateCalc(m1-1, 0);	// m1/1
				int date2 = dateCalc(m2-1, 30);	// m2/31
				return getIncomeArray(date1, date2);

			}
			// m1/0 ~ 0/d2	: m1/1 ~ m1/d2
			else if (m1 >= 1 && d1 == 0 && m2 == 0 && d2 >= 1) {
				int date1 = dateCalc(m1-1, 0);		// m1/1
				int date2 = dateCalc(m1-1, d2-1);	// m1/d2
				return getIncomeArray(date1, date2);
			}
			// m1/0 ~ 0/0	: m1/1 ~ m1/31
			else if (m1 >= 1 && d1 == 0 && m2 == 0 && d2 == 0) {
				int date1 = dateCalc(m1-1, 0);	// m1/1
				int date2 = dateCalc(m1-1, 30);	// m1/31
				return getIncomeArray(date1, date2);

			} else {	 // 범위가 이 위에 이 많은 것들 중에도 없을 만큼 잘못 입력되었을 경우 -1 리턴
				incomeArr.add(-1);
				return incomeArr;
			}
		} else {	// income 배열이 없는 경우
			incomeArr.add(-2);
			return incomeArr;
		}
	}
	
	// 위 함수에서 중복되는 범위별 수익 배열 만들기를 함수로 구현
	public ArrayList<Integer> getIncomeArray(int date1, int date2) throws Exception {
		
			ArrayList<Integer> incomeArr = new ArrayList<Integer>();
			int sum = 0;
			
			if (date1 <= date2) {
				for (int i = date1; i <= date2; i++) {
					incomeArr.add(income.get(i));
					sum += income.get(i);
				}
				incomeArr.add(sum);
				return incomeArr;
			} else {
				incomeArr.add(-1);
				return incomeArr;
			}
	}

	
	// 0~11월, 0~30일의 수입 배열 인덱스 구하기
	public int dateCalc(int month, int day) throws Exception {
		if ((month >= 0 && month <= 11) && (day >= 0 && day <= 30)) {
			return (month * 31) + day;
		} else throw new Exception("잘못된 값을 입력하였습니다.");
	}
	
	public ArrayList<Integer> getIncome() {
		return income;
	}
	
	// room 데이터필드 DataOutputStream 출력
	void writeRoomInfos(DataOutputStream dataOut) throws Exception {
		for (int i = 0; i < room.size(); i++)
			room.get(i).writeRoomInfo(dataOut);
	}
	
	void writeIncomeInfo(DataOutputStream dataOut) throws Exception {
		for (int i = 0; i < income.size(); i++)
			dataOut.writeInt(income.get(i));
	}
	
	// room 데이터필드 ObjectOutputStream 출력
	void writeRoomInfos(ObjectOutputStream objectOut) throws Exception {
		objectOut.writeObject(room);
	}
	
	void writeIncomeInfo(ObjectOutputStream objectOut) throws Exception {
		objectOut.writeObject(income);
	}
} // finish Admin
