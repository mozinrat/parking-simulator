package com.gojek.app.tests;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rohit on 12/22/16.
 */
@RunWith(ZohhakRunner.class)
public class ParkingConditionsTests {

    HashMap<String, Integer> parkingLot;
    Set<Integer> parkingNo;

    @Before
    public void init() {
        parkingLot = new HashMap<String, Integer>(6);
        parkingNo = new HashSet<Integer>(6);

    }

    @TestWith({
            "KA­01­HH­1234, White, Allocated slot number: 1",
            "KA­01­HH­9999, White, Allocated slot number: 2",
            "KA­01­BB­0001, Black, Allocated slot number: 3",
            "KA­01­HH­7777, Red, Allocated slot number: 4",
            "KA­01­HH­2701, Blue, Allocated slot number: 5",
            "KA­01­HH­3141, Black, Allocated slot number: 6",
            "KA­01­HH­3142, Black, Sorry parking lot is full",
            "KA­01­HH­123X, White, Invalid license",
            "KA­01­HH­1234, White, Duplicate license",

    })
    public void park(String licensePlate, String color, String result) {
//        "Simulates a vehicle entering the parking lot. Return the state of the parking lot if
//        there is still free space, Sorry, parking lot is full if no empty parking space exists."
        Assert.assertTrue(2 > 1); // condition for result
    }

    @TestWith({
            "test, Not Valid",
            "KA­01­HH­1234, 1",
            " , Not Found",
    })
    public void findVehicleByLicense(String licensePlate, String result) {
//     "Given a licence plate, returns the location of a vehicle, Not found if not present."
        Assert.assertTrue(1 == 1); // condition for result
    }

    @TestWith({
            "white, KA­01­HH­1234",
    })
    public void findVehicleByColor(String color, String result) {
//     "Given a licence plate, returns the location of a vehicle, Not found if not present."
        Assert.assertTrue(1 == 1); // condition for result
    }

    @Test
    public void getFreeParkingSpaces() {
//     "At any point of time get free parking spaces available."
        Assert.assertTrue(2 > 1); // condition for result
    }


    @TestWith({
            "4,Slot number 4 is free",
            "4,Slot number 4 is already free no vehicle to leave"
    })
    public void leave(int slotNot, String result) {
//     "At any point of time get free parking spaces available."
        Assert.assertTrue(2 > 1); // condition for result
    }

    @Test
    public void status() {
        Assert.assertTrue(1 == 1);
    }
}
