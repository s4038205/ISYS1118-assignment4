/*
Created by Monika Swiergon - s4038205
Unit: ISYS1118 Assignment 4
Class: FlightSearch
Description: Adding conditions to the runFlightSearch function
 */

package flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Set;

public class FlightSearch {
   private String  departureDate;
   private String  departureAirportCode;
   private boolean emergencyRowSeating;
   private String  returnDate;
   private String  destinationAirportCode; 
   private String  seatingClass;
   private int     adultPassengerCount;
   private int     childPassengerCount;
   private int     infantPassengerCount;

   // Decided to add 3 new constants and reasoning described below
   // https://www.baeldung.com/java-static-final-order

   // Using the reference below to establish a strict mode on Java Date Time Formatter to parse a Local Date for the DateTimeParseException.
   // https://stackoverflow.com/questions/226910/how-to-sanity-check-a-date-in-java
   // https://howtodoinjava.com/java/date-time/resolverstyle-strict-date-parsing/
   // Condition 7. All dates must be formatted in the format DD/MM/YYYY eg "23/11/2025" and must be validated to ensure that the combination is correct
   // (eg "29/02/2026" would be invalid as 2026 is not a leap year). Ensure that STRICT date validation is applied.
   private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.STRICT);


   // https://www.codecademy.com/resources/docs/java/set
   // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Set.html
   // set.of is immutable so callers cannot modify it, eliminating accidental side-effects -> created for seating classes and airports
   // https://medium.com/@idiotN/java-10s-set-of-explained-b2b6d79e4357
   private static final Set<String> VALID_SEATING_CLASSES = Set.of("economy", "premium economy", "business", "first");
   // The only valid classes are the four listed above otherwise will return false in the conditions below
   private static final Set<String> VALID_AIRPORTS = Set.of("syd", "mel", "lax", "cdg", "del", "pvg", "doh");
   // The only valid set of airports
   
   
   public boolean runFlightSearch(String departureDate,    String departureAirportCode,   boolean emergencyRowSeating,
                                  String returnDate,       String destinationAirportCode, String seatingClass, 
                                  int adultPassengerCount, int childPassengerCount,       int infantPassengerCount) {
      
	  boolean valid = true;
	   
      // Condition 1. The total number of passenger per search must be at least 1 and cannot exceed 9.
      int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount; // Creating a variable to count all passengers
      if (totalPassengers < 1 || totalPassengers > 9) {
    	 //System.out.println("Condition 1: Returning False");
         return false;
      } //System.out.println("Condition 1: Returning True");

      // Condition 2. Children cannot be seated in emergency row seating or first class.
      if (emergencyRowSeating && seatingClass.equals("first")) 
      {
    	 //System.out.println("Condition 2: Returning False");
    	  return false;
      } //System.out.println("Condition 2: Returning True");

      // Condition 3. Infants cannot be seated in emergency row seating or business class.
      if (emergencyRowSeating && (seatingClass.equals("business") || seatingClass.equals("first"))) {
    	  //System.out.println("Condition 3: Returning False");
    	  return false;
      } //System.out.println("Condition 3: Returning True");

      // Condition 4. All children (aged 2-11 years old) must be seated immediately next to at least one adult passenger (ie up to 2 children per adult).
      // eg if the adult passenger count is 2, then up to 4 child passengers are allowed.
      if (childPassengerCount > adultPassengerCount * 2) {
    	 //System.out.println("Condition 4: Returning False \n");
    	 return false;
      }	//System.out.println("Condition 4: Returning True \n");

      // Condition 5. Each infant (<2 years old) must be seated on an accompanying adults lap (only one infant is allowed per adult)
      if (infantPassengerCount > adultPassengerCount) {
    	  //System.out.println("Condition 5: Returning False");
    	  return false;
      } //System.out.println("Condition 5: Returning True");

      // Condition 6. The departure date cannot be in the past (this is based on the current date when runFlightSearch method is called).
      LocalDate today = LocalDate.now(); // assigning a variable today to the current time through LocalDate
      LocalDate departDate; // assigning a variable to depart date through LocalDate
      try { // parsing the departure date in strict mode and ensuring it is today or later.
         departDate = LocalDate.parse(departureDate, DATE_FMT); // https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
      } catch (DateTimeParseException e) {
         return false; // if there's a parsing error return false
      }
      if (departDate.isBefore(today)) { // if the date is before today's date return false
         return false;
      }

      // Condition 8. All flights are two way only (ie include return flights) and the return date cannot be before departure date.
      // If no return date is supplied return false
      if (returnDate == null || returnDate.isBlank()) {
         return false;
      }
      // Same principle as condition 6 as listed above
      LocalDate retDate;
      try {
         retDate = LocalDate.parse(returnDate, DATE_FMT);
      } catch (DateTimeParseException e) {
         return false;
      }
      if (retDate.isBefore(departDate)) {
         return false;
      }

      // Condition 9. The seating class must be one of ("economy', "premium economy", "business", "first").
      if (!VALID_SEATING_CLASSES.contains(seatingClass)) { //If there is no valid seating class selected return false
         return false;
      }

      //Condition 10. Only economy class seating can have an emergency row (all classes of seating can be non-emergency).
      if (emergencyRowSeating && !seatingClass.equals("economy")) {
         return false;
      }

      // Condition 11. Only the following airports are available: "syd" (Sydney), "mel" (Melbourne), "lax" (Los Angeles), "cdg" (Paris), "del" (Delhi), "pvg" (Shanghai) and "doh" (Doha).
      // eg for a flight from Melbourne to Shanghai, the departure airport code would be "mel" and the destination airport code would be "pvg".
      // Furthermore, the departure airport and destination airport cannot be the same.
      if (!VALID_AIRPORTS.contains(departureAirportCode) || !VALID_AIRPORTS.contains(destinationAirportCode) || departureAirportCode.equals(destinationAirportCode)) {
         return false; // If the dest. code matches departure code or if its not a valid airport return false
      }

      // Adding constructor parameters for each attribute to the class
      this.departureDate = departureDate;
      this.departureAirportCode = departureAirportCode;
      this.emergencyRowSeating = emergencyRowSeating;
      this.returnDate = returnDate;
      this.destinationAirportCode = destinationAirportCode;
      this.seatingClass = seatingClass;
      this.adultPassengerCount = adultPassengerCount;
      this.childPassengerCount = childPassengerCount;
      this.infantPassengerCount = infantPassengerCount;
      
      return valid;
   }
   
   // Adding getters for each variable to the class
   public String getDepartureDate() { return departureDate; }
   public String getDepartureAirportCode() { return departureAirportCode; }
   public boolean getEmergencyRowSeating() { return emergencyRowSeating; }
   public String getReturnDate() { return returnDate; }
   public String getDestinationAirportCode() { return destinationAirportCode; }
   public String getSeatingClass() { return seatingClass; }
   public int getAdultPassengerCount() { return adultPassengerCount; }
   public int getChildPassengerCount() { return childPassengerCount; }
   public int getInfantPassengerCount() { return infantPassengerCount; } 

}