package com.gojek.app;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

/**
 * Created by rohit on 12/22/16.
 */
public class ParkingSimulator {

    private SortedSet<Integer> emptySlots;
    private HashMap<Integer, Vehicle> slotStatus;
    private HashMap<String, Integer> licensePosition;
    private HashMap<String, Set<Integer>> colorLicense;

    public ParkingSimulator(Integer noOfSolts) {
        this.emptySlots = Collections.synchronizedSortedSet(new TreeSet<>(range(1, noOfSolts + 1).boxed().collect(Collectors.toList())));
        this.licensePosition = new HashMap<>(noOfSolts);
        this.colorLicense = new HashMap<>(noOfSolts);
        this.slotStatus = new HashMap<>(noOfSolts);
        System.out.println("Parking created with slots " + noOfSolts);
    }

    public String park(String licensePlate, String color) {
        Vehicle v = new Vehicle(licensePlate,color);
        if (emptySlots.isEmpty()) {
            return "Sorry parking lot is full";
        }
        final Integer first = emptySlots.first();
        if (licensePosition.get(v.getLicensePlate()) != null) {
            return "Car with same licence number already in parking lot";
        }
        // add to licence tracking , parking vehicle
        slotStatus.put(first, v);
        licensePosition.put(v.getLicensePlate(), first);
        // make slot occupied
        emptySlots.remove(first);
        // track the color with license plate
        Set<Integer> colorSet = colorLicense.get(v.getColor());
        if (colorSet != null && !colorSet.isEmpty()) {
            colorSet.add(first);
        } else {
            colorSet = new HashSet<>();
            colorSet.add(first);
            colorLicense.put(v.getColor(), colorSet);
        }
        return "Allocated slot number: " + first;
    }

    public String leave(Integer slotNo) {
        if(slotNo>(emptySlots.size()+slotStatus.size())){
            return "Invalid slot number, max available is "+(emptySlots.size()+slotStatus.size());
        }
        if (emptySlots.contains(slotNo)) {
            return String.format("Slot number %d is already free no vehicle to leave", slotNo);
        }
        // remove from licence tracking
        final Vehicle v = slotStatus.get(slotNo);
        licensePosition.remove(v.getLicensePlate());
        slotStatus.remove(slotNo);

        // make slot empty
        emptySlots.add(slotNo);

        // remove from color tracking
        colorLicense.get(v.getColor()).remove(slotNo);
        return String.format("Slot number %d is free", slotNo);
    }

    public String status() {
       return slotStatus.entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue().getLicensePlate() + " " + entry.getValue().getColor())
                .collect(Collectors.joining("\n"));
    }

    public String findSlotNoByLicense(String licensePlate) {
        final Integer pos = licensePosition.get(licensePlate);
        if (pos != null) {
            return String.valueOf(pos);
        } else return "Not found";
    }

    public String findLicenseByColor(String color) {
        final Set<Integer> pos = colorLicense.get(color);
        if (pos != null && !pos.isEmpty()) {
            return  pos.stream().map(slotNo -> slotStatus.get(slotNo).getLicensePlate()).collect(Collectors.joining(", "));
        } else return "Not found for color " + color;
    }

    public String findSlotNoByColor(String color) {
        final Set<Integer> pos = colorLicense.get(color);
        if (pos != null && !pos.isEmpty()) {
            return  pos.toString().replaceAll( "[\\[|\\]|]", "" );
        } else return "Not found for color " + color;
    }

    public String empltySlots(){
        return emptySlots.toString().replaceAll( "[\\[|\\]|]", "" );
    }

}