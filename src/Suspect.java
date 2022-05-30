import java.util.ArrayList;
import java.util.Collections;

public class Suspect implements Comparable<Suspect> {

	private String name;
	private String codeName;
	private String country;
	private ArrayList<String> phoneNumberList;
	private ArrayList<Suspect> probableSuspectList;
	public ArrayList<Suspect> suggestList;
	private ArrayList<Suspect> aSuggestList;

	public Suspect(String name, String codeName, String country) {
		this.name = name;
		this.codeName = codeName;
		this.country = country;
		this.phoneNumberList = new ArrayList<>();
		this.probableSuspectList = new ArrayList<>();
		this.suggestList = new ArrayList<>();
		this.aSuggestList = new ArrayList<>();
	}

	public void addNumber(String number) {
		phoneNumberList.add(number);
	}

	/*
	 * Searching if suspect is already in the list 
	 * or add him in probable suspectList
	 */
	public void findSuspect(Suspect suspect) {
		if (!(isConnectedTo(suspect))) {
			if (suspect.getName().equals(name))// finds if the user is not the same as himself
				System.out.println("You can not be suggest with yourself!");
			else {// he is not the same so add him in the probableSuspectList
				probableSuspectList.add(suspect);
				suspect.addSuspect(this);
			}
		}
	}

	// Searching if probableSuspectList have this suspect 
	public boolean isConnectedTo(Suspect aSuspect) {
		for (Suspect suspect : probableSuspectList) {
			if (suspect.getName().equals(aSuspect.getName()))
				return true; // already in list
		}
		return false; // is not in the list
	}

	/*
	 * Creating new arrayList which consist of common variables 
	 * between two probable lists of two different suspects
	 */
	public ArrayList<Suspect> getCommonPartners(Suspect aSuspect) {
		ArrayList<Suspect> commonSuspectList = new ArrayList<>(this.probableSuspectList);
		commonSuspectList.retainAll(aSuspect.probableSuspectList);
		return commonSuspectList;
	}

	public int compareTo(Suspect aSuspect) {
		return this.getName().compareTo(aSuspect.getName());
	}

	
	
	// Searching if phone is in suspects phoneList and return the answer
	public boolean phoneInSuspect(String phone) {
		for (String suspectPhones : phoneNumberList) {
			if (suspectPhones.equals(phone)) {
				return true;
			}
		}
		return false;
	}

	// Suggested Suspects
	public void suggestSuspects() {
		if (suggestList.isEmpty()) {
			for (Suspect suspect : probableSuspectList) {
				for (Suspect aSuspect : suspect.probableSuspectList) {
					if (!(aSuspect.getName().equals(this.getName())))
						addSuggestFriend(aSuspect);
				}
			}
		}

	}

	/*
	 * Creating aSuggestList with all suggest suspects
	 * To create this list I cross the suspectList of the first suspect 
	 * and the suspectList of the second suspect and If he is not the same person
	 * I add him in the list
	 * I print the data of aSuggestList
	 */
	public String toStringSuggestFriend() {
		String arrayWithSuggestFriends = "";
		int count = 0;
		// if it is true that means the program is running for the first time
		if (aSuggestList.isEmpty()) {
			for (Suspect suspect : suggestList) {// First suspect's probable suspects
				boolean flag = true;
				for (Suspect aSuspect : probableSuspectList) {// Second suspect's probable suspects
					if (suspect.getName().equals(aSuspect.getName())) {
						flag = false;
					}
				}
				if (flag) {
					aAddSuggestSuspect(suggestList.get(count));
					count++;
				}
			}

		}
		
		// Sort array list
		Collections.sort(aSuggestList);
		
		// Print data
		for (Suspect suspect : aSuggestList)
			arrayWithSuggestFriends += "\nName: " + suspect.getName() + "\nCode Name: " + suspect.getCodeName()
					+ "\nCountry: " + suspect.getCountry();
		
		return arrayWithSuggestFriends;
	}
	
	// Returning a String with all suspect's phones
	public String toStringSuspects(int i) {
		String arrayWithSuspects = "";
		arrayWithSuspects += phoneNumberList.get(i).toString();
		return arrayWithSuspects;
	}

	// Print Suspect info
	public void printInfo() {
		System.out.println("Suspector collaborators :");
		for (Suspect suspect : probableSuspectList) {
			System.out.println("Name: " + suspect.getName() + "Code name: " + suspect.codeName);
		}
	}

	// add new user to list
	public void addSuspect(Suspect suspect) {
		probableSuspectList.add(suspect);
	}

	
	// Finding if phone is in the list of the phones
	public void phoneExist() {
		
	}
	
	public String getSuspects() {
		String suspects = "";
		for (Suspect suspect : probableSuspectList) {
			suspects += suspect.getName() + " " + suspect.getCodeName() + "\n";
		}

		return suspects;
	}

	// add new suggest Friend
	public void addSuggestFriend(Suspect suspect) {
		suggestList.add(suspect);
	}

	// add all new suggest Friend
	public void removeSuggestFriend(Suspect suspect) {
		suggestList.remove(suspect);
	}

	// add new suggested when it clarifies that they are not the same as themselves
	// or with existing species in new arrayList
	public void aAddSuggestSuspect(Suspect suspect) {
		aSuggestList.add(suspect);
	}

	public String getCodeName() {
		return codeName;
	}

	public String getCountry() {
		return country;
	}

	public ArrayList<Suspect> getProbableSuspectList() {
		return probableSuspectList;
	}

	public ArrayList<String> getPhoneList() {
		return phoneNumberList;
	}

	public String getName() {
		return this.name;
	}

}
