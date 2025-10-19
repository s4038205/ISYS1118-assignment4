/*
Created by Monika Swiergon - s4038205
Unit: ISYS1118 Assignment 4
Class: FlightSearchTest
Description: Unit Testing for the class FlightSearch
 */

package flight;

import org.junit.jupiter.api.DisplayName; // https://www.digitalocean.com/community/tutorials/junit-display-name-displayname
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlightSearchTest {

    // Test 1: Testing that the passenger count is too low/high
    @Test
    @DisplayName("Condition 1: Total number of passengers")
    void testNumOfPassengers() {
        FlightSearch flight = new FlightSearch();
     // 1 passenger - valid
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                1, 0, 0));   
     // 0 passengers - invalid
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                0, 0, 0)); 
     // 10 passengers - invalid
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                10, 0, 0)); 
        //assertEquals(falseTest, falseTest = false);        
        //assertNull(flight.getDepartureDate());
    }

    // Test 2: Testing for a child in first class
    @Test
    @DisplayName("Condition 2: A Child in first class")
    void testChildInFirstClass() {
        FlightSearch flight = new FlightSearch();
        // Child in first class
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "first",
                1, 1, 0));   // Adding a child in first class
        // No Child in first Class
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "first",
                1, 0, 0)); 
    }

    // Test 3: Testing for an infant sitting in emergency/business
    @Test
    @DisplayName("Condition 3: An infant in business class")
    void testInfantInBusinessClass() {
        FlightSearch flight = new FlightSearch();
        // Infant in business class
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "business",
                1, 0, 1));   // Adding an infant in business class
        // No infant
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "business",
                1, 0, 0));         
    }

    // Test 4: Testing for 2 children per adult
    @Test
    @DisplayName("Condition 4: Too many children per adult")
    void testTooManyChildren() {
        FlightSearch flight = new FlightSearch();
        // 3 children, 1 adult
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                1, 3, 0));   // 3 children, 1 adult
        // 2 children, 1 adult
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                1, 2, 0));
    }

    // Test 5: Testing for 1 infant per adult
    @Test
    @DisplayName("Condition 5: More infants than adults")
    void testInfantsExceedAdults() {
        FlightSearch flight = new FlightSearch();
        //2 infants 1 adult
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                1, 0, 2));   // 2 infants, 1 adult
        // 1 infant per adult
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                1, 0, 1)); 
    }

    // Test 6: Testing if the departure date is in the past
    @Test
    @DisplayName("Condition 6: Departure date is in the past")
    void testDepartureDatePast() {
        FlightSearch flight = new FlightSearch();
        // date from 2000
        assertFalse(flight.runFlightSearch(
                "01/01/2000", "syd", false, // adding a date from 2000
                "30/12/2025", "mel", "economy",
                1, 0, 0));
        //normal date
        assertTrue(flight.runFlightSearch(
                "25/12/2025", "syd", false,
                "30/12/2025", "mel", "economy",
                1, 0, 0));

    }

    // Test 7: Testing if the dates/format are always correct
    @Test
    @DisplayName("Condition 7: Testing if 29/02/2026, a non-leap year is parsed")
    void testInvalidDateFormat() {
        FlightSearch flight = new FlightSearch();
        assertFalse(flight.runFlightSearch(
                "29/02/2026", "syd", false, // adding an invalid date
                "30/12/2026", "mel", "economy",
                1, 0, 0));
        //Normal date
        assertTrue(flight.runFlightSearch(
                "25/11/2025", "syd", false, // adding an invalid date
                "30/12/2025", "mel", "economy",
                1, 0, 0));
    }

    // Test 8: Testing if the return date is before the departure date
    @Test
    @DisplayName("Condition 8: Return date is before the departure date")
    void testReturnBeforeDeparture() {
        FlightSearch flight = new FlightSearch();
        assertFalse(flight.runFlightSearch(
                "30/12/2025", "syd", false,  // putting the 30th Dec as the departure date
                "10/12/2025", "mel", "economy", // putting the 10th of Dec as the return date
                1, 0, 0));
        //Normal date
        assertTrue(flight.runFlightSearch(
                "25/11/2025", "syd", false, // adding a valid date
                "30/12/2025", "mel", "economy",
                1, 0, 0));
    }

    // Test 9: Testing if the seating class is valid
    @Test
    @DisplayName("Condition 9: Invalid seating class")
    void testInvalidSeatingClass() {
        FlightSearch flight = new FlightSearch();
        //invalid class
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "fourth",  // Adding an invalid seating class of "fourth class"
                1, 0, 0));
        //valid class
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false,
                "30/12/2025", "mel", "first", 
                1, 0, 0));       
    }

    // Test 10: Testing if the emergency row is only placed in economy class
    @Test
    @DisplayName("Condition 10: Emergency row within business class")
    void testEmergencyRowInBusiness() {
        FlightSearch flight = new FlightSearch();
        // Emergency Row within First
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", true,  // emergency row = true
                "30/12/2025", "mel", "first", // first class
                2, 0, 0)); 
        // Emergency Row within Business
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", true,  // emergency row = true
                "30/12/2025", "mel", "business", // business class
                2, 0, 0));   
        // Emergency row within premium economy
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", true,  // emergency row = true
                "30/12/2025", "mel", "premium economy", // premium economy class
                2, 0, 0));
        // Emergency row within economy
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", true,  // emergency row = true
                "30/12/2025", "mel", "economy", // economy class
                2, 0, 0));
    }

    // Test 11: Testing if the airport codes provided are correct
    @Test
    @DisplayName("Condition 11: Unknown airport code")
    void testUnknownAirport() {
        FlightSearch flight = new FlightSearch();
        // adding a code of "abc"
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "abc", false, // adding a code of "abc"
                "30/12/2025", "mel", "economy",
                1, 0, 0));
        // adding normal codes
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "syd", false, // adding normal codes
                "30/12/2025", "mel", "economy",
                1, 0, 0));
    }

    // Test 12: Testing if the airport codes provided are different from each other
    @Test
    @DisplayName("Condition 11: Same departure & destination codes")
    void testSameAirport() {
        FlightSearch flight = new FlightSearch();
        // setting the dep and dest codes both to syd
        assertFalse(flight.runFlightSearch(
                "23/12/2025", "syd", false, 
                "30/12/2025", "syd", "economy",
                1, 0, 0));
        // making the dest and dep codes diff
        assertTrue(flight.runFlightSearch(
                "23/12/2025", "cdg", false, 
                "30/12/2025", "syd", "economy",
                1, 0, 0));
    }

}