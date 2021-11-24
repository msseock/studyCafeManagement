import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;
import java.util.GregorianCalendar;
import java.util.Calendar;



public class CheckOutComplete extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7056487740894205103L;
	
	// 버튼 참조 변수 선언
	private JButton btnPay;
	private JButton btnCancel;
	private Admin admin = new Admin();
	private String userNo;
	private int charge;

	public CheckOutComplete(int charge, String userNo)
	{
		this.setTitle("이용 완료");
		this.setPreferredSize(new Dimension(200, 100));
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.userNo = userNo;
		this.charge = charge;
		
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
		try {
			if (e.getSource() == btnPay) {
				System.out.println("결제 버튼 이벤트 실행");
				admin.checkOut(userNo); // 좌석에서 사용자 체크아웃
				
				// 결제 당일 날짜 구하기 (체크인한 날짜와 상관 없이 돈을 지불하는 시기로 수익 정산)
				GregorianCalendar calendar = new GregorianCalendar();
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE) - 1;
				
				admin.addDailyIncome(month, day, charge); // 수입 추가
				
				// 파일 출력(저장)
		        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./roomData.dat"));
		        admin.writeRoomInfos(out);
		        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("./incomeData.dat"));
		        admin.writeIncomeInfo(out2);
		        out.close();
		        out2.close();
				
				new MainMenu(admin);
				this.dispose();
				
			} else if (e.getSource() == btnCancel) {
				System.out.println("취소 버튼 이벤트 실행");
				new UserMenu();
				this.dispose();
			}
		} catch (IOException ioe) {
			System.out.println("자료 저장 실패 (IOException): " + ioe.getMessage());
		} catch(Exception excpt) {
			new DefaultAlert("체크아웃 오류", "체크아웃에 실패했습니다.", "확인");
		}
	}

}
