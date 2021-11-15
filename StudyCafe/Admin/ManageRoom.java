import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ManageRoom extends JFrame implements ActionListener {
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnCheckIncome;
	private JButton btnRefund;
	private JButton btnClean;
	private JButton btnCreateRoom;
	private JButton btnDeleteRoom;
	private JButton btnModifyRoom;
	private JButton btnComplete;
	private JButton btnCancel;
	private Admin admin;

	
	
	ManageRoom(Admin admin) { // 이 생성자에서 admin 객체 넘겨받을 수 있을까?
		this.setTitle("방 관리");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.admin = admin;
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("사용가능한 방들 중 원하는 방을 골라 클릭 후 선택 버튼을 클릭해주세요."));
		
//		// Border WEST: 메뉴 이동 버튼 생성
//		JPanel westPanel = new JPanel();
//		westPanel.setLayout(new GridLayout(2, 1));
//		btnCheckIncome = new JButton("수익 확인");
//		btnCheckIncome.addActionListener(this);
//		btnRefund = new JButton("환불");
//		btnRefund.addActionListener(this);
//		
//		westPanel.add(btnCheckIncome);
//		westPanel.add(btnRefund);

		// Border CENTER: 방 전체 상태 조회 테이블 생성		
		String colNames[] = { "번호", "이름", "정원", "시간당 요금", "10분당 추가요금", "사용중", "청소", "입실시간", "사용자" };
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(model);         
		
		
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
		btnComplete = new JButton("완료");
		btnComplete.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		

		
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.gridheight = 1;
		southPanel.add(btnClean, gbc);
		
		gbc.gridx = 1; gbc.gridy = 0;
		southPanel.add(btnCreateRoom, gbc);
		
		gbc.gridx = 2; gbc.gridy = 0;
		southPanel.add(btnDeleteRoom, gbc);
		
		gbc.gridx = 3; gbc.gridy = 0;
		southPanel.add(btnModifyRoom, gbc);
		
		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
		southPanel.add(btnComplete, gbc);
		
		gbc.gridx = 2; gbc.gridy = 1;
		southPanel.add(btnCancel, gbc);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(northPanel, BorderLayout.NORTH);
//		contentPane.add(westPanel, BorderLayout.WEST);
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}

	// 이벤트 처리 함수
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnClean) {
			System.out.println("청소 버튼 이벤트 실행");

			
		} else if (obj == btnCreateRoom) {
			System.out.println("생성 버튼 이벤트 실행");
			
			new CreateRoom();			
			
		} else if (obj == btnDeleteRoom) {
			System.out.println("삭제 버튼 이벤트 실행");
			

		} else if (obj == btnModifyRoom) {
			System.out.println("수정 버튼 이벤트 실행");
			
			new ModifyRoom();

		} else if (obj == btnComplete) {
			System.out.println("완료 버튼 이벤트 실행");
			
			new MainMenu(admin);
			this.dispose();

		} else if (obj == btnCancel) {
			System.out.println("취소 버튼 이벤트 실행");
			
			new AdminMenu(admin);
			this.dispose();

		} 
//		else if (obj == btnCheckIncome) {
//			System.out.println("수익 확인 버튼 이벤트 실행");
//			
//			new CheckIncome();
//			this.dispose();
//			
//		} else if (obj == btnRefund) {
//			System.out.println("환불");
//			
//			new Refund();
//			this.dispose();
//		}
	}
}