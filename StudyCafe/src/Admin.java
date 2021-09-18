
public class Admin {
	// 필드
	private int totalRoomCount = 100;
	private Room room[] = new Room[totalRoomCount];
	private int income[][] = new int[12][31];
	private int lastRoomNumber = 0;	// 방을 생성할 때 방 번호를 부여해주는 변수. 몇번까지 방이 만들어져있는지 알 수도 있다(가장 마지막 방번호)
	
	
	// 여기부터 메소드
	
	// 방을 생성하는 메소드 createRoom
	public void createRoom(String roomName, int occupancy, int chargePerHour, int surcharge) throws Exception {
		if((lastRoomNumber < totalRoomCount) && (occupancy > 0) && (chargePerHour >= 0) && (surcharge >= 0)) {
			room[lastRoomNumber++] = new Room(roomName, occupancy, chargePerHour, surcharge);
			// roomNumber를 1만큼 증가시켜 다음 방을 위한 준비(방이 추가되었음을 표시)
		} else throw new Exception("입력하신 방을 생성할 수 없습니다.");
		
	} // finish createRoom()
	
	
	// 여러 개의 방을 생성하는 메소드 createRooms
	public void createRooms(int howManySameRoom, String roomName, int occupancy, int chargePerHour, int surcharge) throws Exception {
		if (lastRoomNumber + howManySameRoom <= totalRoomCount) {
			for (int i = 0; i < howManySameRoom; i++) {
				createRoom(roomName, occupancy, chargePerHour, surcharge);
			}
		} else throw new Exception("입력하신 방을 생성할 수 없습니다.");
	} // finish createManyRoom()
	
	
	// 방 삭제 메소드 deleteRoom
	public void deleteRoom(int deleteRoomNo) throws Exception {
		// 지우려는 방 번호가 0 이상이고, lastRoomNumber(최대방번호)보다 작으면서,room[deleteRoomNo]가 존재하는지 검사
		if (deleteRoomNo >= 0 && deleteRoomNo < lastRoomNumber && room[deleteRoomNo] != null) { 
			room[deleteRoomNo] = null; 	// 만족한다면 room[deleteRoomNo]를 null로 바꿔 객체 삭제해주기
			
			for(int i = deleteRoomNo; room[i+1] != null; i++) { // 빈 방이 없도록 비운 방 다음 방들을 하나씩 당겨준다.
				room[i] = room[i+1];
			}
			lastRoomNumber--;	// 방의 총개수를 하나 줄인다.
		}
		else throw new Exception("해당하는 번호의 방이 존재하지 않습니다"); // 조건을 만족시키지 못하면 Exception 발생시키기
	}
	
	
	// 정원이 people인 방 중에서 사용 가능한 방을 출력하는 메소드
	public Room[] searchAvailableRoom(int size) throws Exception {
		Room availableRoom[] = new Room[lastRoomNumber]; // 반환할 배열로 availableRoom 배열 생성
		boolean roomExist = false;
		
		// 사용 가능한 방이 있는지 탐색
		for (int i = 0; i < lastRoomNumber; i++) {	// lastRoomNumber로 몇번 방까지 있는지 알 수 있기 때문에 lastRoomNumber만큼 반복
			Room currentRoom = room[i];	// i번째 room 객체를 currentRoom으로 대입해서 사용
			
			if ((currentRoom.getOccupancy() == size) && (currentRoom.available())) {	
				// 1. i번째 room객체에서 getOcuupancy로 반환받은 정원과 매개변수로 입력된 people이 일치하고, 
				// 2. i번째 room이 이용가능(깨끗하고, 비어있는지를 확인 후 boolean값 반환)하다면
				availableRoom[i] = currentRoom;	// i번째 방을 availableRoom[i]에 추가
				roomExist = true;
			}
		} // finish for loop
		
		if (!roomExist)
			throw new Exception("사용 가능한 방이 없습니다.");
		
		return availableRoom;	// 사용가능한 방을 저장해둔 availableRoom 배열 리턴
		
	} // finish searchAvailableRoom()
	
