// TODO: Auto-generated Javadoc
/**
 * The Class Country. Has a name and capital stored as strings and a population stored as a long, with 
 * a gdp, area, and happinessIndex stored as doubles. Has getter and setter methods for each private variable and a method that prints a
 * report for a country.
 * 
 * @author Keith Brandenburg
 * @version 2 February 2025
 */
class Country {
	
	/** The name. */
	private String name;
	
	/** The capital. */
	private String capital;
	
	/** The population. */
	private long population;
	
	/** The gdp. */
	private double gdp;
	
	/** The area. */
	private double area;
	
	/** The happiness index. */
	private double happinessIndex;


	/**
	 * Instantiates a new country.
	 *
	 * @param name the name
	 * @param capital the capital
	 * @param population the population
	 * @param gdp the gdp
	 * @param area the area
	 * @param happinessIndex the happiness index
	 */
	public Country(String name, String capital, long population, double gdp, double area, double happinessIndex) {
		this.name = name;
		this.capital = capital;
		this.population = population;
		this.gdp = gdp;
		this.area = area;
		this.happinessIndex = happinessIndex;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() { 
		return name;

	}
	
	/**
	 * Gets the capital.
	 *
	 * @return the capital
	 */
	public String getCapital() { 
		return capital; 
	}

	/**
	 * Gets the population.
	 *
	 * @return the population
	 */
	public long getPopulation() { 
		return population; 
	}

	/**
	 * Gets the gdp.
	 *
	 * @return the gdp
	 */
	public double getGdp() { 
		return gdp; 
	}

	/**
	 * Gets the area.
	 *
	 * @return the area
	 */
	public double getArea() { 
		return area; 
	}

	/**
	 * Gets the happiness index.
	 *
	 * @return the happiness index
	 */
	public double getHappinessIndex() { 
		return happinessIndex; 
	}

	/**
	 * Gets the gdp per capita.
	 *
	 * @return the gdp per capita
	 */
	public double getGdpPerCapita() { 
		return gdp / population; 
	}

	/**
	 * Gets the area per capita.
	 *
	 * @return the area per capita
	 */
	public double getAreaPerCapita() { 
		return area / population; 
	}

	/**
	 * Prints the country.
	 */
	public void printCountry() {
		System.out.printf("%-20s %-20s %10.3f %10.6f %10.3f\n", name, capital, getGdpPerCapita(), getAreaPerCapita(), happinessIndex);
	}
}