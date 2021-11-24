import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.*;



public class Room implements java.io.Serializable  {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4726908021408585013L;
	// 필드
	private String roomName;				// 방 이름
//	private int roomNo;						// 방 번호(추가됨)
	private int occupancy;					// 방의 정원(최대인원수)
	private int chargePerHour;				// 1시간당 요금
	private int surcharge;					// 10분당 추가요금
	private boolean clean;					// 방이 깨끗한 상태인지 확인
	private boolean empty;					// 비어있는지 여부
	private User user;						// User 객체
	private Calendar checkInDate;			// 입실 날짜
	
	
	// 메소드
	
	// 생성자
	public Room(String roomName, int occupancy, int chargePerHour, int surcharge){
		this.roomName = roomName;				// 방 이름 설정
//		this.roomNo = roomNo;					// 방 번호 설정
		this.occupancy = occupancy;				// 정원 설정
		this.chargePerHour = chargePerHour;		// 시간당 요금 설정
		this.surcharge = surcharge;				// 추가요금 설정
		empty = true;							// 비어있음
		clean = true;							// 기본값으로 clean을 0으로 설정
	}
	
	// user 검색을 위한 생성자
	public Room(String userNo) {
		user = new User(userNo);
		occupancy = 0;
		empty = false;
		clean = false;
	}

	
	// 체크인 메소드
	public int checkIn(String userNo, int roomNo) throws Exception {
		if (available()) {
			this.checkInDate = new GregorianCalendar();	// 체크인 시간 기록
			
			this.user = new User(userNo, roomNo);		// user객체 추가
			this.empty = false;
			this.clean = false;
			return 0;
		} else return -1;
	} // finish checkIn()
	
	// override
	public void checkIn(String userNo, int roomNo, Calendar checkInDate) throws Exception {
		if (empty) {
				this.checkInDate = checkInDate;		// 체크인 시간 기록
				
				user = new User(userNo, roomNo);	// user객체 추가
				empty = false;	// empty를 false로
		} else throw new Exception("이용 가능 정원을 초과했습니다.");	
	} // finish checkIn()
	
	// 이용요금 구하기
	public int calcCharge() {
		// checkOutTime 기록
		long checkOutTime = Calendar.getInstance().getTimeInMillis();

		// 사용시간 계산
		long checkInTime = checkInDate.getTimeInMillis();
		long totalMinute = checkOutTime - checkInTime;
		checkInTime = 0;							// 계산 후 0으로 초기화
		totalMinute = (totalMinute / (1000 * 60));	// 총사용시간 n분 계산
		int hour = ((int)totalMinute / 60) > 0 ? ((int)totalMinute / 60) : 1;
		int minute = (int)totalMinute % 60;
		
		// 요금 계산
		int charge = hour  * chargePerHour;
		if (totalMinute > 60) {
			charge += minute / 10 * surcharge;
		}
		return charge;
	}

	
	// 체크아웃 메소드
	public int checkOut() {
		if (!empty) {	// 체크아웃은 한 번만 시행
			// 필드 초기화
			this.empty = true;
			this.user = null;
			this.checkInDate = null;
			
			return 0;

		} else return -1;

	} // finish checkOut()
		
		
	// 체크인 월과 날짜, 시간 반환 메소드
	public int getMonth() {
		if (checkInDate != null) {
			int month = checkInDate.get(Calendar.MONTH) + 1;
			return month;
		} else return -1;
	}
	
	public int getDay() {
		if (checkInDate != null) {
		int day = checkInDate.get(Calendar.DATE);
		return day;
		} else return -1;
	}
	
