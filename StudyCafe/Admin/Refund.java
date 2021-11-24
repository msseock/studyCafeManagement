import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Refund extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3715769910468808786L;
	private JButton btnManageRoom;
	private JButton btnCheckIncome;
	private JButton btnRefundMenu;
//	private JButton btnBackToMain;
	private JComboBox monthCbx;
	private JComboBox dayCbx;
	private JTextField moneyField;
	private JButton btnRefund;
	private JButton btnComplete;
//	private JButton btnCancel;
	private Admin admin = new Admin();
	
	Refund() {
		this.setTitle("환불");
		this.setLocation(100, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		Container contentPane = this.getContentPane();
		
		// Border WEST: 메뉴 이동 버튼 생성
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(3, 1));
		btnManageRoom = new JButton("방 관리");
		btnManageRoom.setBackground(Color.white);
		btnManageRoom.setOpaque(true);
		btnManageRoom.setBorderPainted(false);
		btnManageRoom.addActionListener(this);
		
		btnCheckIncome = new JButton("수익 확인");
		btnCheckIncome.setBackground(Color.white);
		btnCheckIncome.setOpaque(true);
		btnCheckIncome.setBorderPainted(false);
		btnCheckIncome.addActionListener(this);
		
		btnRefundMenu = new JButton("환불");
		btnRefundMenu.setBackground(new Color(142, 142, 147));
		btnRefundMenu.setForeground(Color.white);
		btnRefundMenu.setOpaque(true);
		btnRefundMenu.setBorderPainted(false);
		btnRefundMenu.addActionListener(this);
		
		westPanel.add(btnManageRoom);
		westPanel.add(btnCheckIncome);
		westPanel.add(btnRefundMenu);
//		westPanel.add(btnBackToMain);
		
		// Border NORTH: 안내문 패널 생성
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(new JLabel("환불 메뉴입니다. 환불을 원하는 날짜를 선택하고 환불할 금액을 입력해주세요."));

		// Border CENTER: 방 전체 상태 조회 테이블 생성	
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		Integer[] monthNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		monthCbx = new JComboBox(monthNums);
		monthCbx.setSelectedIndex(0);
		
		Integer[] dayNums = new Integer[31];
		for (int i = 0; i < 31; i++) {
			dayNums[i] = i + 1;
		}
		dayCbx = new JComboBox(dayNums);
		dayCbx.setSelectedIndex(0);
		
		moneyField = new JTextField(10);
		
		centerPanel.add(monthCbx);
		centerPanel.add(new JLabel("월"));
		centerPanel.add(dayCbx);
		centerPanel.add(new JLabel("일"));
		centerPanel.add(moneyField);
		centerPanel.add(new JLabel("원"));
		
		
		// Border SOUTH: 버튼 위치할 패널 생성
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		
		// 버튼에 리스너 연결 후 패널에 추가
		btnRefund = new JButton("환불");
		btnRefund.addActionListener(this);
		btnComplete = new JButton("완료");
		btnComplete.addActionListener(this);
//		btnCancel = new JButton("취소");
//		btnCancel.addActionListener(this);
		
		southPanel.add(btnRefund);
		southPanel.add(btnComplete);
//		southPanel.add(btnCancel);
		
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
				new CheckIncome();
				this.dispose();
				
			} else if (obj == btnRefundMenu) {
				new Refund();
				this.dispose();
				
			}  else if (obj == btnRefund) {
				System.out.println("환불 버튼 이벤트 실행");
				int month = monthCbx.getSelectedIndex();	// 0~11
				int day = dayCbx.getSelectedIndex();		// 0~30
				
				boolean moendyFieldEmpty = moneyField.getText().isEmpty();
				
				// 환불 금액 입력 시 환불 진행
				if (moendyFieldEmpty) {
					new DefaultAlert("환불 오류", "환불할 금액을 입력하세요", "확인");
				} else {
					int money = Integer.parseInt(moneyField.getText());
					int result = admin.subtractDailyIncome(month, day, money);
					
					if (result == 0) {	// 환불 정상처리
						new DefaultAlert("환불", (month + 1) + "월  " + (day + 1) + "일 " + money + "원 환불 완료되었습니다", "확인");
						
	//					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
	//			        admin.writeRoomInfos(out);
				        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
				        admin.writeIncomeInfo(out2);
	//			        out.close();
				        out2.close();
	
					} else if (result == -1) {	// 환불 금액 over
						new DefaultAlert("환불 오류", "환불 금액이 해당 일자의 총수입보다 큽니다.", "확인");
					} else if (result == -2) {	// 수익 없는 경우
						new DefaultAlert("환불 오류", "수익이 존재하지 않습니다.", "확인");
					}
				}
				
				
			} else if (obj == btnComplete) {
				System.out.println("완료 버튼 이벤트 실행");
				
				new MainMenu(admin);
				this.dispose();
				
			} 
//			else if (obj == btnCancel) {
//				System.out.println("취소 버튼 이벤트 실행");
//				
//				new AdminMenu();
//				this.dispose();
//				
//			}
		} catch (NullPointerException npe) {
			new DefaultAlert("환불 오류", "가격을 정확히 입력해주세요", "확인");
		} catch (IOException ioe) {
			System.out.println("저장 오류: " + ioe.getMessage());
		} catch (Exception excpt) {
			System.out.println(excpt.getMessage());
		}
		
		
	}
}
