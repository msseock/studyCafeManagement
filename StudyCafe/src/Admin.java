import java.util.ArrayList;
import java.io.*;

public class Admin {
	// 필드
	private ArrayList<Room> room = new ArrayList<Room>();
	private ArrayList<Integer> income = new ArrayList<Integer>(12*31);
	
	// 생성자
	Admin(DataInputStream dataIn) throws Exception, EOFException {
		int roomCount = 0;
		while(true) {
			// [0]:int roomName.length -> char[] roomName
//			DataInputStream in = new DataInputStream (input);
			int length = dataIn.readInt();
			char[] charArray = new char[length];
			for (int i = 0; i < length; i++) {
				charArray[i] = dataIn.readChar();
			}
			String roomName = new String(charArray);
			
			// [1]: int occupancy
			int occupancy = dataIn.readInt();
			
			// [2]: int chargePerHour
			int chargePerHour = dataIn.readInt();
			
			// [3]: int surcharge
			int surcharge = dataIn.readInt();
			
			room.add(roomCount, new Room(roomName, occupancy, chargePerHour, surcharge));
			
			// [4]: boolean empty
			boolean empty = dataIn.readBoolean();
			room.get(roomCount).setEmpty(empty);
			// [5]: boolean clean
			boolean clean = dataIn.readBoolean();
			room.get(roomCount).setEmpty(clean);
			
			
			/* !empty일때 실행시킬 것들 */
			if (!empty) {
				/*
				* user 정보
				* [6]: int user.getUserName().length -> char[] user.getUserName()
				* [7]: int user.getUserNo().length -> char[] user.getUserNo()
				* [8]: int user.getRoomNo()
				*/
				
				
				/*
				 * checkInDate(사용 시작 시간: GregorianCalendar 생성자를 위한 매개변수 받아오기)
				 * [9]: int year
				 * [10]: int month
				 * [11]: int dayOfMonth
				 * [12]: int hourOfDay
				 * [13]: int minute
				 */
			}
			
			
		}
	}
	
	// 여기부터 메소드
	
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
	
	
	// deleteRoom()은 아예 처음부터 다시 만들자
	public void deleteRoom(int deleteRoomNo) throws IndexOutOfBoundsException {
		room.remove(deleteRoomNo);
	}
	

    // 정원이 people인 방 중에서 사용 가능한 방을 출력하는 메소드(수정)
	public ArrayList<Boolean> searchAvailableRoom(int size) throws Exception {
		ArrayList<Boolean> available = new ArrayList<Boolean>();
		
		// 사용 가능한 방이 있는지 탐색
		for(int i = 0; i < room.size(); i++) {
			Room currentRoom = room.get(i);	// i번째 room 객체를 currentRoom으로 대입해서 사용
			
            // 1. i번째 room객체에서 getOcuupancy로 반환받은 정원과 매개변수로 입력된 people이 일치하고, 
            // 2. i번째 room이 이용가능(깨끗하고, 비어있는지를 확인 후 boolean값 반환)하다면
			if ((currentRoom.getOccupancy() == size) && (currentRoom.available())) {	
				available.add(i, true);
			}
			else available.add(i, false);
		} // finish for loop
		
		if(available.isEmpty())
		    throw new Exception("사용 가능한 방이 없습니다.");
		
		return available;
		
	} // finish searchAvailableRoom()
	

    // getRoom()(getRooms로 변경)
	public ArrayList<Room> getRooms() {
		return room;
	}

	
	// Room 번호를 입력하면 Room 배열의 번호 번째 객체 반환하는 오버라이딩 함수도 만들어놔야겠다 그럼 UI에서 좀더 편해짐
	public Room getRoom(int roomNumber) throws IndexOutOfBoundsException {
		return room.get(roomNumber);
		
	}
	
