import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame implements ActionListener {
	// 버튼 참조 변수 선언
	private JButton btnUserMenu;
	private JButton btnAdminMenu;
	private Admin admin;
	
	public MainMenu(Admin admin) { // 컴포넌트 생성 추가
		this.setTitle("StudyCafe main");
		this.setPreferredSize(new Dimension(300, 150));
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.admin = admin;
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.add(new JLabel("반갑습니다! 사용하실 메뉴를 선택해주세요."));

		
		// Border CENTER: 버튼 위치할 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnUserMenu = new JButton("사용자");
		btnUserMenu.addActionListener(this);
		btnAdminMenu = new JButton("관리자");
		btnAdminMenu.addActionListener(this);

		centerPanel.add(btnUserMenu);
		centerPanel.add(btnAdminMenu);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	// 이벤트 처리 함수
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUserMenu) {
			System.out.println("사용자 버튼 이벤트");
			
			new UserMenu(admin);
			this.dispose();

			
		} else if (e.getSource() == btnAdminMenu) {
			System.out.println("관리자 버튼 이벤트");
			
			new AdminMenu(admin);
			dispose();
			
		}
	}

}