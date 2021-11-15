import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class CheckIn extends JFrame implements ActionListener {
	// 이벤트 연결을 위한 참조 변수 선언
	private JButton btnSelect;
	private JButton btnCancel;
	private String phoneNo;
	private JTable table;
	private Admin admin;
	
	CheckIn(String phoneNo, Admin admin) { // 이 생성자에서 admin 객체 넘겨받을 수 있을까?
		this.setTitle("좌석 선택");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.phoneNo = phoneNo;
		
		this.admin = admin;
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("사용가능한 방들 중 원하는 방을 골라 클릭 후 선택 버튼을 클릭해주세요."));

		// Border CENTER: 전체 방 조회 패널 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		
		String colNames[] = { "번호", "방 이름", "정원", "시간당 요금", "10분당 추가요금" };
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        table = new JTable(model);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		
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
		if (e.getSource() == btnSelect) {
			System.out.println("좌석 선택 버튼 이벤트 실행");
			
			// checkIn 메소드 사용해서 구현
			int selectedRoomNo = table.getSelectedRow();
			new DefaultAlert("좌석 선택 완료", selectedRoomNo + "번 방에 배정되었습니다.", "완료");
			new MainMenu(admin);
			dispose();

			
		} else if (e.getSource() == btnCancel) {
			System.out.println("취소 버튼 이벤트 실행");
			
			new UserMenu(admin);
			this.dispose();

		}
	}
}
