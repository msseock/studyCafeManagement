import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.*;



public class Room {
	// 필드
	private User user;			// User 객체를 저장할 배열 생성. 크기 조정 가능하도록 처음에는 null로 설정
	private String roomName;				// 방 이름
	private int occupancy;					// 방의 정원(최대인원수)
	private int chargePerHour;				// 1시간당 요금
	private int surcharge;					// 10분당 추가요금
//	private int userCount;					// 방에 몇 명이 들어와있는지 확인용
	private boolean empty;					// 비어있는지 여부 반환 용도.
	private boolean clean;					// 방이 깨끗한 상태인지 확인용
	private long checkInTime;				// 입실 시간
	private Calendar checkInDate;			// 입실 날짜
//	private long checkOutTime;				// 퇴실 시간(2인 이상인 스터디룸에 이용하기 위해) (없앨것임)
//	private Calendar checkOutDate;			// 퇴실 날짜(이것도 없앨거임)
	
	
	// 메소드
	
	// 생성자
	public Room(String roomName, int occupancy, int chargePerHour, int surcharge){
		this.roomName = roomName;				// 방 이름 설정
		this.occupancy = occupancy;				// 정원 설정
		this.chargePerHour = chargePerHour;		// 시간당 요금 설정
		this.surcharge = surcharge;				// 추가요금 설정
//		userCount = 0;							// 기본값으로 userCount를 0으로 설정
		empty = true;
		clean = true;							// 기본값으로 clean을 0으로 설정
	}

	
	// 체크인 메소드
	public void checkIn(String userName, String userNo, int roomNo) throws Exception {
		if (empty) {
				checkInDate = new GregorianCalendar();		// 체크인 시간 기록
				checkInTime = checkInDate.getTimeInMillis();
				
				user = new User(userName, userNo, roomNo);	// user객체 추가
				empty = false;	// empty를 false로
		} else throw new Exception("이용 가능 정원을 초과했습니다.");	
	} // finish checkIn()
	
	
//	// (기존)체크아웃 메소드
//	public void checkOut() throws Exception {
//		if (userCount > 0) {	// 체크아웃은 한 번만 시행
//			// 필드 초기화
//			userCount = 0;
//			empty = true;
//			clean = false;		// 사용자가 사용했기 때문에 청소를 위해 clean을 false로 바꾼다
//			
//			// checkOutTime 기록
//			checkOutDate = Calendar.getInstance();
//			checkOutTime = checkOutDate.getTimeInMillis();
//			
//			// users 배열을 비워준다.
//			user = null;
//		} else throw new Exception("이미 체크아웃하였습니다.");
//	} // finish checkOut()
	
	

	// 체크아웃 메소드(수정: pay 과정까지 추가)
	public int checkOut() throws Exception {
		if (!empty) {	// 체크아웃은 한 번만 시행
			// 필드 초기화
			empty = true;
			clean = false;
			user = null;
			
			// checkOutTime 기록
			long checkOutTime = Calendar.getInstance().getTimeInMillis(); // 이것만 있으면 윗줄 두 줄은 필요없을 것 같다

			// 사용시간 계산
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

		} else throw new Exception("이미 체크아웃하였습니다.");

	} // finish checkOut()
		
		
	// 체크인 월과 날짜, 시간 반환 메소드
	public int getMonth() throws Exception {
		if (checkInDate != null) {
			int month = checkInDate.get(Calendar.MONTH) + 1;
			return month;
		} else throw new Exception("check-in 정보가 없습니다.");
	}
	
	public int getDay() throws Exception {
		if (checkInDate != null) {
		int day = checkInDate.get(Calendar.DATE);
		return day;
		} else throw new Exception("check-in 정보가 없습니다.");
	}
	
	public String getCheckInTime() throws Exception {
		if (checkInDate != null) {
			String date = "";
			date += Integer.toString(this.getMonth()) + "월 ";
			date += Integer.toString(this.getDay()) + "일 ";
			date += Integer.toString(checkInDate.get(Calendar.HOUR_OF_DAY)) + ":";
			date += Integer.toString(checkInDate.get(Calendar.MINUTE));
			
			return date;
		} else throw new Exception("check-in 정보가 없습니다.");
		
	}
	
	public void resetCheckInTime() {
		this.checkInDate = null;
	}
	
	
//	// pay 메소드(checkOut에 합병)
//	public int pay() {
//		long totalMinute = checkOutTime - checkInTime;
//		checkInTime = checkOutTime = 0;						// 계산 후 0으로 초기화
//		totalMinute = totalMinute / (1000 * 60);			// 총사용시간 n분 계산
//		
//		
//		int hour = ((int)totalMinute / 60) > 0 ? ((int)totalMinute / 60) : 1;	// 시간 계산(기본 1시간)
//		int minute = (int)totalMinute % 60;					// 몇분인지
//		
//		
//		int charge = 0;
//		charge += hour  * chargePerHour;			// 시간 단위 돈 계산
//		if (totalMinute > 60) {
//			charge += minute / 10 * surcharge;		// 10분당 추가 요금 계산
//		}
//		
//		
//		return charge;
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

	
//	// userCount에 대한 getter, setter 메소드(조정 메소드), add 메소드
//	public int getUserCount() {
//		return userCount;
//	}
//	
//	public void setUserCount(int userCount) throws Exception {
//		if (userCount < occupancy) {
//			this.userCount = userCount;
//		}
//		else throw new Exception("userCount가 정원을 초과했습니다.");
//	}
//	
//	public void addUserCount(int toAdd) throws Exception {
//		if (toAdd + userCount <= occupancy) {
//			userCount += toAdd;
//		} else throw new Exception("userCount가 정원을 초과했습니다.");
//	}
	
	// available 메소드
	public boolean available() {
		return empty && clean;	// 방이 비어있고, 깨끗하다면 true 리턴
	}
	
	// isEmpty()
	public boolean isEmpty() {
		return empty;
	}
	
	// isClean 메소드
	public boolean isClean() {
		return clean;	// clean값을 반환
	}
	
	// 방청소 메소드
	public void cleanRoom() {
		clean = true;	// clean을 true로 바꿔줌
	}
	
} // finish room class
