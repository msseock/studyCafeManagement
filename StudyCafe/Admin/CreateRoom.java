import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreateRoom extends JFrame implements ActionListener {
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnCreate;
	private JTextField roomNameField;
	private JTextField occupancyField;
	private JTextField chargePerHourField;
	private JTextField surchargeField;
	private JTextField howManySameRoomField;
	
	public CreateRoom() { // 컴포넌트 생성 추가
		this.setTitle("방 생성");
		this.setLocation(500, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("'*'표시가 되어있는 항목을 모두 포함하여 방을 생성해주세요"));

		// Border CENTER: 방 정보 입력 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		// JTextField 생성
		roomNameField = new JTextField();
		occupancyField = new JTextField("1~");
		chargePerHourField = new JTextField("0~");
		surchargeField = new JTextField("0~");
		howManySameRoomField = new JTextField("1");
		
		// JLabel 생성 및 배치
		gbc.weightx = 0.1;
		gbc.gridx = 0; gbc.gridy = 0;	// (0, 0)
		JLabel label = new JLabel("방 이름*");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 1;					// (0, 1)
		label = new JLabel("정원*");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 2;					// (0, 2)
		label = new JLabel("시간당 요금*");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 3;					// (0, 3)
		label = new JLabel("추가요금*");
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0 , 0));
		centerPanel.add(label, gbc);
		
		gbc.gridy = 4;					// (0, 4)
		label = new JLabel("동일유형생성");
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

		gbc.gridy = 4;
		centerPanel.add(howManySameRoomField, gbc);

		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout());
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnCreate = new JButton("생성");
		btnCreate.addActionListener(this);
		
		southPanel.add(btnCreate);
		
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
		if (e.getSource() == btnCreate) {
			System.out.println("생성 버튼 이벤트 실행");
			
			// JTextField.getText()와 Admin의 CreateRoom() 이용해서 방 생성 구현 예정
			new DefaultAlert("방 생성 완료", "방 생성이 완료되었습니다", "확인");
			this.dispose();
		}
	}
}
