import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DefaultAlert extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7016348848401271681L;
	// 버튼 참조 변수 선언
	private JButton button;

	public DefaultAlert(String title, String alertMessage, String buttonText) {
		this.setTitle(title);
//		this.setPreferredSize(new Dimension(200, 100));
		this.setLocation(500, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();

		
		// Border CENTER: 안내문 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(new JLabel(alertMessage));
		
		// Border SOUTH: 확인(사용자 응답) 버튼 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout());
		
		// 버튼에 리스너 연결 후 패널에 추가
		button = new JButton(buttonText);
		button.addActionListener(this);
		southPanel.add(button);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button) {
//			System.out.println("이벤트 연결 확인");
			this.dispose();
		}
	}

}
