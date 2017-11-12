package library;

import person.Person;

public class PersonAtLibrary extends Person {
    private String personStatus;

    public PersonAtLibrary(String name, String surname, int age, String personStatus) {
        super(name, surname, age);
        this.personStatus = personStatus;
    }

    @Override
    public String toString() {
        return super.getName() + ", " + super.getSurname() + ", " + "status = " + personStatus;
    }

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

}
