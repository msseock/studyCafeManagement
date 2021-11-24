import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class ManageRoom extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5939998089004640781L;
	
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnManageRoom; 
	private JButton btnCheckIncome;
	private JButton btnRefund;
//	private JButton btnBackToMain;
	
	private JTable roomTable;
	private JButton btnClean;
	private JButton btnCreateRoom;
	private JButton btnDeleteRoom;
	private JButton btnModifyRoom;
	private JButton btnComplete;
	private JButton btnSave;
	private Admin admin = new Admin();

	
	
	ManageRoom() { // 이 생성자에서 admin 객체 넘겨받을 수 있을까?
		this.setTitle("방 관리");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		Container contentPane = this.getContentPane();
		
		
		
		// Border WEST: 메뉴 이동 버튼 생성
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(7, 1));
		westPanel.setBackground(Color.white);
		
		btnManageRoom = new JButton("방 관리");
		btnManageRoom.setBackground(new Color(142, 142, 147));
		btnManageRoom.setForeground(Color.white);
		btnManageRoom.setOpaque(true);
		btnManageRoom.setBorderPainted(false);
		btnManageRoom.addActionListener(this);
		
		btnCheckIncome = new JButton("수익 확인");
		btnCheckIncome.setBackground(Color.white);
		btnCheckIncome.setOpaque(true);
		btnCheckIncome.setBorderPainted(false);
		btnCheckIncome.addActionListener(this);
		
		btnRefund = new JButton("환불");
		btnRefund.setBackground(Color.white);
		btnRefund.setOpaque(true);
		btnRefund.setBorderPainted(false);
		btnRefund.addActionListener(this);
		
//		btnBackToMain = new JButton("메인 메뉴로");
//		btnBackToMain.addActionListener(this);
		
		westPanel.add(btnManageRoom);
		westPanel.add(btnCheckIncome);
		westPanel.add(btnRefund);
