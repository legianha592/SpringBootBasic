package vn.techmaster.bmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.techmaster.bmi.request.MoneyRequest;
import vn.techmaster.bmi.response.MoneyResult;
import vn.techmaster.bmi.service.MoneyConverter;

import java.util.*;


@Controller
public class MoneyController {

  @Autowired
  private MoneyConverter moneyConverter;

  @GetMapping(value = "/money")
  public String getMoneyHome(Model model){
    List<String> list = new ArrayList<>();
    list.addAll(moneyConverter.getMap().keySet());
    Collections.sort(list, Comparator.comparing(o -> o));

    model.addAttribute("moneyconverter", list);
    model.addAttribute("moneyrequest", new MoneyRequest());
    return "money";
  }

  @PostMapping(value = "/money")
  public String getMoneyConverter(@ModelAttribute MoneyRequest request, Model model){
    Float moneyChanged = handleMoney(request.getMoneyCodeToChange(), request.getMoneyToChange(),
            request.getMoneyCodeChanged());
    List<String> list = new ArrayList<>();
    list.addAll(moneyConverter.getMap().keySet());
    Collections.sort(list, Comparator.comparing(o -> o));

    model.addAttribute("moneyconverter", list);
    model.addAttribute("moneyrequest", request);
    model.addAttribute("moneyresult", new MoneyResult(moneyChanged));
    return "money";
  }

  private float handleMoney(String moneyCodeToChange, float moneyToChange, String moneyCodeChanged){
    return Float.valueOf(moneyToChange/moneyConverter.getMap().get(moneyCodeToChange)
            *moneyConverter.getMap().get(moneyCodeChanged));
  }
}
