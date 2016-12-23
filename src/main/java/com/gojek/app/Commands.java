package com.gojek.app;

import java.lang.reflect.Array;

/**
 * Created by rohit on 12/22/16.
 */
public enum Commands {
    create_parking_lot("Constructor",Integer.class),
    park("park",String.class,String.class),
    leave("leave",Integer.class),
    status("status"),
    registration_numbers_for_cars_with_colour("findLicenseByColor",String.class),
    slot_numbers_for_cars_with_colour("findSlotNoByColor",String.class),
    slot_number_for_registration_number("findSlotNoByLicense",String.class);

    private String method;
    private Class[] inputType;

    Commands(String method, Class<?>... inputType) {
        this.method=method;
        this.inputType=inputType;
    }

    public String getMethod() {
        return method;
    }

    public Class[] getInputType() {
        return inputType;
    }
}