//		westPanel.add(btnBackToMain);
		

		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("사용가능한 방들 중 원하는 방을 골라 클릭 후 선택 버튼을 클릭해주세요."));
		
		
		// Border CENTER: 방 전체 상태 조회 테이블 생성
		// header
		String colNames[] = { "번호", "이름", "정원", "시간당 요금", "10분당 추가요금", "사용중", "청소", "입실시간", "사용자" };
		DefaultTableModel roomDataModel = new DefaultTableModel(colNames, 0);
		
		// 방 정보 받아오기
		int i = 0;
		for (Room room:admin.getRooms()) {
			Object roomData[];
			if(room.isEmpty()) {	// 비어있을 때
				roomData = new Object[] { i, room.getRoomName(), room.getOccupancy(), // 번호, 이름, 정원
						room.getChargePerHour(), room.getSurcharge(), "X",	// 시간당 요금, 10분당 추가요금, 사용중
						(room.isClean() ? "O" : "X"), "-", "-" };			// 청소, 입실시간, 사용자
			} else {				// 사용중일 때	
				roomData = new Object[] { i, room.getRoomName(), room.getOccupancy(), // 번호, 이름, 정원
						room.getChargePerHour(), room.getSurcharge(), "O",			// 시간당 요금, 10분당 추가요금, 사용중
						"X", room.getCheckInTime(), room.getUser().getUserNo() };	// 청소(사용자 있으면 'X', 입실시간, 사용자
			}
			roomDataModel.addRow(roomData);
			i++;
		}
		roomTable = new JTable(roomDataModel);     
		
		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnClean = new JButton("청소");
		btnClean.addActionListener(this);
		btnCreateRoom = new JButton("생성");
		btnCreateRoom.addActionListener(this);
		btnDeleteRoom = new JButton("삭제");
		btnDeleteRoom.addActionListener(this);
		btnModifyRoom = new JButton("수정");
		btnModifyRoom.addActionListener(this);
		
		btnSave = new JButton("저장");
		btnSave.addActionListener(this);
		btnComplete = new JButton("완료");
		btnComplete.addActionListener(this);
		

		
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.gridheight = 1;
		southPanel.add(btnClean, gbc);
		
		gbc.gridx = 1; gbc.gridy = 0;
		southPanel.add(btnCreateRoom, gbc);
		
		gbc.gridx = 2; gbc.gridy = 0;
		southPanel.add(btnDeleteRoom, gbc);
		
		gbc.gridx = 3; gbc.gridy = 0;
		southPanel.add(btnModifyRoom, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1;gbc.gridwidth = 2;
		southPanel.add(btnSave, gbc);
		
		gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 2;
		southPanel.add(btnComplete, gbc);
		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(westPanel, BorderLayout.WEST);
		contentPane.add(mainPanel, BorderLayout.CENTER);

		
		this.pack();
		this.setVisible(true);
	}

	// 이벤트 처리 함수
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		// 청소 버튼 클릭 시
		if (obj == btnManageRoom) {
			new ManageRoom();
			this.dispose();

			
		} else if (obj == btnCheckIncome) {
			System.out.println("수익 확인 버튼 이벤트 실행");
			
			new CheckIncome();
			this.dispose();
			
		} else if (obj == btnRefund) {
			System.out.println("환불");
			
			new Refund();
			this.dispose();
			
		} else if (obj == btnClean) {
			System.out.println("청소 버튼 이벤트 실행");
			
			int row = roomTable.getSelectedRow();
			
			if (row != -1) {
				// backEnd
				ArrayList<Room> room = admin.getRooms();
				room.get(row).cleanRoom();
				
				DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
				model.setValueAt("O", row, 6);
				
				new DefaultAlert("청소 완료", row + " 번 방이 청소되었습니다.", "확인");
			} else {
				new DefaultAlert("선택 오류", "청소할 방을 선택해주세요", "확인");
			}
			
			

		// 생성 버튼 클릭 시
		} else if (obj == btnCreateRoom) {
			System.out.println("생성 버튼 이벤트 실행");
			
			new CreateRoom();			
			this.dispose();

		// 삭제 버튼 클릭 시
		} else if (obj == btnDeleteRoom) {
			System.out.println("삭제 버튼 이벤트 실행");
			
			int row = roomTable.getSelectedRow();
			if (row != -1) {
				admin.deleteRoom(row);
				DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
				model.removeRow(row);
				
				new DefaultAlert("삭제 완료", row + "번째 방이 삭제되었습니다.", "확인");
			} else {
				new DefaultAlert("삭제 오류", "삭제할 방을 선택해주세요", "확인");
			}
			
		// 수정 버튼 클릭 시
		} else if (obj == btnModifyRoom) {
			System.out.println("수정 버튼 이벤트 실행");
			
			int row = roomTable.getSelectedRow();
			
			if (row != -1) {
				Room room = admin.getRoom(row);
				new ModifyRoom(row, room.getRoomName(), room.getOccupancy(), room.getChargePerHour(), room.getSurcharge());
				this.dispose();
			} else {
				new DefaultAlert("수정 오류", "수정할 방을 선택해주세요", "확인");
			}
			

		// 완료 버튼 클릭 시
		} else if (obj == btnComplete) { // 완료: 저장(파일 출력) 후 메인메뉴로
			System.out.println("완료 버튼 이벤트 실행");
			
			try {
				// 파일 출력(저장)
		        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
		        admin.writeRoomInfos(out);
//		        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
//		        admin.writeIncomeInfo(out2);
		        out.close();
//		        out2.close();
			} catch (IOException ioe) {
				System.out.println("자료 저장 실패 (IOException): " + ioe.getMessage());
			} catch (Exception excpt) {
				System.out.println("자료 저장 실패 (Exception): " + excpt.getMessage());
			}
			
			
			new MainMenu(admin);
			this.dispose();

		
		} else if (obj == btnSave) {	// 저장: 파일 출력만 실행
			System.out.println("저장 버튼 이벤트 실행");
			
			try {
				// 파일 출력(저장)
		        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
		        admin.writeRoomInfos(out);
//		        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
//		        admin.writeIncomeInfo(out2);
		        out.close();
//		        out2.close();
			} catch (IOException ioe) {
				System.out.println("자료 저장 실패 (IOException): " + ioe.getMessage());
			} catch (Exception excpt) {
				System.out.println("자료 저장 실패 (Exception): " + excpt.getMessage());
			}
			
		} 
		
	}
}