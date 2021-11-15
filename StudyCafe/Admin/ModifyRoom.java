import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModifyRoom extends JFrame implements ActionListener {
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnModify;
	private JTextField roomNameField;
	private JTextField occupancyField;
	private JTextField chargePerHourField;
	private JTextField surchargeField;
	
	public ModifyRoom() { // 컴포넌트 생성 추가
		this.setTitle("방 수정");
		this.setLocation(500, 300);
		this.setPreferredSize(new Dimension(300, 200));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("n번 방을 수정합니다.")); // 숫자에 해당하는 방 번호 매개변수로 넘겨받기

		// Border CENTER: 방 정보 입력 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		// JTextField 생성. 실제 Admin 객체의 선택한 room 정보 불러와서 입력하기
		roomNameField = new JTextField("1인용");
		occupancyField = new JTextField("1");
		chargePerHourField = new JTextField("1000");
		surchargeField = new JTextField("300");
		
		// JLabel 생성 및 배치
		gbc.weightx = 0.1;
		gbc.gridx = 0; gbc.gridy = 0;	// (0, 0)
		JLabel label = new JLabel("방 이름");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 1;					// (0, 1)
		label = new JLabel("정원");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 2;					// (0, 2)
		label = new JLabel("시간당 요금");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 3;					// (0, 3)
		label = new JLabel("추가요금");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		
		// JTextField 배치
		gbc.weightx = 0.4;
		gbc.gridx = 1; gbc.gridy = 0;
		centerPanel.add(roomNameField, gbc);
		
		gbc.gridy = 1;
		centerPanel.add(occupancyField, gbc);

		gbc.gridy = 2;
		centerPanel.add(chargePerHourField, gbc);

		gbc.gridy = 3;
		centerPanel.add(surchargeField, gbc);

		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout());
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnModify = new JButton("수정");
		btnModify.addActionListener(this);
		
		southPanel.add(btnModify);
		
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
		if (e.getSource() == btnModify) {
			System.out.println("수정 버튼 이벤트 실행");
			
			// JTextField.getText()와 Admin의 SetRoom() 이용해서 방 수정 구현 예정
			new DefaultAlert("방 수정 완료", "방 수정이 완료되었습니다", "확인");
			this.dispose();
		}
	}
}
