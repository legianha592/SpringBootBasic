package vn.techmaster.bmi.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class MoneyConverter {
  Map<String, Float> myMap = new HashMap<>();

  public MoneyConverter() {
    this.parseExchangeRate();
  }

  public void parseExchangeRate() {
    try {
      File file = ResourceUtils.getFile("classpath:static/exchange_rate.json");
      FileReader reader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(reader);
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode masterNode = objectMapper.readTree(bufferedReader);

      Iterator<Map.Entry<String, JsonNode>> iter = masterNode.fields();
      while (iter.hasNext()) {
         var node = iter.next();
         myMap.put(node.getKey(), Float.parseFloat(node.getValue().toString()));
//         System.out.println(node.getKey() + " = " +  Float.parseFloat(node.getValue().toString()));
      }

    } catch (FileNotFoundException e) {
      System.out.println(e);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public Map<String, Float> getMap() {
    return myMap;
  }
}
