import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CheckIncome extends JFrame implements ActionListener {
	private JButton btnManageRoom;
	private JButton btnCheckIncome;
	private JButton btnRefund;

	private JButton btnToday;
	private JButton btnThisMonth;
	private JComboBox month1;
	private JComboBox day1;
	private JComboBox month2;
	private JComboBox day2;
	private JButton btnCheck;
	private JTable incomeTable;
	private JLabel totalIncomeLabel;
	private JButton btnComplete;
//	private JButton btnCancel;
	private Admin admin = new Admin();
	private Calendar calendar;
	
	
	CheckIncome() {
		this.setTitle("수익 조회");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		Container contentPane = this.getContentPane();
		
		// Border WEST: 메뉴 이동 버튼 생성
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(7, 1));
		westPanel.setBackground(Color.white);
		
		btnManageRoom = new JButton("방 관리");
		btnManageRoom.setBackground(Color.white);
		btnManageRoom.setOpaque(true);
		btnManageRoom.setBorderPainted(false);
		btnManageRoom.addActionListener(this);
		
		btnCheckIncome = new JButton("수익 확인");
		btnCheckIncome.setBackground(new Color(142, 142, 147));
		btnCheckIncome.setForeground(Color.white);
		btnCheckIncome.setOpaque(true);
		btnCheckIncome.setBorderPainted(false);
		btnCheckIncome.addActionListener(this);
		
		btnRefund = new JButton("환불");
		btnRefund.setBackground(Color.white);
		btnRefund.setOpaque(true);
		btnRefund.setBorderPainted(false);
		btnRefund.addActionListener(this);
		
		westPanel.add(btnManageRoom);
		westPanel.add(btnCheckIncome);
		westPanel.add(btnRefund);
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("조회를 원하는 기간을 선택해주세요."));

		
		// Border CENTER: 방 전체 상태 조회 테이블 생성	
		JPanel centerPanel1 = new JPanel();
		centerPanel1.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		btnToday = new JButton("오늘");
		btnToday.addActionListener(this);
		btnThisMonth = new JButton("이번달");
		btnThisMonth.addActionListener(this);
		btnCheck = new JButton("조회");
		btnCheck.addActionListener(this);
		
		Integer[] monthNums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		month1 = new JComboBox(monthNums);
		month2 = new JComboBox(monthNums);
		month1.setSelectedIndex(0);
		month2.setSelectedIndex(0);
		
		Integer[] dayNums = new Integer[32];
		for (int i = 0; i <= 31; i++) {
			dayNums[i] = i;
		}
		day1 = new JComboBox(dayNums);
		day2 = new JComboBox(dayNums);
		day1.setSelectedIndex(0);
		day2.setSelectedIndex(0);
		
		gbc.gridwidth = 2; gbc.gridheight = 1;
		gbc.gridx = 0; gbc.gridy = 0;
		centerPanel1.add(btnToday, gbc);
		
		gbc.gridwidth = 2; gbc.gridheight = 1;
		gbc.gridx = 2; gbc.gridy = 0;
		centerPanel1.add(btnThisMonth, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0; gbc.gridy = 1;
		centerPanel1.add(month1, gbc);
		
		gbc.gridx = 1; gbc.gridy = 1;
		centerPanel1.add(new JLabel("월"), gbc);
		
		gbc.gridx = 2; gbc.gridy = 1;
		centerPanel1.add(day1, gbc);
		
		gbc.gridx = 3; gbc.gridy = 1;
		centerPanel1.add(new JLabel("일부터"), gbc);
		
		gbc.gridx = 0; gbc.gridy = 2;
		centerPanel1.add(month2, gbc);		
		
		gbc.gridx = 1; gbc.gridy = 2;
		centerPanel1.add(new JLabel("월"), gbc);
		
		gbc.gridx = 2; gbc.gridy = 2;
		centerPanel1.add(day2, gbc);
		
		gbc.gridx = 3; gbc.gridy = 2;
		centerPanel1.add(new JLabel("일까지"), gbc);
		
		gbc.gridwidth = 4;
		gbc.gridx = 0; gbc.gridy = 3;
		centerPanel1.add(btnCheck, gbc);

		
        
        // Border EAST: 날짜 조회 결과 생성
		JPanel centerPanel2 = new JPanel();
		centerPanel2.setLayout(new GridBagLayout());
		centerPanel2.setPreferredSize(new Dimension(230, 350));
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
        String colNames[] = { "날짜", "수익" };
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        incomeTable = new JTable(model);
        incomeTable.setPreferredScrollableViewportSize(new Dimension(200, 300));
        
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel2.add(new JScrollPane(incomeTable), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        totalIncomeLabel = new JLabel("총 수익: 0원");
        centerPanel2.add(totalIncomeLabel, gbc); // table의 수익을 모두 더해서 반환(추후 구현 예정)
		
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(centerPanel1, BorderLayout.EAST);
        centerPanel.add(centerPanel2, BorderLayout.CENTER);

		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout());
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnComplete = new JButton("완료");
		btnComplete.addActionListener(this);
//		btnCancel = new JButton("취소");
//		btnCancel.addActionListener(this);
		
		southPanel.add(btnComplete);
//		southPanel.add(btnCancel);
		
		// mainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(westPanel, BorderLayout.WEST);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object obj = e.getSource();
			
			if (obj == btnManageRoom) {
				new ManageRoom();
				this.dispose();
				
			} else if (obj == btnCheckIncome) {
				System.out.println("수익 확인 버튼 이벤트 실행");
				
				new CheckIncome();
				this.dispose();
				
			} else if (obj == btnRefund) {
				System.out.println("환불");
				
				new Refund();
				this.dispose();
				
			} else if (obj == btnToday) { // 오늘 수익 조회
				System.out.println("오늘 수익 조회 버튼 이벤트 실행");
				
				calendar = new GregorianCalendar();		// 오늘 날짜
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE) - 1;
				
				int income = admin.getDailyIncome(month, day);
				
				DefaultTableModel model = (DefaultTableModel) incomeTable.getModel();	// income table model 불러오기
				model.setNumRows(0);	// 	table 초기화

				String todayIncome[] = { (month+1) + "/" + (day + 1), income + "원" };
				model.addRow(todayIncome);
				totalIncomeLabel.setText("오늘 수익: " + income + "원");
				

			} else if (obj == btnThisMonth) { // 이번달 수익 조회
				System.out.println("이번달 수익 조회 버튼 이벤트 실행");
				
				calendar = new GregorianCalendar();		// 오늘 날짜
				int month = calendar.get(Calendar.MONTH);			
//				int income = admin.getMonthlyIncome(month);
				
				// table 정보 추가
				ArrayList<Integer> incomeArr = admin.getSelectedIncome(month+1, 0, 0, 0);
				int incomeSum = incomeArr.get(incomeArr.size() - 1);
				if (incomeSum >= 0) {
					DefaultTableModel model = (DefaultTableModel) incomeTable.getModel();	// income table model 불러오기
					model.setNumRows(0);	// 	table 초기화
					
					String incomeData[] = new String[2]; // model row에 추가할 데이터
					
					// table에 날짜와 수익값 추가
					int arrSize = incomeArr.size()-1;
					for (int i = 0; i < arrSize; i++) {
						incomeData[0] = (month+1) + "/" + (i+1);
						incomeData[1] = incomeArr.get(i) + "원";
						model.addRow(incomeData);
					}
					
					// totalIncomeLabel에 기간 총수입 띄우기
					totalIncomeLabel.setText("이번달 수익: " + incomeSum + "원");	
					
				} else if (incomeSum == -1) {
					new DefaultAlert("수익 조회 오류", "올바른 기간을 입력해주세요", "확인");
				} else if (incomeSum == -2) {
					new DefaultAlert("수익 조회 오류", "수익이 존재하지 않습니다", "확인");
				}
				
			} else if (obj == btnCheck) { // 기간 수익 조회
				// 기간 날짜 받아오기
				int m1 = month1.getSelectedIndex();
				int d1 = day1.getSelectedIndex();
				int m2 = month2.getSelectedIndex();
				int d2 = day2.getSelectedIndex();
				
				// 이벤트 실행 메시지 출력
				System.out.println(m1 + "/" +  d2 + " ~ " + m2 + "/" + d2 + " 기간 수익 조회 버튼 이벤트 실행");
				
				// 해당하는 기간의 수익을 table과 totalIncomeLabel에 띄우기
				ArrayList<Integer> incomeArr = admin.getSelectedIncome(m1, d1, m2, d2);
				int incomeSum = incomeArr.get(incomeArr.size() - 1);
				if (incomeSum >= 0) {
					DefaultTableModel model = (DefaultTableModel) incomeTable.getModel();	// income table model 불러오기
					model.setNumRows(0);	// 	table 초기화
					
					String incomeData[] = new String[2]; // model row에 추가할 데이터
					
					// table에 날짜와 수익값 추가
					int arrSize = incomeArr.size()-1;
					for (int i = 0; i < arrSize; i++) {
						if (m1 < m2) {
								incomeData[0] = (m1 + ((d1 + i) / 31)) + "/" + ((d1 + i) % 31 + 1);
								incomeData[1] = incomeArr.get(i) + "원";
						} else {
							incomeData[0] = m1 + "/" + (d1 + i);
							incomeData[1] = incomeArr.get(i) + "원";
						}
						model.addRow(incomeData);
					}
					
					// totalIncomeLabel에 기간 총수입 띄우기
					totalIncomeLabel.setText(m1 + "/" +  d1 + " ~ " + m2 + "/" + d2 + " 기간 수익: " + incomeSum + "원");
					
				} else if (incomeSum == -1) {
					new DefaultAlert("수익 조회 오류", "올바른 기간을 입력해주세요", "확인");
				} else if (incomeSum == -2) {
					new DefaultAlert("수익 조회 오류", "수익이 존재하지 않습니다", "확인");
				}
				
				
			} else if (obj == btnComplete) { // 완료
				System.out.println("완료 버튼 이벤트 실행");
				
				new MainMenu(admin);
				this.dispose();
				
			} 
//			else if (obj == btnCancel) { // 취소
//				System.out.println("취소 버튼 이벤트 실행");
//				
//				new MainMenu(admin);
//				this.dispose();
//				
//			}
		} catch (Exception excpt) {
			System.out.println(excpt);
		}
		
	}
}