	public String getCheckInTime() {
		if (checkInDate != null) {
			String date = "";
			date += Integer.toString(this.getMonth()) + "/";
			date += Integer.toString(this.getDay()) + " ";
			date += Integer.toString(checkInDate.get(Calendar.HOUR_OF_DAY)) + ":";
			date += Integer.toString(checkInDate.get(Calendar.MINUTE));
			
			return date;
		} else return "check-in 정보가 없습니다.";
		
	}
	
//	public void resetCheckInTime() {
//		this.checkInDate = null;
//	}

	
	// users배열에 대한 getter 메소드
	public User getUser() {
		return user;
	}
	
	
	// roomName에 대한 getter, setter 메소드
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
//	// roomNo에 대한 getter, setter
//	public int getRoomNo() {
//		return roomNo;
//	}
//	
//	public void setRoomNo(int roomNo) {
//		this.roomNo = roomNo;
//	}
	
	
	// occupancy에 대한 getter, setter 메소드
	public int getOccupancy() {
		return occupancy;
	}
	
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	
	
	// chargePerHour에 대한 getter, setter 메소드
	public int getChargePerHour() {
		return chargePerHour;
	}
	
	public void setChargePerHour(int chargePerHour) {
		this.chargePerHour = chargePerHour;
	}
	
	
	// surcharge에 대한 getter, setter 메소드
	public int getSurcharge() {
		return surcharge;
	}
	
	public void setSurcharge(int surcharge) {
		this.surcharge = surcharge;
	}
	
	
	// changeCharges 메소드: chargePerHour와 surcharge값을 동시에 변환
	public void changeCharges(int chargePerHour, int surcharge) {
		this.chargePerHour = chargePerHour;
		this.surcharge = surcharge;
	}

	
	// available 메소드
	public boolean available() {
		return empty && clean;	// 방이 비어있고, 깨끗하다면 true 리턴
	}
	
	/* empty getter, setter */
	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty(boolean b) {
		empty = b;
	}
	
	/* clean getter, setter */
	public boolean isClean() {
		return clean;	// clean값을 반환
	}
	
	// 방청소 메소드
	public void cleanRoom() {
		clean = true;	// clean을 true로 바꿔줌
	}
	
	public void setClean(boolean b) {
		clean = b;
	}
	
	// room 데이터필드 출력
	public void writeRoomInfo(DataOutputStream dataOut) throws Exception {
		/*
		 * [0]: String roomName
		 * [1]: int occupancy
		 * [2]: int chargePerHour
		 * [3]: int surcharge
		 */
		dataOut.writeUTF(roomName);
		dataOut.writeInt(occupancy);
		dataOut.writeInt(chargePerHour);
		dataOut.writeInt(surcharge);


		// [4]: boolean clean
		dataOut.writeBoolean(clean);
		
		// [5]: boolean empty
		dataOut.writeBoolean(empty);

		// 사용중이면 User 정보 출력
		if (!empty) {
			/*
			 * user 정보
			 * [6]: String user.getUserName()
			 * [7]: String user.getUserNo()
			 */
			dataOut.writeUTF(user.getUserNo());

			/*
			 * checkInDate(사용 시작 시간: GregorianCalendar 생성자를 위한 매개변수 받아오기)
			 * [8]: int year
			 * [9]: int month
			 * [10]: int dayOfMonth
			 * [11]: int hourOfDay
			 * [12]: int minute
			 */
			dataOut.writeInt(checkInDate.get(Calendar.YEAR));
			dataOut.writeInt(checkInDate.get(Calendar.MONTH));
			dataOut.writeInt(checkInDate.get(Calendar.DATE));
			dataOut.writeInt(checkInDate.get(Calendar.HOUR_OF_DAY));
			dataOut.writeInt(checkInDate.get(Calendar.MINUTE));
		}
	}
	
	@Override // User 비교
	public boolean equals(Object obj) {
		if (!(obj instanceof Room)) {
			return false;
		} 
		else {
			User user = ((Room) obj).getUser();
			if (user != null) {
				if (user.getUserNo().equals(this.user.getUserNo())) {
					return true;
				} else return false;
				
			} else return false;
		}
	}
	
} // finish room class
