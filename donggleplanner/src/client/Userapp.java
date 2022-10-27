package client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import server.ServerController;

public class Userapp {
	
	public Userapp() {

		frontController();
	}

	/* 클라이언트 화면 및 데이터 흐름 제어 */
	private void frontController() {
		Scanner scanner = new Scanner(System.in);
		
		String Title = this.mainTitle(this.getToday(true));
		String menu = this.makeMenu();
		String message = new String();
		String close = this.close();
		boolean isLoop = true;
		String[] accessInfo = new String[2];
		/*AccessCode와 SecretCode를 입력받기 위한 배열 */
		boolean accessResult;
		/*ServerController의 ctl에 접근하기 위한 참조변수
		 * 위에 선언하는 이유는 while문으로 인한 무한 참조변수의 생성을 막기위해서
		 * */
		ServerController ctl = new ServerController();
		/* 로그인 시 완료와 종료의 분기를 위한 반복문 */
		while (isLoop) {
			for (int idx = 0; idx < accessInfo.length; idx++) {
				this.display(Title);
				this.display(this.makeAccess(true, accessInfo[0]));
				accessInfo[idx] = this.userInput(scanner);
			}
			this.display(this.makeAccess(false, null));
			/*사용자의 id와 비밀번호를 가진 clientData 생성*/
			String[] itemName = {"id", "password"};
			
			/* 서버에 로그인 정보 전달 
			 * makeClientData에서 만든 배열을 파라미터를 통해서 전달
			 * */
			
			accessResult = 
				ctl.controller(this.makeClientData("1",itemName, accessInfo)).equals("1")? true:false;
						
					
			
			
			
			
			/* 서버로부터 받은 로그인 결과에 따른 화면 출력 */
			this.display(this.accessResult(accessResult));
			/*accessResult값으로 인하여 if구문에 변화가 생기므로 주의할 것*/
			
			/* 로그인 실패!!!!! */
			if (!accessResult) {
				if(this.userInput(scanner).toUpperCase().equals("N")) {
					isLoop = false;
				}else {
					/*userInput값을 받기 위한 */
					accessInfo[0] = null;
					accessInfo[1] = null; 
				}
			}else {
				/* 로그인 성공!!!!!*/
			while(isLoop) {
				String menuSelection = new String();
				
				this.display(Title);
				this.display(menu);
				menuSelection = this.userInput(scanner);
			
				/*0.Disconnetion 선택시 프로그램 종료 */
				if(menuSelection.toUpperCase().equals("0")) {
					ctl.controller(this.makeClientData("-1",itemName, accessInfo));
					isLoop = false;
			
					
					
				}
				
			}
			}
			
		}
		this.display(close);
			scanner.close();
	} // controller

	/* makeClientData 
	 * serviceCode=1&id=*****&password=****으로 저장하기위한
	 * 
	 * */
	private String makeClientData(String serviceCode ,String[] item, String[] userData) {
		StringBuffer clientData = new StringBuffer();
		clientData.append("servicecode="+serviceCode);
	
		for(int idx=0; idx<userData.length;idx++) {
			clientData.append("&");
			clientData.append(item[idx] + "=" + userData[idx]);
		}
		return clientData.toString();
	}
	
	
	
	
	
	/* 사용자 입력 */
	private String userInput(Scanner scanner) {
		return scanner.next();
	}

	/* 프로그램 메인 타이틀 제작 */
	private String mainTitle(String date) {
		StringBuffer title = new StringBuffer();

		title.append("◤Donggle◥〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
		title.append("∥\n");
		title.append("∥  D◎nggle D◎nggle   \n");
		title.append("∥      Planner\n");
		title.append("∥                  ╭◜◝ ͡ ◜◝        ╭◜◝ ͡ ◜◝\n");
		title.append("∥	          ( •‿•。   ) ☆   ( •‿•。   ) ☆\n");
		title.append("∥	           ╰◟◞ ͜ ◟◞        ╰◟◞ ͜ ◟◞\n");
		title.append("∥	                  ╭◜◝ ͡ ◜◝╮ \n");
		title.append("∥	                 ( •‿•。   ) ☆\n");
		title.append("∥                         ╰◟◞ ͜ ◟◞╯\n");
		title.append("∥                       " + date + "\n");
		title.append("∥                      design by. 4조\n");
		title.append("∥\n");
		title.append("●〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");

		return title.toString();
	}// 메인 타이틀

	private String makeAccess(boolean isItem, String AccessCode) {

		StringBuffer access = new StringBuffer();

		if (isItem) {
			access.append("◤ACCESS◥〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			access.append("∥\n");
			access.append("∥	  ____________     ____________\n");
			access.append("∥	 ｜AccessCode｜     ｜SecretCode｜    \n");
			access.append("∥	  ￣￣￣￣￣￣         ￣￣￣￣￣￣     \n");
			access.append("∥            " + ((AccessCode != null) ? AccessCode + "          \t" : ""));
		} else {
			access.append("●〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			access.append("\n");
			access.append("\n❥ ❥ ❥ Loading . . . \n");
		}

		return access.toString();

	} // Login화면

	/* Server 응답에 따른 로그인 결과값 출력 */
	private String accessResult(boolean isAccess) {
		
		StringBuffer accessResult = new StringBuffer();

		if (isAccess) {
			accessResult.append("❥ ❥ ❥ ❥ Successful Connection !!!\n");
			accessResult.append(".....Move after 2 seconds...\n");

		} else {
			accessResult.append("❥ ❥ ❥ ❥ Connection Failed...\n");
			accessResult.append("______________________ Retry(Y/N) ?");
		}

		return accessResult.toString();

	}
	
	private String close() {
		StringBuffer close = new StringBuffer();
		
		close.append("\n◤G◎◎D BYE !◥ 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
		close.append("∥\n");
		close.append("∥ See you ~ !ლ(●ↀωↀ●)ლ ლ(●ↀωↀ●)ლ \n");
		close.append("∥\n");
		close.append("●〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		
		return close.toString();
		
	}
	

	private String makeMenu() {

		StringBuffer menu = new StringBuffer();

		menu.append("◤MENU◥〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
		menu.append("∥\n");
		menu.append("∥ 1. TASK LIST         2. TASK SETTINGS\n");
		menu.append("∥ 3. MODIFY TASK       4. TASK STATS\n");
		menu.append("∥ 0. DISCONNECT\n");
		menu.append("∥\n");
		menu.append("●〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓 Select: ");

		return menu.toString();

	}// 메뉴 1번 - 메인 메뉴

	
	
	private String makeMessage(String text) {
		StringBuffer message = new StringBuffer();

		message.append("[message] ");
		message.append(text);
		message.append("\n");

		return message.toString();
	}// Error Message - 입력값이 제대로 들어오지않았을 때

	/*화면에 현재의 날짜를 출력하기 위한 */
	private String getToday(boolean isDate) {
		String pattern = (isDate) ? "yyyy. MM. dd" : "yyyy-MM-dd HH:mm:ss";
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	private void display(String text) {
		System.out.print(text);
	}// 화면 출력

}// userapp
