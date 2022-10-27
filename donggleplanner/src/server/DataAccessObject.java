package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import server.beans.AccessHistoryBean;
import server.beans.MemberBean;
import server.beans.ToDoBean;

/*Data File과의 통신을 위한(DAO)
 * DB에서 정보만 전달하는 역할을 수행
 * */
public class DataAccessObject {
	/* txt파일의 주소 */
 String[] fileInfo = {
		 "C:\\java\\data\\woong\\donggleplanner\\src\\database\\MEMBERS.txt",
		 "C:\\java\\data\\woong\\donggleplanner\\src\\database\\ACCESSHISTORY.txt",
		 "C:\\java\\data\\woong\\donggleplanner\\src\\database\\DONGGLE.txt"};
	public DataAccessObject() {

	}

	/* 회원정보 수집 (List는 Collection의 최상위 ) */
	public ArrayList<MemberBean> readDatabase(int fileIdx) {
		String line;
		ArrayList<MemberBean> memberList = null;
		/* Generic<> list를 활용할때에는 list에 넣은 값들은 타입이 자기의 속성값이 아닌
		 * 가장 큰 값인 object로 바뀌어, 빈으로 바꿀때엔 다운캐스트가 필요하다
		 * Generic은 <>안에 넣어주면 자동으로 다운캐스트를 도와준다
		 *  */
		MemberBean member;
		BufferedReader buffer = null;
		
try {
	/* buffer의 파라미터 안에 파일을 읽어올 수 있는 FileReader를 만들고 
	 * FileReader의 파라미터 안에
	 * File을 만들며, 그 파라미터 안에 DB의 주소값을 가진 fileInfo를 넣어
	 * 별도로 참조변수 선언을 하여 메모리를 할당하지 않고 원하는 값을 가져온다.
	 *  */
	buffer = new BufferedReader(new FileReader
			(new File(fileInfo[fileIdx])));
	memberList = new ArrayList<MemberBean>();
	/* list의 종류인 Array를 사용하여 사용
	 * 배열과의 차이점은 배열은 크기를 미리 설정해야하지만
	 * ArrayList는 크기를 미리 정하지않고 원하는 만큼 가능하다
	 *  */
	
	while((line = buffer.readLine())!= null) 
	/* filereader는 char(한글자)로 읽어오므로 readLine을 통하여 줄로 읽어온다 */
	{
		/* 불러온 MEMBERS의 
		 * (jw,123,김지웅,01066248449,1)을 split
		 * */
		String[] record = line.split(",");
		member = new MemberBean();
		member.setAccessCode(record[0]);
		member.setSecretCode(record[1]);
		member.setUserName(record[2]);
		member.setPhoneNumber(record[3]);
		member.setActivation(Integer.parseInt(record[4]));
		memberList.add(member);
	}
	
} catch (FileNotFoundException e) {
	/* Exception은 자바가 처리할 수 없는 모든 예외처리를 맡는다 */
	System.out.println("파일이 존재하지 않습니다.");
	e.printStackTrace();
	/* printStackTrace는 어디서 에러가 발생했는지 나타내는 */
}catch (IOException e) {
	memberList = null;
	/* catch구문에서 오류가 발생하여 초기화가 되지 않을 것을 대비 */
	System.out.println("파일로부터 데이터를 가져올 수 없습니다.");
	e.printStackTrace();
}finally {
	/*finally =  try - catch구문에 상관없이 실행 */
	try {
		buffer.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
     return memberList;
	}

	
	
	
	/* 접속기록(로그정보) 작성 */
	public boolean writeAccessHistory(AccessHistoryBean accessInfo) {
		boolean result = false;
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter
					(new FileWriter
					(new File(this.fileInfo[accessInfo.getFileIdx()]),true));
			/* 실제로 쓰는 건 FileWriter
			 * true - FileWriter는 원래 한 줄만 기록이 저장되므로, 
			 * 옵션값을 추가하여 다른 줄이 추가되도록 해야한다 
			 * FileWriter라는 생성자를 호출할 때, true값을 넣어주어야 한다
			 * */
			writer.write(accessInfo.getAccessCode()+","
			 +accessInfo.getAccessDate()+","
					+accessInfo.getAccessType());
			 
				writer.newLine();
				writer.flush();
				result = true;
		
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try {writer.close();} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
		
		
		
	}
	
	public ArrayList<ToDoBean> getToDoList(ToDoBean searchInfo){
		ArrayList<ToDoBean> dayList = null;
		ToDoBean toDo = null;
		String line;
		BufferedReader buffer = null;
		int date, recordCount = 1;
		int[] dateRange = new int[2];
		
		try {
			buffer = new BufferedReader
					(new FileReader
							(new File(fileInfo[searchInfo.getFileIdx()])));
			while((line=buffer.readLine()) !=null) {
				if(recordCount == 1) dayList = new ArrayList<ToDoBean>();
				
				String[] record = line.split(",");
				date = Integer.parseInt(searchInfo.getStartDate());
				dateRange[0] = Integer.parseInt(record[1].substring(0, 8));
				dateRange[1] = Integer.parseInt(record[2].substring(0, 8));
					
				if(date > dateRange[0]/100) dateRange[0] = Integer.parseInt(date+"01");
				if(date > dateRange[1]/100) dateRange[1] = Integer.parseInt(date+"30");
				
				for(int idx=dateRange[0]; idx<=dateRange[1]; idx++) {
					toDo = new ToDoBean();
					/* 문자열로 저장하기 위해서 + " " */
					toDo.setStartDate(idx+"");
					dayList.add(toDo);
				}
				
				recordCount++;
				
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return dayList;
		
		
		
		
		
		
		
	}
	
	

}
