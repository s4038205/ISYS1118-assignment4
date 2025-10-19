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

    // Test 1: Testing that the passenger count is too low
    @Test
    @DisplayName("Condition 1: Total passengers less than 1")
    void testPassengerCountTooLow() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", false,
                "30/11/2025", "mel", "economy",
                0, 0, 0);   // 0 passengers
        assertFalse(falseTest);
        //assertEquals(falseTest, falseTest = false);        
        //assertNull(flight.getDepartureDate());
    }

    // Test 2: Testing that the passenger count is too high
    @Test
    @DisplayName("Condition 1: Total passengers more than 9")
    void testPassengerCountTooHigh() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", false,
                "30/11/2025", "mel", "economy",
                9, 1, 0);   // 10 passengers
        assertFalse(falseTest);
    }

    // Test 3: Testing for a child in first class
    @Test
    @DisplayName("Condition 2: A Child in first class")
    void testChildInFirstClass() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", true,
                "30/11/2025", "mel", "first",
                1, 1, 0);   // Adding a child in first class
        assertFalse(falseTest);
    }

    // Test 4: Testing for an infant sitting in emergency/business
    @Test
    @DisplayName("Condition 3: An infant in business class")
    void testInfantInBusinessClass() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", true,
                "30/11/2025", "mel", "business",
                1, 0, 1);   // Adding an infant in business class
        assertFalse(falseTest);
    }

    // Test 5: Testing for 2 children per adult
    @Test
    @DisplayName("Condition 4: Too many children per adult")
    void testTooManyChildren() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", false,
                "30/11/2025", "mel", "economy",
                1, 3, 0);   // 3 children, 1 adult
        assertFalse(falseTest);
    }

    // Test 6: Testing for 1 infant per adult
    @Test
    @DisplayName("Condition 5: More infants than adults")
    void testInfantsExceedAdults() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", false,
                "30/11/2025", "mel", "economy",
                1, 0, 2);   // 2 infants, 1 adult
        assertFalse(falseTest);
    }

    // Test 7: Testing if the departure date is in the past
    @Test
    @DisplayName("Condition 6: Departure date is in the past")
    void testDepartureDatePast() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "01/01/2000", "syd", false, // adding a date from 2000
                "30/11/2025", "mel", "economy",
                1, 0, 0);
        assertFalse(falseTest);
    }

    // Test 8: Testing if the dates/format are always correct
    @Test
    @DisplayName("Condition 7: Testing if 29/02/2026, a non-leap year is parsed")
    void testInvalidDateFormat() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "29/02/2026", "syd", false, // adding an invalid date
                "30/11/2025", "mel", "economy",
                1, 0, 0);
        assertFalse(falseTest);
    }

    // Test 9: Testing if the return date is before the departure date
    @Test
    @DisplayName("Condition 8: Return date is before the departure date")
    void testReturnBeforeDeparture() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "30/11/2025", "syd", false,  // putting the 30th November as the departure date
                "01/11/2025", "mel", "economy", // putting the 1st of November as the return date
                1, 0, 0);
        assertFalse(falseTest);
    }

    // Test 10: Testing if the seating class is valid
    @Test
    @DisplayName("Condition 9: Invalid seating class")
    void testInvalidSeatingClass() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", false,
                "30/11/2025", "mel", "fourth",  // Adding an invalid seating class of "fourth class"
                1, 0, 0);
        assertFalse(falseTest);
    }

    // Test 11: Testing if the emergency row is only placed in economy class
    @Test
    @DisplayName("Condition 10: Emergency row within business class")
    void testEmergencyRowInBusiness() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", true,  // emergency row = true
                "30/11/2025", "mel", "business", // business class
                2, 0, 0);   // 2 adults
        assertFalse(falseTest);
    }

    // Test 12: Testing if the airport codes provided are correct
    @Test
    @DisplayName("Condition 11: Unknown airport code")
    void testUnknownAirport() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "abc", false, // adding a code of "abc"
                "30/11/2025", "mel", "economy",
                1, 0, 0);
        assertFalse(falseTest);
    }

    // Test 13: Testing if the airport codes provided are different from each other
    @Test
    @DisplayName("Condition 11: Same departure & destination codes")
    void testSameAirport() {
        FlightSearch flight = new FlightSearch();
        boolean falseTest = flight.runFlightSearch(
                "23/11/2025", "syd", false, // setting the dep and dest codes both to syd
                "30/11/2025", "syd", "economy",
                1, 0, 0);
        assertFalse(falseTest);
    }

}