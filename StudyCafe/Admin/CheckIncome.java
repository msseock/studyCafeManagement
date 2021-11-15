import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CheckIncome extends JFrame implements ActionListener {
	private JButton btnToday;
	private JButton btnThisMonth;
	private JComboBox month1;
	private JComboBox day1;
	private JComboBox month2;
	private JComboBox day2;
	private JButton btnCheck;
	private JLabel totalIncomeLabel;
	private JButton btnComplete;
	private JButton btnCancel;
	private Admin admin;
	private Calendar calendar;
	
	
	CheckIncome(Admin admin) {
		this.setTitle("수익 조회");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.admin = admin;
		
		Container contentPane = this.getContentPane();
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("조회를 원하는 기간을 선택해주세요."));

		// Border CENTER: 방 전체 상태 조회 테이블 생성	
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
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
		centerPanel.add(btnToday, gbc);
		
		gbc.gridwidth = 2; gbc.gridheight = 1;
		gbc.gridx = 2; gbc.gridy = 0;
		centerPanel.add(btnThisMonth, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0; gbc.gridy = 1;
		centerPanel.add(month1, gbc);
		
		gbc.gridx = 1; gbc.gridy = 1;
		centerPanel.add(new JLabel("월"), gbc);
		
		gbc.gridx = 2; gbc.gridy = 1;
		centerPanel.add(day1, gbc);
		
		gbc.gridx = 3; gbc.gridy = 1;
		centerPanel.add(new JLabel("일부터"), gbc);
		
		gbc.gridx = 0; gbc.gridy = 2;
		centerPanel.add(month2, gbc);		
		
		gbc.gridx = 1; gbc.gridy = 2;
		centerPanel.add(new JLabel("월"), gbc);
		
		gbc.gridx = 2; gbc.gridy = 2;
		centerPanel.add(day2, gbc);
		
		gbc.gridx = 3; gbc.gridy = 2;
		centerPanel.add(new JLabel("일까지"), gbc);
		
		gbc.gridwidth = 4;
		gbc.gridx = 0; gbc.gridy = 3;
		centerPanel.add(btnCheck, gbc);

		
        
        // Border EAST: 날짜 조회 결과 생성
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
        String colNames[] = { "날짜", "수익" };
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        JTable incomeTable = new JTable(model);
        incomeTable.setPreferredScrollableViewportSize(new Dimension(150, 200));
        
        gbc.gridx = 0; gbc.gridy = 0;
        eastPanel.add(new JScrollPane(incomeTable), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        totalIncomeLabel = new JLabel("총 수익: 0원");
        eastPanel.add(totalIncomeLabel, gbc); // table의 수익을 모두 더해서 반환(추후 구현 예정)
		
		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnComplete = new JButton("완료");
		btnComplete.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		
		southPanel.add(btnComplete);
		southPanel.add(btnCancel);
		
		// 최종 frame의 contentPane 배치
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(eastPanel, BorderLayout.EAST);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object obj = e.getSource();
			// 오늘 수익 조회
			if (obj == btnToday) {
				System.out.println("오늘 수익 조회 버튼 이벤트 실행");
				
				calendar = new GregorianCalendar();		// 오늘 날짜
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DATE);
				
				int income = admin.getDailyIncome(month, day);
				totalIncomeLabel.setText("오늘 수익: " + income + "원");
				

			} else if (obj == btnThisMonth) { // 이번달 수익 조회
				System.out.println("이번달 수익 조회 버튼 이벤트 실행");
				
				calendar = new GregorianCalendar();		// 오늘 날짜
				int month = calendar.get(Calendar.MONTH) + 1;			
				int income = admin.getMonthlyIncome(month);
				totalIncomeLabel.setText("오늘 수익: " + income + "원");
				
			} else if (obj == btnCheck) { // 기간 수익 조회
				System.out.println(month1.getSelectedIndex() + "/" +  day1.getSelectedIndex() + " ~ "
				+ month2.getSelectedIndex() + "/" + day2.getSelectedIndex() + " 기간 수익 조회 버튼 이벤트 실행");
				
				
			} else if (obj == btnComplete) { // 완료
				System.out.println("완료 버튼 이벤트 실행");
				
				new MainMenu(admin);
				this.dispose();
				
			} else if (obj == btnCancel) { // 취소
				System.out.println("취소 버튼 이벤트 실행");
				
				new AdminMenu(admin);
				this.dispose();
				
			}
		} catch (Exception excpt) {
			System.out.println(excpt);
		}
		
	}
}
