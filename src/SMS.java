
public class SMS extends Communication {
	
	private String sms;
	
	public SMS(String firstPhone, String secondPhone, int day, int month, int year, String sms) {
		super(firstPhone, secondPhone, day, month, year);
		this.sms = sms;
	}
	
	public void printInfo() {
		super.printInfo();
		System.out.println(" sms "+sms);
	}
	
	
	public String getSms() {
		return sms;
	}

}
