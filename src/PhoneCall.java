
public class PhoneCall extends Communication{
	
	
	private int sec;
	
	public PhoneCall(String firstPhone, String secondPhone, int day, int month, int year, int sec) {
		super(firstPhone, secondPhone, day, month, year);
		this.sec = sec;
	}
	
	public void printInfo() {
		super.printInfo();
		System.out.println(" second: "+sec);
	}

	public int getSec() {
		return sec;
	}
	
	
}
