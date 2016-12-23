package com.gojek.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by rohit on 12/23/16.
 */
public class CommandInterpreter {

    public static void main(String[] args) {
        print("Welcome to parking lot simulator, to list available commands press h, to quit press q");
        try (Scanner scanner = getScanner(args)) {
            ParkingSimulator parkingSimulator = null;
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                switch (command) {
                    case "h":
                    case "help": {
                        print("available commands " + Arrays.toString(Commands.values()));
                        System.out.println();
                        break;
                    }
                    case "q":
                    case "quit": {
                        print("Bye for now");
                        System.exit(0);
                        break;
                    }
                    case "":
                        break;
                    default:
                        final CustomMethod customMethod = validateAndTranslate(command);
                        // resolve constructor and methods, constructor will have method name as constructor by design
                        try {
                            if ("Constructor".equals(customMethod.getName())) {
                                final Constructor<ParkingSimulator> constructor = ParkingSimulator.class.getConstructor(customMethod.getInputTypes());
                                parkingSimulator = constructor.newInstance(customMethod.getArgs());
                            } else {
                                final Method method = ParkingSimulator.class.getDeclaredMethod(customMethod.getName(), customMethod.getInputTypes());
                                final Object result = method.invoke(parkingSimulator, customMethod.getArgs());
                                print(result.toString());
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                            print("Failed to execute instructions");
                        } catch (NullPointerException e) {
                            print(" Please create parking lot first....");
                        }
                        break;
                }
            }
        }catch (NoSuchElementException e){
            // graceful shutdown, should have done scanner.hasNextLine in whileLoop
            System.exit(0);
        }
    }

    private static Scanner getScanner(String[] args) {
        Scanner sc = new Scanner(new InputStreamReader(System.in));
        if(args.length>0) {
            File file = new File(args[0]);
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println(" File not found");
            }
        }
        return sc;
    }

    public static void print(String s) {
        System.out.println(s);
    }

    /**
     * Helper function to help and validate user input, will suggest user in repl mode as correct input
     * Also will translate user input in valid commands and arguements
     */
    private static CustomMethod validateAndTranslate(String input) {
        final String[] split = input.split(" ");
        String strCom = split[0];
        final Commands command = Commands.valueOf(strCom);
        Object[] args = new Object[command.getInputType().length];
        for (int i = 0; i < command.getInputType().length; i++) {
            Class clazz = command.getInputType()[i];
            try {
                if (clazz == String.class) {
                    args[i] = split[i + 1];
                } else {
                    Method method = clazz.getDeclaredMethod("valueOf", String.class);
                    if (method != null) {
                        args[i] = method.invoke(null, split[i + 1]);
                    }
                }
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                print("Failure : " + split[i + 1] + " is not of type " + clazz.getName());
                print(" Please provide inputs as " + strCom + " " + Arrays.toString(command.getInputType()));
            } catch (ArrayIndexOutOfBoundsException e) {
                print(" Please provide inputs as " + strCom + " " + Arrays.toString(command.getInputType()));
            } catch (IllegalArgumentException e) {
                print(" Not a valid function, press h to show valid functions");
            }
        }
        return new CustomMethod(command.getMethod(), args, command.getInputType());
    }

    static class CustomMethod {
        String name;
        Object[] args;
        Class[] inputTypes;

        public CustomMethod(String name, Object[] args, Class[] inputTypes) {
            this.name = name;
            this.args = args;
            this.inputTypes = inputTypes;
        }

        public Class[] getInputTypes() {
            return inputTypes;
        }

        public void setInputTypes(Class[] inputTypes) {
            this.inputTypes = inputTypes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }
}