	// checkIn & out
	public void checkIn(String userName, String userNo, int roomNo) throws Exception {
//		room[roomNo].checkIn(userName, userNo, roomNo);		// 기존
		room.get(roomNo).checkIn(userName, userNo, roomNo);	// 변경
	}
		    

	// checkOut()(수정)
	public int checkOut(String userName, String userNo) throws Exception, IndexOutOfBoundsException {
		boolean found = false;	// 조건에 맞는 roomNo가 있을 때 true로 변환
		int pay = 0;
		
		// userName, userNo와 일치하는 user가 있는지 탐색
		for(int i = 0; i < room.size(); i++) {
			if(room.get(i).available())	// user가 없는지(null) 확인
				continue;
			
			User user = room.get(i).getUser();
			if (user.getUserName().equals(userName) && user.getUserNo().equals(userNo)) { // userName과 userNo가 모두 일치하는 user가 있는지 검사
				found = true;
				Room currentRoom = room.get(i);
			
				pay = currentRoom.checkOut();			// 체크아웃
				addDailyIncome(currentRoom.getMonth() - 1, currentRoom.getDay() - 1, pay);	// 요금을 수익에 더하기
				currentRoom.resetCheckInTime();	// 체크인 시간 초기화
				break;
			}
		}
		
		if (found) return pay;
		else throw new Exception("해당하는 조건에 맞는 user가 존재하지 않습니다.");
	} // finish checkOut()

	
	/* 방청소 관련 메소드 */
	
	// 청소할 방 탐색 후 반환
	public ArrayList<Boolean> findRoomsToClean() {
		ArrayList<Boolean> dirtyRoom = new ArrayList<Boolean>();
		
		for (int i = 0; i < room.size(); i++) {
			if (!room.get(i).isClean())			// i번째 방이 깨끗하지 않은지 검사(치워야 하는지 검사)
				dirtyRoom.add(i, true);			// 치워야 한다면 dirtyRoom의 i번째 true로 set
			else dirtyRoom.add(i, false);		// 아니라면 null로 처리
		}
		
		return dirtyRoom;						// roomsToClean 배열 반환
	}
	
	// 방 청소 메소드(수정)
	public void cleanRoom(int roomNo) throws IndexOutOfBoundsException {
		room.get(roomNo).setClean(true);
	}
	
	// lastRoomNumber에 대한 getter 메소드
	public int getLastRoomNumber() {
		return room.size();
	}
	
	
	/* income관리 메소드 */
	
	// month월 day일의 income ArrayList index 반환용
	public int dateCalc(int month, int day) throws Exception {
		if ((month >= 0 && month <= 11) && (day >= 0 && day <= 30)) {
			return (month * 31) + day;
		} else throw new Exception("잘못된 값을 입력하였습니다.");
	}
	
	
	// month월 day의 수입 반환
	public int getDailyIncome(int month, int day) throws Exception, IndexOutOfBoundsException {
		if (!income.isEmpty()) {
			return income.get(dateCalc(month, day));	// 수정
		}
		else throw new Exception("수입이 없습니다.");
	}
	
	// month월 day일의 수입에 money를 더함
	public void addDailyIncome(int month, int day, int money) throws Exception, IndexOutOfBoundsException {
		// income 배열이 존재할 경우
		if (!income.isEmpty()) {
			int date = dateCalc(month, day);
			income.set(date, income.get(date) + money);
		}
		// income이 없을 경우 익셉션 발생 방지를 위해, 초기 1번만 수행
		else {
			for (int i = 0; i < 12*31; i++) {
				income.add(0);
			}
			income.set(dateCalc(month, day), money);
		}
	}
	
	// month월의 수입을 모두 더해 반환
	public int getMonthlyIncome(int month) throws Exception, IndexOutOfBoundsException {
		if (!income.isEmpty()) {
			int sum = 0;
			for (int i = 0; i < 31; i++) {
				sum += income.get((month * 31) + i);
			}
			return sum;
		}
		else throw new Exception("수입이 없습니다.");
	}
	
	
} // finish Admin
