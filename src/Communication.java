
public class Communication {

	private String firstPhone;
	private String secondPhone;
	private int day, month, year;

	public Communication(String firstPhone, String secondPhone, int day, int month, int year) {
		this.firstPhone = firstPhone;
		this.secondPhone = secondPhone;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public void printInfo() {
		System.out.println("First phone: " + firstPhone + " Second phone: " + secondPhone + " day/month/year: " + day
				+ "/" + month + "/" + year);
	}
	
	/*
	 *  Finding if number1 and number2 are equal.
	 *  So suspects have a communication.
	 */
	public boolean matchPhoneCommunication(String number1, String number2) {
		return (this.getFirstPhone().equals(number2) || this.getFirstPhone().equals(number1))
				&& (this.getSecondPhone().equals(number2) || this.getSecondPhone().equals(number1));
	}

	public String getFirstPhone() {
		return firstPhone;
	}

	public String getSecondPhone() {
		return secondPhone;
	}

}
