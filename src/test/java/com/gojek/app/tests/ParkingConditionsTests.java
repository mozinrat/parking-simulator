package com.gojek.app.tests;

import com.gojek.app.ParkingSimulator;
import com.gojek.app.Vehicle;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by rohit on 12/22/16.
 */
@RunWith(ZohhakRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingConditionsTests {

    private static ParkingSimulator parkingSimulator;

    @BeforeClass
    public static void init() {
        parkingSimulator = new ParkingSimulator(6);
    }

    @TestWith({
            "KA­01­HH­1234, White, Allocated slot number: 1",
            "KA­01­HH­9999, White, Allocated slot number: 2",
            "KA­01­BB­0001, Black, Allocated slot number: 3",
            "KA­01­HH­7777, Red, Allocated slot number: 4",
            "KA­01­HH­2701, Blue, Allocated slot number: 5",
            "KA­01­HH­1234, White, Car with same licence number already in garage",
            "KA­01­HH­3141, Black, Allocated slot number: 6",
            "KA­01­HH­3142, Black, Sorry parking lot is full"
//            "KA­01­HH­123X, White, Invalid license",
    })
    /** Simulates a vehicle entering the parking lot. Return the state of the parking lot if
     there is still free space, Sorry, parking lot is full if no empty parking space exists.
     */
    public void test1_park(String licensePlate, String color, String result) {
        Assert.assertTrue(result.equals(parkingSimulator.park(licensePlate, color)));
    }

    @TestWith({
            "KA­01­HH­1234, 1",
            "KA­01­HH­5234, Not found",
//            "test, License plate not Valid",

    })
    /**
     * Given a licence plate, returns the location of a vehicle, Not found if not present.
     */
    public void test2_findSlotNoByLicense(String licensePlate, String result) {
        Assert.assertTrue(result.equals(parkingSimulator.findSlotNoByLicense(licensePlate)));
    }

    @TestWith(value = {
            "White; KA­01­HH­1234, KA­01­HH­9999",
            "whita; Not found for color whita",
    },separator=";")
    /**
     * Given a color, returns the license number of all vehicles, Not found if not present.
     * registration_numbers_for_cars_with_colour White
     */
    public void test3_findLicenseNoByColor(String color, String result) {
        Assert.assertTrue(result.equals(parkingSimulator.findLicenseByColor(color)));
    }

    @TestWith(value = {
            "White; 1, 2 ",
            "whita; Not found for color whita",
    },separator=";")
    /**
     * Given a color, returns the locations of all vehicles, Not found if not present.
     * slot_numbers_for_cars_with_colour White
     */
    public void test4_findSlotByColor(String color, String result) {
        Assert.assertTrue(result.equals(parkingSimulator.findSlotNoByColor(color)));
    }


    @TestWith({
            "4, Slot number 4 is free",
            "4, Slot number 4 is already free no vehicle to leave"
    })
    /** Simulates a vehicle leaving the parking lot. Return the state of the freed parking lot.
     */
    public void test5_leave(int slotNot, String result) {
        Assert.assertTrue(result.equals(parkingSimulator.leave(slotNot)));
    }

    @Test
    /**
     * At any point of time get free parking spaces available..
     */
    public void test6_getFreeParkingSpaces() {
        Assert.assertNotNull(parkingSimulator.empltySlots());
    }

    @Test
    public void test7_status() {
        Assert.assertFalse(parkingSimulator.status().isEmpty());
    }
}
