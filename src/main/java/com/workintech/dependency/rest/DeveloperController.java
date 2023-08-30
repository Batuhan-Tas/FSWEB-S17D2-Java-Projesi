package com.workintech.dependency.rest;

import com.workintech.dependency.model.Developer;
import com.workintech.dependency.model.Experience;
import com.workintech.dependency.model.JuniorDeveloper;
import com.workintech.dependency.model.MidDeveloper;
import com.workintech.dependency.tax.DeveloperTax;
import com.workintech.dependency.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/developers")
public class DeveloperController {

    private Map<Integer, Developer> developers;
    private Taxable taxable;

    @PostConstruct
    public void init(){
        developers = new HashMap<>();
    }

    @Autowired
    public DeveloperController(@Qualifier("developerTax") Taxable taxable) {
        this.taxable = taxable;
    }

    @GetMapping("/")
    public List<Developer> get(){
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable int id){
        return developers.get(id);
    }

    @PostMapping("/")
    public Developer save(@RequestBody Developer developer){

        if(developer.getExperience() == Experience.JUNIOR) {
            Developer newDev = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary()*taxable.getSimpleTaxRate(),developer.getExperience());
            developers.put(developer.getId(),newDev);
            return developers.get(developer.getId());
        } else if(developer.getExperience() == Experience.MID){
            Developer newDev = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary()*taxable.getMiddleTaxRate(),developer.getExperience());
            developers.put(developer.getId(),newDev);
            return developers.get(developer.getId());
        } else if(developer.getExperience() == Experience.SENIOR){
            Developer newDev = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary()*taxable.getUpperTaxRate(),developer.getExperience());
            developers.put(developer.getId(),newDev);
            return developers.get(developer.getId());
        } else{
            return null;
        }

    }

    @PutMapping("/{id}")
    public Developer update(@PathVariable int id,@RequestBody Developer developer){
        if(!developers.containsKey(id)){
            System.out.println("No developer with such ID");
        }

        if(developer.getExperience() == Experience.JUNIOR) {
            Developer newDev = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary()*taxable.getSimpleTaxRate(),developer.getExperience());
            developers.put(developer.getId(),newDev);
            return developers.get(developer.getId());
        } else if(developer.getExperience() == Experience.MID){
            Developer newDev = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary()*taxable.getMiddleTaxRate(),developer.getExperience());
            developers.put(developer.getId(),newDev);
            return developers.get(developer.getId());
        } else if(developer.getExperience() == Experience.SENIOR){
            Developer newDev = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary()*taxable.getUpperTaxRate(),developer.getExperience());
            developers.put(developer.getId(),newDev);
            return developers.get(developer.getId());
        } else{
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public List<Developer> delete(@PathVariable int id){
        if(!developers.containsKey(id)){
            System.out.println("No developer with such ID");
        }

        developers.remove(id);
        return developers.values().stream().toList();
    }
}
