
public class User implements java.io.Serializable  {	
	// 필드
//	private String userName;	// 사용자 이름. 입실 시 사용자 이름 입력받아서 사용
	private String userNo;		// 사용자 번호. 사용자의 전화번호 입력받아서 사용
	private int roomNo;			// 사용자가 사용하는 방. room 배열의 index로 설정한다.

	// 메소드
	
	// 생성자
	User(String userNo, int roomNo){
//		this.userName = userName;	// 사용자 이름 설정
		this.userNo = userNo;		// 사용자 번호(전화번호) 설정
		this.roomNo = roomNo;		// 사용하는 방 설정
	}
	
	User(String userNo) {
//		this.userName = userName;
		this.userNo = userNo;
	}
	
//	// userName에 대한 getter, setter 메소드
//	public String getUserName() {
//		return userName;
//	}
//	
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
	
	// userNo에 대한 getter, setter 메소드
	public String getUserNo() {
		return userNo;
	}
	
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	// roomNo에 대한 getter, setter 메소드
	public int getRoomNo() {
		return roomNo;
	}
	
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
}
