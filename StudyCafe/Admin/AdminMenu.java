import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminMenu extends JFrame implements ActionListener {
	// 버튼 참조 변수 선언
	private JButton btnManageRoom;
	private JButton btnCheckIncome;
	private JButton btnRefund;
	private JButton btnBackToMain;
	private Admin admin;

	
	public AdminMenu(Admin admin) { // 컴포넌트 생성 추가
		this.setTitle("Admin");
		this.setPreferredSize(new Dimension(300, 200));
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.admin = admin;
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("관리자 메뉴입니다."));

		
		// Border CENTER: 버튼 위치할 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 2));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnManageRoom = new JButton("방 관리");
		btnManageRoom.addActionListener(this);
		btnCheckIncome = new JButton("수익 조회");
		btnCheckIncome.addActionListener(this);
		btnRefund = new JButton("환불");
		btnRefund.addActionListener(this);
		btnBackToMain = new JButton("메인메뉴로");
		btnBackToMain.addActionListener(this);

		centerPanel.add(btnManageRoom);
		centerPanel.add(btnCheckIncome);
		centerPanel.add(btnRefund);
		centerPanel.add(btnBackToMain);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	// 이벤트 처리 함수
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnManageRoom) {
			System.out.println("방관리 버튼 이벤트 실행");
			
			new ManageRoom(admin);
			dispose();
			
		} else if (e.getSource() == btnCheckIncome) {
			System.out.println("수익 조회 버튼 이벤트 실행");
			
			new CheckIncome(admin);
			dispose();
			
		} else if (e.getSource() == btnRefund) {
			System.out.println("환불 버튼 이벤트 실행");
			
			new Refund(admin);
			dispose();

		} else if (e.getSource() == btnBackToMain) {
			new MainMenu(admin);
			this.dispose();
		}
	}
}
