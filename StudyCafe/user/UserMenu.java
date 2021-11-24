import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserMenu extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4205640317429618470L;
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnCheckIn;
	private JButton btnCheckOut;
	private JButton btnBackToMain;
	private JTextField phoneNoField;
	private Admin admin = new Admin();
	
	public UserMenu() { // 컴포넌트 생성 추가
		this.setTitle("User");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("전화번호를 입력하고 원하는 메뉴를 선택해주세요."));

		// Border CENTER: 전화번호 입력 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.weightx = 0.1;
		gbc.gridx = 0; gbc.gridy = 0;
		JLabel label = new JLabel("전화번호");
		label.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(label, gbc);
		
		gbc.weightx = 0.4;
		gbc.gridx = 1;
		phoneNoField = new JTextField();
		centerPanel.add(phoneNoField, gbc);
		
		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnCheckIn = new JButton("좌석 선택");
		btnCheckIn.addActionListener(this);
		
		btnCheckOut = new JButton("이용 완료");
		btnCheckOut.addActionListener(this);
		
		btnBackToMain = new JButton("메인메뉴로");
		btnBackToMain.addActionListener(this);
		
		gbc.weightx = 1.0;
		gbc.gridx = 0; gbc.gridy = 0;
		southPanel.add(btnCheckIn, gbc);
		
		gbc.gridx = 1; gbc.gridy = 0;
		southPanel.add(btnCheckOut, gbc);
		
		gbc.gridwidth = 2;
		gbc.gridx = 0; gbc.gridy = 1;
		southPanel.add(btnBackToMain, gbc);
		
		
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
			if (e.getSource() == btnCheckIn) {			// checkIn
				System.out.println("좌석 선택 버튼 이벤트 실행");
				
				String phoneNo = phoneNoField.getText();
				
				if (!phoneNo.isEmpty()) {
					System.out.print("phoneNoField NULL 아님, ");
					new CheckIn(phoneNo);
					this.dispose();
				} else new DefaultAlert("좌석 선택 오류", "전화번호를 입력해주세요", "다시 입력");
			

			
			} else if (e.getSource() == btnCheckOut) {	// checkOut
				System.out.println("이용 완료 버튼 이벤트 실행");
				
				// admin에서 phoneNoField.getText()로 checkOut() 함수 호출 후 user 존재 여부에 따라 창 띄우기
				int charge = admin.showCharge(phoneNoField.getText());
				if (charge != -1) {
					new CheckOutComplete(charge, phoneNoField.getText());
					this.dispose();
				} else new DefaultAlert("사용자 정보 조회 불가", "해당하는 조건에 맞는 user가 존재하지 않습니다.", "다시 입력");
				
			} else if (e.getSource() == btnBackToMain) {// back to main
				System.out.println("메인메뉴로 버튼 이벤트 실행");
	
				new MainMenu(admin);
				dispose();
	
			}
		} catch (NullPointerException npe) {
			new DefaultAlert("좌석 선택 오류", "전화번호를 입력해주세요.", "다시 입력");
		} catch (Exception excptn) {
			excptn.getMessage();
		}
		
	}
}
