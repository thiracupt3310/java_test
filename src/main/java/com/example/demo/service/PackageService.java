package com.example.demo.service;

import com.example.demo.model.PackageModel;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackageService {

    private List<PackageModel> packageModels = new ArrayList<>();

    PackageService(){
        this.packageModels.add(new PackageModel("package1", 800, new Date()));
        this.packageModels.add(new PackageModel("package2", 700, new Date()));
        this.packageModels.add(new PackageModel("package3", 1000, new Date()));
    }

    public boolean addPackage(PackageModel packageModel){
        try {
            packageModel.setId(UUID.randomUUID().toString());
            packageModel.setCreate_date(new Date());
            this.packageModels.add(packageModel);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean editPackage(PackageModel packageModel, String id){
        try {
            packageModel.setId(id);
            packageModel.setCreate_date(new Date());
            for (PackageModel packageModel1 : this.packageModels){
                if (packageModel1.getId().equals(packageModel.getId())){
                    this.packageModels.set(this.packageModels.indexOf(packageModel1), packageModel);
                    break;
                }
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<PackageModel> getPackages(){
        return this.packageModels;
    }

    public List<PackageModel> searchById(List<PackageModel> packageModels, String id){
        return packageModels.stream()
                .filter(p -> p.getId().contains(id)).collect(Collectors.toList());
    }

    public List<PackageModel> searchByName(List<PackageModel> packageModels, String name){
        return packageModels.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public List<PackageModel> searchByMinPrice(List<PackageModel> packageModels, double min_price){
        return packageModels.stream()
                .filter(p -> p.getPrice_per_unit() >= min_price).collect(Collectors.toList());
    }

    public List<PackageModel> searchByMaxPrice(List<PackageModel> packageModels, double max_price){
        return packageModels.stream()
                .filter(p -> p.getPrice_per_unit() <= max_price).collect(Collectors.toList());
    }

    public List<PackageModel> searchByDateFrom(List<PackageModel> packageModels, Date date){
        return packageModels.stream()
                .filter(p -> p.getCreate_date().compareTo(date) >= 0).collect(Collectors.toList());
    }

    public List<PackageModel> searchByDateTo(List<PackageModel> packageModels, Date date){
        return packageModels.stream()
                .filter(p -> p.getCreate_date().compareTo(date) <= 0).collect(Collectors.toList());
    }

    public List<PackageModel> getResults(String id, String name, String min_price, String max_price, String date_from, String date_to, String sort_type){
        List<PackageModel> packageModels = this.getPackages();

        if (!id.isEmpty()){
            packageModels = this.searchById(packageModels, id);
        }

        if (!name.isEmpty()){
            packageModels = this.searchByName(packageModels, name);
        }

        if (!min_price.isEmpty()){
            double min_priceD = Double.parseDouble(min_price);
            packageModels = this.searchByMinPrice(packageModels, min_priceD);
        }

        if (!max_price.isEmpty()){
            double max_priceD = Double.parseDouble(max_price);
            packageModels = this.searchByMaxPrice(packageModels, max_priceD);
        }

        if (!date_from.isEmpty()){
            if (date_from.matches("\\d{4}-\\d+-\\d+")){
                try {
                    Date date_fromD = new SimpleDateFormat("yyyy-MM-dd").parse(date_from);
                    packageModels = this.searchByDateFrom(packageModels, date_fromD);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!date_to.isEmpty()){
            if (date_to.matches("\\d{4}-\\d+-\\d+")){

                try {
                    Date date_toD = new SimpleDateFormat("yyyy-MM-dd").parse(date_to);
                    packageModels = this.searchByDateTo(packageModels, date_toD);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        if (!sort_type.isEmpty()) {
            packageModels = this.bubbleSort(packageModels, sort_type);
        }

        return packageModels;
    }

    public boolean deletePackage(String id){
        for (PackageModel packageModel1 : this.packageModels){
            if (packageModel1.getId().equals(id)){
                this.packageModels.remove(packageModel1);
                break;
            }
        }
        return true;
    }

    public List<PackageModel> bubbleSort(List<PackageModel> packageModels, String type){
        for (int i = 0; i < packageModels.size(); i++){
            for (int j = 1; j < packageModels.size() - 1; j++){
                if (type.equalsIgnoreCase("id") && packageModels.get(j-1).getId().compareTo(packageModels.get(j).getId()) > 0){
                    PackageModel temp = packageModels.get(j-1);
                    packageModels.set(j-1, (packageModels.get(j)));
                    packageModels.set(j, temp);
                } else if (type.equalsIgnoreCase("name") && packageModels.get(j-1).getName().compareTo(packageModels.get(j).getName()) > 0){
                    PackageModel temp = packageModels.get(j-1);
                    packageModels.set(j-1, (packageModels.get(j)));
                    packageModels.set(j, temp);
                } else if (type.equalsIgnoreCase("price") && packageModels.get(j-1).getPrice_per_unit() > packageModels.get(j).getPrice_per_unit()){
                    PackageModel temp = packageModels.get(j-1);
                    packageModels.set(j-1, (packageModels.get(j)));
                    packageModels.set(j, temp);
                }  else if (type.equalsIgnoreCase("date") && packageModels.get(j-1).getCreate_date().compareTo(packageModels.get(j).getCreate_date()) > 0){
                    PackageModel temp = packageModels.get(j-1);
                    packageModels.set(j-1, (packageModels.get(j)));
                    packageModels.set(j, temp);
                }
            }
        }
        return packageModels;
    }
}
