import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.table.*;

public class CheckIn extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3678899975124600855L;
	
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnSelect;
	private JButton btnCancel;
	private String phoneNo;
	private JTable roomTable;
	private Admin admin = new Admin();
	
	CheckIn(String phoneNo) { // 이 생성자에서 admin 객체 넘겨받을 수 있을까?
		this.setTitle("좌석 선택");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.phoneNo = phoneNo;
				
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("사용가능한 방들 중 원하는 방을 골라 클릭 후 선택 버튼을 클릭해주세요."));

		// Border CENTER: 전체 방 조회 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		
		String colNames[] = { "번호", "사용 가능", "방 이름", "정원", "시간당 요금", "10분당 추가요금" };
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        
        // 방 정보 받아오기
 		int i = 0;
 		for (Room room:admin.getRooms()) {
 			Object roomData[];
 			if(room.available()) {	// 비어있을 때
 				roomData = new Object[] { i, "O", room.getRoomName(), room.getOccupancy(),	// 번호, 사용가능여부, 이름, 정원
 						room.getChargePerHour(), room.getSurcharge() };			// 시간당 요금, 10분당 추가요금
 			} else {				// 사용중일 때	
 				roomData = new Object[] { i, "X", room.getRoomName(), room.getOccupancy(),	// 번호, 사용가능여부, 이름, 정원
 						room.getChargePerHour(), room.getSurcharge() };			// 시간당 요금, 10분당 추가요금, 사용중
 			}
 			model.addRow(roomData);
 			i++;
 		}
 		roomTable = new JTable(model);  
        centerPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
		
		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnSelect = new JButton("선택");
		btnSelect.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		southPanel.add(btnSelect);
		southPanel.add(btnCancel);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}

	// 이벤트 처리 함수
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnSelect) {
				System.out.println("좌석 선택 버튼 이벤트 실행");
				
				// checkIn 메소드 사용해서 구현
				int selectedRoomNo = roomTable.getSelectedRow();
				int result = admin.checkIn(phoneNo, selectedRoomNo);
				if (result == 0) {
					new DefaultAlert("좌석 선택 완료", selectedRoomNo + "번 방에 배정되었습니다.", "완료");
					
					// 파일 출력
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
			        admin.writeRoomInfos(out);
//			        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
//			        admin.writeIncomeInfo(out2);
			        out.close();
//			        out2.close();
			        
					new MainMenu(admin);
					dispose();
				} else {
					new DefaultAlert("좌석 선택 불가", selectedRoomNo + "번 방은 사용할 수 없는 방입니다.", "확인");
				}
				

				
			} else if (e.getSource() == btnCancel) {
				System.out.println("취소 버튼 이벤트 실행");
				
				new UserMenu();
				this.dispose();

			}
		} catch (IOException ioe) {
			System.out.println("저장 오류(ioe): " + ioe.getMessage());
		}
		catch (Exception excpt) {
			new DefaultAlert("좌석 선택 오류", "좌석을 선택할 수 없습니다.", "확인");
		}
		
	}
}
