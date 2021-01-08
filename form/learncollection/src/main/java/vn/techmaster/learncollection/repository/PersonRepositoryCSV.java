package vn.techmaster.learncollection.repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import vn.techmaster.learncollection.model.CountPersonAndAge;
import vn.techmaster.learncollection.model.Person;

@Repository
public class PersonRepositoryCSV implements PersonRepositoryInterface {
  private ArrayList<Person> people;

  @Autowired
  public PersonRepositoryCSV(@Value("${csvFile}") String csvFile) {
    people = new ArrayList<>();
    loadData(csvFile);
  }

  private void loadData(String csvFile) {
    try {
      File file = ResourceUtils.getFile("classpath:static/" + csvFile);
      CsvMapper mapper = new CsvMapper(); // Dùng để ánh xạ cột trong CSV với từng trường trong POJO
      CsvSchema schema = CsvSchema.emptySchema().withHeader(); // Dòng đầu tiên sử dụng làm Header
      ObjectReader oReader = mapper.readerFor(Person.class).with(schema); // Cấu hình bộ đọc CSV phù hợp với kiểu
      Reader reader = new FileReader(file);
      MappingIterator<Person> mi = oReader.readValues(reader); // Iterator đọc từng dòng trong file
      while (mi.hasNext()) {
        Person person = mi.next();
        people.add(person);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @Override
  public List<Person> getAll() {
    return people;
  }

  @Override
  public List<String> getAllJob(){
    List<String> list = new ArrayList<>();
    for (Person person : people){
      if (!list.contains(person.getJob())){
        list.add(person.getJob());
      }
    }
    return list;
  }

  @Override
  public List<Person> sortPeopleByFullNameReversed() {
    return people.stream()
            .sorted(Comparator.comparing(Person::getFullname).reversed())
            .collect(Collectors.toList());
  }

  @Override
  public List<String> getSortedCities() {
    /*
     * return people.stream(). sorted(Comparator.comparing(Person::getCity)).
     * map(Person::getCity).collect(Collectors.toList());
     */
    return people.stream()
            .map(Person::getCity)
            .sorted()
            .collect(Collectors.toList());
  }

  @Override
  public List<String> getSortedJobs() {
    return people.stream()
            .map(Person::getJob)
            .sorted()
            .collect(Collectors.toList());
  }

  @Override
  public List<Map.Entry<String, Integer>> findTop5Cities() {
    // TODO Auto-generated method stub

    HashMap<String, Integer> map = new HashMap<>();

    for (Person person : people){
      if(map.containsKey(person.getCity())){
        map.put(person.getCity(), map.get(person.getCity()) + 1);
      }
      else{
        map.put(person.getCity(), 1);
      }
    }

    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
    List<Map.Entry<String, Integer>> finalList = new ArrayList<>(5);

    Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        return o2.getValue() - o1.getValue();
      }
    };

    Collections.sort(list, valueComparator);

    int size = 0;
    if (list.size() >= 5){
      size = 5;
    }
    else{
      size = list.size();
    }
    for (int i=0; i<size; i++){
      finalList.add(list.get(i));
    }

    return finalList;
  }

  @Override
  public List<Map.Entry<String, Integer>> findTop5Jobs() {
    // TODO Auto-generated method stub
    HashMap<String, Integer> map = new HashMap<>();

    for (Person person : people){
      if(map.containsKey(person.getJob())){
        map.put(person.getJob(), map.get(person.getJob()) + 1);
      }
      else{
        map.put(person.getJob(), 1);
      }
    }

    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
    List<Map.Entry<String, Integer>> finalList = new ArrayList<>(5);

    Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        return o2.getValue() - o1.getValue();
      }
    };

    Collections.sort(list, valueComparator);

    int size = 0;
    if (list.size() >= 5){
      size = 5;
    }
    else{
      size = list.size();
    }
    for (int i=0; i<size; i++){
      finalList.add(list.get(i));
    }

