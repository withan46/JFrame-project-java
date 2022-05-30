import java.util.ArrayList;
import java.util.List;

public class Registry {

	private ArrayList<Suspect> suspectsList;
	private ArrayList<Communication> phoneCommunicationList;
	private ArrayList<Communication> smsCommunicationList;
	private ArrayList<SMS> allWords;

	public Registry() {
		this.suspectsList = new ArrayList<>();
		this.phoneCommunicationList = new ArrayList<>();
		this.smsCommunicationList = new ArrayList<>();
		this.allWords = new ArrayList<SMS>();
	}

	public void addSuspect(Suspect aSuspect) {
		suspectsList.add(aSuspect);
	}

	/*
	 * Searching the First Suspect Phone. If I find it I am searching for the second one. 
	 * If I find them, I will call findSuspect which search if suspect is not
	 * already in the list and add him. 
	 * Create two lists: PhoneCalls(phoneCommunicationList) - SMS(smsCommunicationList) Depending on
	 * the communication renew the specific list
	 */
	public void addCommunication(Communication aCommunication) {
		for (Suspect suspect : suspectsList) {
			if (suspect.phoneInSuspect(aCommunication.getFirstPhone())) {
				for (Suspect aSuspect : suspectsList) {
					if (aSuspect.phoneInSuspect(aCommunication.getSecondPhone())) {
						suspect.findSuspect(aSuspect);
						break;
					}
				}
			}

		}
		if (!(aCommunication instanceof SMS))
			phoneCommunicationList.add(aCommunication);
		else
			smsCommunicationList.add(aCommunication);

	}

	/*
	 * Crossing the suspectList and finding which suspect 
	 * have the the most partners and save 
	 * the list size in max and the suspect in maxSuspect.
	 */
	public Suspect getSuspectWithMostPartners() {
		int max = 0;
		Suspect maxSuspect = null;
		for (Suspect suspect : suspectsList) {
			if (suspect.getProbableSuspectList().size() >= max) {
				max = suspect.getProbableSuspectList().size();
				maxSuspect = suspect;
			}

		}
		return maxSuspect;
	}
	
	// Finding which phone have the longest distance 
	public PhoneCall getLongestPhoneCallBetween(String number1, String number2) {
		int max = 0;
		PhoneCall maxPhoneCallSeconds = null;
		for (Communication communication : phoneCommunicationList) {
			if (communication.matchPhoneCommunication(number1, number2)) {// true = they communicate
				if (((PhoneCall) communication).getSec() > max) {
					max = ((PhoneCall) communication).getSec();
					maxPhoneCallSeconds = ((PhoneCall) communication);
				}
			}
		}

		return maxPhoneCallSeconds;
	}

	/*
	 * words: list with specific words.
	 * Searching if we have communication between suspects
	 * and searching if there is any word from words list
	 * return messages which have these words
	 */
	public ArrayList<SMS> getMessagesBetween(String number1, String number2) {
		List<String> words = new ArrayList<String>();
		words.add("Bomb");
		words.add("Attack");
		words.add("Explosives");
		words.add("Gun");

		allWords.clear();
		for (Communication communication : smsCommunicationList) {
			if (communication.matchPhoneCommunication(number1, number2)) {
				for (String word : words) {
					if (((SMS) communication).getSms().toLowerCase().indexOf(word.toLowerCase()) != -1) {
						allWords.add((SMS) communication);
					}
				}
			}
		}
		return allWords;
	}

	public ArrayList<SMS> getAllWords() {
		return allWords;
	}

	public ArrayList<Suspect> getSuspectsList() {
		return suspectsList;
	}

}
