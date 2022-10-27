package client;

public class TaskManagement {

	public TaskManagement() {

	}

	public Object taskController(int selection) {
		Object result = null;
		switch(selection) {
		case 11:
			this.makeTaskCalendarCtl(result);
			break;
		case 12:
			this.getTaskListCtl(result);
			break;
		case 2:
			this.TaskRegistrationCtl(result);
			break;
		case 3:
			this.setModifyTaskCtl(result);
			break;
		case 4:
			this.getTaskStatCtl(result);
			break;
		}
		return result;
	}

	/* 특정 달의 Task Calendar 생성하기 */
	private void makeTaskCalendarCtl(Object result) {

	}
	
	/* 등록된 모든 할일 리스트 가져오기 */
	private void getTaskListCtl(Object result) {
		
	}
	/* 할일 등록 하기 */
	private void TaskRegistrationCtl(Object result) {

	}
	/* 등록된 할일 수정하기 */
	private void setModifyTaskCtl(Object result) {

	}
	/* 할일에 대한 통계 만들기 */
	private void getTaskStatCtl(Object result) {

	}
	
	/* 특정 달의 할일이 등록되어있는 날짜가져오기 */
	private String[] getTaskDays() {
		String[] taskDays = null;
		
		return taskDays;
	}
	/* 특정 달의 할일이 등록되어있는 날짜를 특정 달의 달력에 표시하기 */
	private String makeCalendar(String[] days) {
		StringBuffer calendar = new StringBuffer();
		
		return calendar.toString();
	}
}
