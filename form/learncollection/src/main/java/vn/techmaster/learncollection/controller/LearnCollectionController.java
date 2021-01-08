package vn.techmaster.learncollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.techmaster.learncollection.model.Person;
import vn.techmaster.learncollection.repository.PersonRepositoryInterface;
import vn.techmaster.learncollection.request.Top5CitiesHaveMostSpecificJobRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LearnCollectionController {

    @Autowired
    PersonRepositoryInterface personRepository;

    @GetMapping(value = "/getall")
    public String getAll(Model model){
        model.addAttribute("listpeople", personRepository.getAll());
        return "getall";
    }

    @GetMapping(value = "/top5city")
    public String top5City(Model model){
        model.addAttribute("top5city", personRepository.findTop5Cities());
        return "top5city";
    }

    @GetMapping(value = "/top5job")
    public String top5Job(Model model){
        model.addAttribute("top5job", personRepository.findTop5Jobs());
        return "top5job";
    }

    @GetMapping(value = "/findtopjobincity")
    public String findTopJobInCity(Model model){
        model.addAttribute("findtopjobincity", personRepository.findTopJobInCity());
        return "findtopjobincity";
    }

    @GetMapping(value = "/groupjobbycount")
    public String groupJobByCount(Model model){
        model.addAttribute("groupjobbycount", personRepository.groupJobByCount());
        return "groupjobbycount";
    }

    @GetMapping(value = "/averagejobage")
    public String averageJobAge(Model model){
        model.addAttribute("averagejobage", personRepository.averageJobAge());
        return "averagejobage";
    }

    @GetMapping(value = "/averagecityage")
    public String averageCityAge(Model model){
        model.addAttribute("averagecityage", personRepository.averageCityAge());
        return "averagecityage";
    }

    @GetMapping(value = "/averagejobsalary")
    public String averageJobSalary(Model model){
        model.addAttribute("averagejobsalary", personRepository.averageJobSalary());
        return "averagejobsalary";
    }

    @GetMapping(value = "/top5highestsalarycity")
    public String top5HighestSalaryCity(Model model){
        model.addAttribute("top5highestsalarycity", personRepository.top5HighestSalaryCities());
        return "top5highestsalarycity";
    }

    @GetMapping(value = "/top5citieshavemostspecificjob")
    public String top5CityHaveMostSpecificJobRequest(Model model){
        model.addAttribute("alljob", personRepository.getAllJob());
        model.addAttribute("top5citieshavemostspecificjob",
                new Top5CitiesHaveMostSpecificJobRequest());
        return "top5citieshavemostspecificjob";
    }

    @PostMapping(value = "/top5citieshavemostspecificjob")
    public String top5CityHaveMostSpecificJobResult(
            @ModelAttribute Top5CitiesHaveMostSpecificJobRequest request,
            Model model){
        List<Map.Entry<String, Integer>> list = personRepository.find5CitiesHaveMostSpecificJob(request.getJob());
        model.addAttribute("alljob", personRepository.getAllJob());
        model.addAttribute("top5citieshavemostspecificjob", request);
        model.addAttribute("response", list);
        return "top5citieshavemostspecificjob";
    }
}
