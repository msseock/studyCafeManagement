import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CheckOutComplete extends JFrame implements ActionListener {
	// 버튼 참조 변수 선언
	private JButton btnPay;
	private JButton btnCancel;
	private Admin admin;

	public CheckOutComplete(int charge) 
	{
		this.setTitle("이용 완료");
		this.setPreferredSize(new Dimension(200, 100));
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();

		
		// Border CENTER: 안내문 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(new JLabel("이용요금: " + charge + "원"));
		
		// Border SOUTH: 확인(사용자 응답) 버튼 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnPay = new JButton("결제");
		btnPay.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		
		southPanel.add(btnPay);
		southPanel.add(btnCancel);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btnPay) {
			System.out.println("결제 버튼 이벤트 실행");
			new MainMenu(admin);
			this.dispose();
			
		} else if (e.getSource() == btnCancel) {
			System.out.println("취소 버튼 이벤트 실행");
			new UserMenu(admin);
			this.dispose();
		}
	}

}
