package main.scl.simplecarslist;


public class Person {
    private final String firstName;
    private final String lastName;
    private final String make;
    private final String model;

    public Person(String firstName, String lastName, String make, String model) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.make = make;
        this.model = model;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return firstName+MainActivity.FILE_SEPERATOR+lastName+MainActivity.FILE_SEPERATOR+make+MainActivity.FILE_SEPERATOR+model;
    }
}
