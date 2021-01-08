package vn.techmaster.learncollection.model;

public class CountPersonAndAge {
    long numberPerson;
    long age;

    public CountPersonAndAge(long numberPerson, long age) {
        this.numberPerson = numberPerson;
        this.age = age;
    }

    public long getNumberPerson() {
        return numberPerson;
    }

    public void setNumberPerson(long numberPerson) {
        this.numberPerson = numberPerson;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
