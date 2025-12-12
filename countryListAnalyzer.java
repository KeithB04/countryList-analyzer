import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * 
 * This class prompts the user for a file with countries in it then provides a menu of options.
 * These options include sorting the countries given in different ways and searching the array of
 * countries in different ways. The menu also includes an option to print a report for a given country 
 * and a report for the whole list of countries provided. The menu also provides an option to find the Kendall's Tau
 * matrix(not currently working) which is two values comparing the happiness index and the GDP Per capita and happiness
 * index with area per capita.
 * 
 * @author Keith Brandenburg
 * @version 2 February 2025
 */
public class Project1 {
	
	/** The countries. */
	private static Country[] countries;
	
	/** The is sorted by name. */
	private static boolean isSortedByName = false;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("COP 3530 Project1");
		System.out.println("Instructor Xudong Liu");
		System.out.println("\nArray Searches and Sorts");
		boolean fileFound = false;
		String fileName = "";

		while(!fileFound) {
			System.out.print("Enter the file name: ");
			fileName = scanner.next();
			if(fileName.compareTo("Countries1.csv") == 0) {
				fileFound = true;
			}
			else {
				System.out.println("File not found");
			}
		}
		loadCountries(fileName);

		int choice;
		do {
			System.out.println("1. Print a countries report");
			System.out.println("2. Sort by Name");
			System.out.println("3. Sort by Happiness Index");
			System.out.println("4. Sort by GDP per capita");
			System.out.println("5. Find and print a given country");
			System.out.println("6. Print Kendall’s tau matrix");
			System.out.println("7. Quit");
			System.out.print("Enter your choice: ");

			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				scanner.nextLine();
			} else {
				scanner.nextLine();
				choice = -1;
			}

			switch (choice) {
			case 1: printReport(); break;
			case 2: sortByName(); break;
			case 3: sortByHappinessIndex(); break;
			case 4: sortByGdpPerCapita(); break;
			case 5: searchCountry(scanner); break;
			case 6: printKendallsTauMatrix(); break;
			case 7: System.out.println("Have a good day!"); break;
			default: System.out.println("Invalid choice! Enter 1-7.");
			}
		} while (choice != 7);

		scanner.close();
	}

	/**
	 * Load countries.
	 *
	 * @param fileName the file name
	 */
	private static void loadCountries(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			String[] lines = br.lines().toArray(String[]::new);
			countries = new Country[lines.length];
			for (int i = 0; i < lines.length; i++) {
				String[] data = lines[i].split(",");
				countries[i] = new Country(data[0], data[1], Long.parseLong(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
			}
			System.out.println("There were " + countries.length + " records read.");
			br.close();
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	/**
	 * Prints the report.
	 */
	private static void printReport() {
		System.out.println("Name                 Capitol              GDPPC      APC        HappinessIndex");
		System.out.println("----------------------------------------------------------------------------");
		for (Country country : countries) {
			country.printCountry();
		}
	}

	/**
	 * Sort by name.
	 */
	private static void sortByName() {
		for (int i = 1; i < countries.length; i++) {
			Country key = countries[i];
			int j = i - 1;
			while (j >= 0 && countries[j].getName().compareTo(key.getName()) > 0) {
				countries[j + 1] = countries[j];
				j--;
			}
			countries[j + 1] = key;
		}
		isSortedByName = true;
		System.out.println("Countries sorted by Name.");
	}

	/**
	 * Sort by happiness index.
	 */
	private static void sortByHappinessIndex() {
		for (int i = 0; i < countries.length - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < countries.length; j++) {
				if (countries[j].getHappinessIndex() < countries[minIndex].getHappinessIndex()) {
					minIndex = j;
				}
			}
			Country temp = countries[i];
			countries[i] = countries[minIndex];
			countries[minIndex] = temp;
		}
		isSortedByName = false;
		System.out.println("Countries sorted by Happiness Index.");
	}

	/**
	 * Sort by gdp per capita.
	 */
	private static void sortByGdpPerCapita() {
		for (int i = 0; i < countries.length - 1; i++) {
			for (int j = 0; j < countries.length - i - 1; j++) {
				if (countries[j].getGdpPerCapita() > countries[j + 1].getGdpPerCapita()) {
					Country temp = countries[j];
					countries[j] = countries[j + 1];
					countries[j + 1] = temp;
				}
			}
		}
		isSortedByName = false;
		System.out.println("Countries sorted by GDP per capita.");
	}

	/**
	 * Search country.
	 *
	 * @param scanner the scanner
	 */
	private static void searchCountry(Scanner scanner) {
		System.out.print("Enter country name: ");
		String name = scanner.nextLine();
		int index;
		if (isSortedByName) {
			index = binarySearch(name);
			System.out.println("Binary search is used");
		} else {
			index = sequentialSearch(name);
			System.out.println("Sequential search is used");
		}
		if (index != -1) {
			System.out.printf("Name:          %-20s\nCapital:       %-20s\nGDPPC:       %10.3f\nAPC:         %10.6f\nHappines: %10.3f\n", countries[index].getName(), countries[index].getCapital(), countries[index].getGdpPerCapita(), countries[index].getAreaPerCapita(), countries[index].getHappinessIndex());
		} else {
			System.out.println("Error: country " + name + " not found");
		}
	}

	/**
	 * Binary search.
	 *
	 * @param name the name
	 * @return the int
	 */
	private static int binarySearch(String name) {
		int left = 0, right = countries.length - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			int cmp = countries[mid].getName().compareToIgnoreCase(name);
			if (cmp == 0) return mid;
			if (cmp < 0) left = mid + 1;
			else right = mid - 1;
		}
		return -1;
	}

	/**
	 * Sequential search.
	 *
	 * @param name the name
	 * @return the int
	 */
	private static int sequentialSearch(String name) {
		for (int i = 0; i < countries.length; i++) {
			if (countries[i].getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Prints the kendalls tau matrix.
	 */
	private static void printKendallsTauMatrix() {
		double tauGDP = calculateKendallsTau(countries, Comparator.comparingDouble(Country::getGdpPerCapita));
		double tauArea = calculateKendallsTau(countries, Comparator.comparingDouble(Country::getAreaPerCapita));

		System.out.println("---------------------------------------------");
		System.out.println("|                |   GDPPC   |   AreaPC   |");
		System.out.println("---------------------------------------------");
		System.out.printf("| Happiness Index | %5.4f  | %5.4f    |\n", tauGDP, tauArea);
		System.out.println("---------------------------------------------");
	}

	/**
	 * Calculate kendalls tau.
	 *
	 * @param countries the countries
	 * @param comparator the comparator
	 * @return the double
	 */
	private static double calculateKendallsTau(Country[] countries, Comparator<Country> comparator) {
		int n = countries.length;
		int agree = 0, disagree = 0;

		Country[] sorted = countries.clone();
		Arrays.sort(sorted, comparator);

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				boolean sameOrder = sorted[i] == countries[i] && sorted[j] == countries[j];
				if (sameOrder) agree++;
				else disagree++;
			}
		}
		return (double) (agree - disagree) / (n * (n - 1) / 2);
	}
}
