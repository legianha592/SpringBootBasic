package vn.techmaster.learncollection.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Person {

  private int id;
  private String fullname;
  private String job;
  private String gender;
  private String city;
  private long salary;
  private Date birthday;
  private String birthdayString;
  private long age;
  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    try {
      this.birthday = dateFormatter.parse(birthday);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public String getBirthdayString(){
    setBirthdayString(this.birthday);
    return birthdayString;
  }

  public void setBirthdayString(Date date){
    this.birthdayString = dateFormatter.format(date);
  }

  public void setAge(){
    LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate birthdayDate = this.birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    this.age = Period.between(birthdayDate, currentDate).getYears();
  }

  public long getAge(){
    this.setAge();
    return age;
  }
}
