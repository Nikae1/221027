package server;



public class ServerController {

	
	/*Client의 요청에 따라 분기(서버와 모델)를 담당*/
	public ServerController() {
		
	}
	
	public String controller(String clientData) {
		String accessResult = null;
		String serviceCode = (clientData.split("&")[0]).split("=")[1];
		/* serviceCode=1&id=hoonzzang&password=1234를 분기시키기 위해서
		 * split("&")[0] >>> {"serviceCode=1", "id=hoonzzang", "password=1234"}
		 * split("=")[1] >>> {"serviceCode","1"}
		 * */
		
		
		/* 항상 Userapp에서 넘어오는 idx의 값은 service 첫번째이며, 값은 1이라고 가정하였을때
		 * 로그인을 활성화 시켜주기위하여 Auth의 로그인 class인 accessCtl로 값을 전달
		 *  */
		
			
		
		if(serviceCode.equals("1")) {
			Auth auth = new Auth();
		 
			accessResult = auth.accessCtl(clientData)? "1":"0";
		}else if(serviceCode.equals("-1")){
			Auth auth = new Auth();
			auth.accessOut(clientData);
			accessResult = "로그아웃...";
		}
		
		return accessResult;
	}
}
