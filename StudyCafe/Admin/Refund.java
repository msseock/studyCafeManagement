import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Refund extends JFrame implements ActionListener {
	private JComboBox month;
	private JComboBox day;
	private JTextField moneyField;
	private JButton btnRefund;
	private JButton btnComplete;
	private JButton btnCancel;
	private Admin admin;
	
	Refund(Admin admin) {
		this.setTitle("환불");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.admin = admin;
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("환불 메뉴입니다. 환불을 원하는 날짜를 선택하고 환불할 금액을 입력해주세요."));

		// Border CENTER: 방 전체 상태 조회 테이블 생성	
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		Integer[] monthNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		month = new JComboBox(monthNums);
		month.setSelectedIndex(0);
		
		Integer[] dayNums = new Integer[31];
		for (int i = 0; i < 31; i++) {
			dayNums[i] = i + 1;
		}
		day = new JComboBox(dayNums);
		day.setSelectedIndex(0);
		
		moneyField = new JTextField(10);
		
		centerPanel.add(month);
		centerPanel.add(new JLabel("월"));
		centerPanel.add(day);
		centerPanel.add(new JLabel("일"));
		centerPanel.add(moneyField);
		centerPanel.add(new JLabel("원"));

		
		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 3));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnRefund = new JButton("환불");
		btnRefund.addActionListener(this);
		btnComplete = new JButton("완료");
		btnComplete.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		
		southPanel.add(btnRefund);
		southPanel.add(btnComplete);
		southPanel.add(btnCancel);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnRefund) {
			System.out.println("환불 버튼 이벤트 실행");
			
			new DefaultAlert("환불", (month.getSelectedIndex() + 1) + "월  " + (day.getSelectedIndex() + 1) + "일 " 
			+ moneyField.getText() + "원 환불 완료되었습니다", "확인");
			
		} else if (obj == btnComplete) {
			System.out.println("완료 버튼 이벤트 실행");
			
			new MainMenu(admin);
			this.dispose();
			
		} else if (obj == btnCancel) {
			System.out.println("취소 버튼 이벤트 실행");
			
			new AdminMenu(admin);
			this.dispose();
			
		}
	}
}