	public Room[] getRoom() {
		return room;
	}
	
	// checkIn & out
	public void checkIn(String userName, String userNo, int roomNo) throws Exception {
		room[roomNo].checkIn(userName, userNo, roomNo);
	}
	
	public int checkOut(String userName, String userNo) throws Exception {
		int roomNo = 0;
		boolean found = false;	// 조건에 맞는 roomNo가 있을 때 true로 변환

		// userName, userNo와 일치하는 user가 있는지 탐색
		for(int i = 0; i < lastRoomNumber; i++) {	
			if(room[i].available())	// user가 없는지(null) 확인
				continue;
			
			User user = room[i].getUser();
			if (user.getUserName().equals(userName) && user.getUserNo().equals(userNo)) { // userName과 userNo가 모두 일치하는 user가 있는지 검사
				roomNo = user.getRoomNo();	// roomNo에 currentUser의 roomNo를 대입
				found = true;
				break;
			}
		}
		
		if (found) {
			Room currentRoom = room[roomNo];
			
			currentRoom.checkOut();			// 체크아웃
			int pay = currentRoom.pay();	// 요금 계산
			addDailyIncome(currentRoom.getMonth() - 1, currentRoom.getDay() - 1, pay);	// 요금을 수익에 더하기
			currentRoom.resetCheckInTime();	// 체크인 시간 초기화
			return pay;
		} else throw new Exception("해당하는 조건에 맞는 user가 존재하지 않습니다.");
		
	}
	
	// 방청소 관련 메소드
	// 청소할 방 탐색 후 반환
	public Room[] findRoomsToClean() {
		Room roomsToClean[] = new Room[lastRoomNumber]; // 최대 lastRoomNumber크기의 배열 생성
		
		for (int i = 0; i < lastRoomNumber; i++) {
			Room currentRoom = room[i];			// i번째 room 객체를 currentRoom으로 대입해서 사용
			
			if (!currentRoom.isClean())			// i번째 방이 깨끗하지 않은지 검사(치워야 하는지 검사)
				roomsToClean[i] = currentRoom;	// 치워야 한다면 roomsToClean 배열에 추가
			else roomsToClean[i] = null;		// 아니라면 null로 처리
		}
		
		return roomsToClean;					// roomsToClean 배열 반환
	}
	
	
	// 방 청소 메소드
	public void cleanRoom(int roomNo) throws Exception {		// 방 번호를 이용해서 청소한 방 정하기
		if (room[roomNo] != null) {								// 방이 존재하는지 검사
			room[roomNo].cleanRoom();							// Room 클래스의 cleanRoom() 메소드 활용해서 roomNo번호에 해당하는 방 청소
		}
		else throw new Exception("해당하는 방이 존재하지 않습니다.");	// 방이 존재하지 않는다면 익셉션 발생시키기
	}
	
	
	// lastRoomNumber에 대한 getter 메소드
	public int getLastRoomNumber() {
		return lastRoomNumber;
	}
	
	
	// income관리 메소드
	public int getDailyIncome(int month, int day) throws Exception {
		if ((month >= 0 && month <= 11) && (day >= 0 && day <= 30)) {
			return income[month][day];	// 매개변수로 입력된 month월의 day일에 해당하는 수입을 조회
		} else throw new Exception("잘못된 값을 입력하였습니다.");
	}
	
	public void addDailyIncome(int month, int day, int money) {
		income[month][day] += money;
	}
	
	public int getMonthlyIncome(int month) throws Exception {
		if (month >= 0 && month <= 11) {
			int sum = 0;
			for (int i  = 0; i < 31; i++) {
				sum += income[month][i];
			}
			return sum;
		} else throw new Exception("잘못된 값을 입력하였습니다.");
	}

	
} // finish Admin