    return finalList;
  }

  @Override
  public List<Map.Entry<String, HashMap<String, Integer>>> findTopJobInCity() {
    // TODO Auto-generated method stub
    HashMap<String, List<Person>> mapByCity = groupPeopleByCity();
    HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
    List<Map.Entry<String, HashMap<String, Integer>>> list;

    for (Map.Entry<String, List<Person>> entry : mapByCity.entrySet()){
      //entry chúa key la thanh pho, value la danh sach nhung nguoi trong thanh pho do
      HashMap<String, Integer> mapByJobInCity = new HashMap<>();
      HashMap<String, Integer> mostJobInCity = new HashMap<>();
      List<Person> listPeopleInCity = entry.getValue();

      for (int i=0; i<listPeopleInCity.size(); i++){
        String job = listPeopleInCity.get(i).getJob();
        if (mapByJobInCity.containsKey(job)){
          mapByJobInCity.put(job, mapByJobInCity.get(job) + 1);
        }
        else{
          mapByJobInCity.put(job, 1);
        }
      }

      List<Map.Entry<String, Integer>> listByJobInCity = new ArrayList<>(mapByJobInCity.entrySet());

      Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
          if (o1.getValue() == o2.getValue()){
            return o1.getKey().compareTo(o2.getKey());
          }
          return o2.getValue() - o1.getValue();
        }
      };

      Collections.sort(listByJobInCity, valueComparator);

      mostJobInCity.put(listByJobInCity.get(0).getKey(), listByJobInCity.get(0).getValue());

      map.put(entry.getKey(), mostJobInCity);
    }

    list = new ArrayList<>(map.entrySet());

    Comparator<Map.Entry<String, HashMap<String, Integer>>> outerValueComparator = new Comparator<Map.Entry<String, HashMap<String, Integer>>>() {
      @Override
      public int compare(Map.Entry<String, HashMap<String, Integer>> o1, Map.Entry<String, HashMap<String, Integer>> o2) {
        return o1.getKey().compareTo(o2.getKey());
      }
    };

    Collections.sort(list, outerValueComparator);

    return list;
  }

  @Override
  public List<Map.Entry<String, Integer>> groupJobByCount() {
    HashMap<String, Integer> map = new HashMap<>();
    for (Person person : people){
      if (map.containsKey(person.getJob())){
        map.put(person.getJob(), map.get(person.getJob()) + 1);
      }
      else{
        map.put(person.getJob(), 1);
      }
    }

    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

    Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        return o2.getValue() - o1.getValue();
      }
    };

    Collections.sort(list, valueComparator);

    return list;
  }

  @Override
  public HashMap<String, List<Person>> groupPeopleByCity() {
    // TODO Auto-generated method stub
    HashMap<String, List<Person>> map = new HashMap<>();

    for (Person person : people){
      if (map.containsKey(person.getCity())){
        map.get(person.getCity()).add(person);
      }
      else{
        List<Person> list = new ArrayList<>();
        list.add(person);
        map.put(person.getCity(), list);
      }
    }

    return map;
  }

 

  @Override
  public List<Map.Entry<String, Float>> averageCityAge() {
    // TODO Auto-generated method stub
    HashMap<String, Long> mapAge = new HashMap<>();
    HashMap<String, Long> countPeople = new HashMap<>();
    HashMap<String, Float> map = new HashMap<>();
    for (Person person : people){
      if (mapAge.containsKey(person.getCity())){
        mapAge.put(person.getCity(), mapAge.get(person.getCity()) + person.getAge());
        countPeople.put(person.getCity(), countPeople.get(person.getCity()) + 1);
      }
      else{
        mapAge.put(person.getCity(), person.getAge());
        countPeople.put(person.getCity(), 1L);
      }
    }

    for (Map.Entry<String, Long> age : mapAge.entrySet()){
      Float averageAge = (mapAge.get(age.getKey())).floatValue()/countPeople.get(age.getKey());
      map.put(age.getKey(), averageAge);
    }

    List<Map.Entry<String, Float>> list = new ArrayList<>(map.entrySet());

    Comparator<Map.Entry<String, Float>> valueComparator = new Comparator<Map.Entry<String, Float>>() {
      @Override
      public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        if (o1.getValue() < o2.getValue()){
          return -1;
        }
        return 1;
      }
    };

    Collections.sort(list, valueComparator);

    return list;
  }

  @Override
  public List<Map.Entry<String, Float>> averageJobAge() {
    // TODO Auto-generated method stub
    HashMap<String, Long> mapAge = new HashMap<>();
    HashMap<String, Long> countPeople = new HashMap<>();
    HashMap<String, Float> map = new HashMap<>();
    for (Person person : people){
      if (mapAge.containsKey(person.getJob())){
        mapAge.put(person.getJob(), mapAge.get(person.getJob()) + person.getAge());
        countPeople.put(person.getJob(), countPeople.get(person.getJob()) + 1);
      }
      else{
        mapAge.put(person.getJob(), person.getAge());
        countPeople.put(person.getJob(), 1L);
      }
    }

    for (Map.Entry<String, Long> age : mapAge.entrySet()){
      Float averageAge = (mapAge.get(age.getKey())).floatValue()/countPeople.get(age.getKey());
      map.put(age.getKey(), averageAge);
    }

    List<Map.Entry<String, Float>> list = new ArrayList<>(map.entrySet());

    Comparator<Map.Entry<String, Float>> valueComparator = new Comparator<Map.Entry<String, Float>>() {
      @Override
      public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        if (o1.getValue() < o2.getValue()){
          return -1;
        }
        return 1;
      }
    };

    Collections.sort(list, valueComparator);

    return list;
  }

  @Override
  public List<Map.Entry<String, Float>> averageJobSalary() {
    // TODO Auto-generated method stub
    HashMap<String, Long> mapAge = new HashMap<>();
    HashMap<String, Long> countPeople = new HashMap<>();
    HashMap<String, Float> map = new HashMap<>();
    for (Person person : people){
      if (mapAge.containsKey(person.getJob())){
        mapAge.put(person.getJob(), mapAge.get(person.getJob()) + person.getSalary());
        countPeople.put(person.getJob(), countPeople.get(person.getJob()) + 1);
      }
      else{
        mapAge.put(person.getJob(), person.getSalary());
        countPeople.put(person.getJob(), 1L);
      }
    }

    for (Map.Entry<String, Long> age : mapAge.entrySet()){
      Float averageAge = (mapAge.get(age.getKey())).floatValue()/countPeople.get(age.getKey());
      map.put(age.getKey(), averageAge);
    }

    List<Map.Entry<String, Float>> list = new ArrayList<>(map.entrySet());

    Comparator<Map.Entry<String, Float>> valueComparator = new Comparator<Map.Entry<String, Float>>() {
      @Override
      public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        if (o1.getValue() < o2.getValue()){
          return -1;
        }
        return 1;
      }
    };

    Collections.sort(list, valueComparator);

    return list;
  }

  @Override
  public List<Map.Entry<String, Integer>> find5CitiesHaveMostSpecificJob(String job) {
    HashMap<String, Integer> map = new HashMap<>();
    for (Person person : people){
      if (person.getJob().equals(job)){
        if (map.containsKey(person.getCity())){
          map.put(person.getCity(), map.get(person.getCity()) + 1);
        }
        else{
          map.put(person.getCity(), 1);
        }
      }
    }

    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
    List<Map.Entry<String, Integer>> finalList = new ArrayList<>(5);

    Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        return o2.getValue()-o1.getValue();
      }
    };

    Collections.sort(list, valueComparator);

    int size = 0;
    if (list.size() >= 5){
      size = 5;
    }
    else{
      size = list.size();
    }
    for (int i=0; i<size; i++){
      finalList.add(list.get(i));
    }

    return finalList;
  }

  @Override
  public List<Map.Entry<String, Float>> top5HighestSalaryCities() {
    // TODO Auto-generated method stub
    HashMap<String, Long> mapAge = new HashMap<>();
    HashMap<String, Long> countPeople = new HashMap<>();
    HashMap<String, Float> map = new HashMap<>();

    for (Person person : people){
      if (mapAge.containsKey(person.getCity())){
        mapAge.put(person.getCity(), mapAge.get(person.getCity()) + person.getSalary());
        countPeople.put(person.getCity(), countPeople.get(person.getCity()) + 1);
      }
      else{
        mapAge.put(person.getCity(), person.getSalary());
        countPeople.put(person.getCity(), 1L);
      }
    }

    for (Map.Entry<String, Long> age : mapAge.entrySet()){
      Float averageAge = (mapAge.get(age.getKey())).floatValue()/countPeople.get(age.getKey());
      map.put(age.getKey(), averageAge);
    }

    List<Map.Entry<String, Float>> list = new ArrayList<>(map.entrySet());
    List<Map.Entry<String, Float>> finalList = new ArrayList<>(5);

    Comparator<Map.Entry<String, Float>> valueComparator = new Comparator<Map.Entry<String, Float>>() {
      @Override
      public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
        if (o1.getValue() == o2.getValue()){
          return o1.getKey().compareTo(o2.getKey());
        }
        if (o1.getValue() > o2.getValue()){
          return -1;
        }
        return 1;
      }
    };

    Collections.sort(list, valueComparator);

    int size = 0;
    if (list.size() >= 5){
      size = 5;
    }
    else{
      size = list.size();
    }
    for (int i=0; i<size; i++){
      finalList.add(list.get(i));
    }

    return finalList;
  }
  
}
