package com.example.demo.controller;

import com.example.demo.model.PackageModel;
import com.example.demo.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("name")
public class PackageController {

    @Autowired
    PackageService packageService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "id", defaultValue = "") String id ,
                        @RequestParam(value = "name", defaultValue = "") String name ,
                        @RequestParam(value = "min_price", defaultValue = "") String min_price ,
                        @RequestParam(value = "max_price", defaultValue = "") String max_price ,
                        @RequestParam(value = "date_from", defaultValue = "") String date_from ,
                        @RequestParam(value = "date_to", defaultValue = "") String date_to,
                        @RequestParam(value = "search_type", defaultValue = "") String search_type

    ){
        model.addAttribute("input_id", id);
        model.addAttribute("input_name", name);
        model.addAttribute("input_min_price", min_price);
        model.addAttribute("input_max_price", max_price);
        model.addAttribute("input_date_from", date_from);
        model.addAttribute("input_date_to", date_to);
        model.addAttribute("packages", this.packageService.getResults(id, name, min_price, max_price, date_from, date_to, search_type));

        return "packages";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreate(Model model){
        model.addAttribute("packageModel", new PackageModel("", 0, new Date()));
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String postCreate(Model model, @ModelAttribute PackageModel packageModel){
        this.packageService.addPackage(packageModel);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getCreate(Model model, @PathVariable String id){
        List<PackageModel> packageModels = this.packageService.getResults(id, "", "", "", "", "", "");
        if (packageModels.size() > 0){
            model.addAttribute("packageModel", packageModels.get(0));
        }

        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String putCreate(Model model, @ModelAttribute PackageModel packageModel, @PathVariable String id){
        packageModel.setId(id);
        this.packageService.editPackage(packageModel, id);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteCreate(Model model, @PathVariable String id){
        this.packageService.deletePackage(id);
        return "redirect:/";
    }

}
