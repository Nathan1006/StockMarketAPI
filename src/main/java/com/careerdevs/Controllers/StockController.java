package com.careerdevs.Controllers;

import com.careerdevs.Models.CompAV;
import com.careerdevs.Models.CompCSV;
import com.careerdevs.Parsers.StockCsvParser;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private Environment env;

    String stockURL = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=IBM&apikey=";

    @GetMapping("/feature1")
    public List<CompCSV> feature1 (RestTemplate restTemplate){

        List<CompCSV> tempCsvData = StockCsvParser.readCSV(); // change name later

        assert tempCsvData != null;

        tempCsvData.sort(Comparator.comparing(CompCSV::getName));

        for (CompCSV comp : tempCsvData) {

            comp.setAssetType(null);
            comp.setIpoDate(null);
            comp.setDelistingDate(null);
            comp.setStatus(null);

        }

        return tempCsvData;
    }

    @GetMapping("/feature2") // Sort by date
    public List<CompCSV> feature2 (RestTemplate restTemplate){

        List<CompCSV> nameData = StockCsvParser.readCSV();

        assert nameData != null;

        nameData.sort(Comparator.comparing(CompCSV::getName));

        for (CompCSV comp : nameData) {

            comp.setAssetType(null);
            comp.setExchange(null);
            comp.setDelistingDate(null);
            comp.setStatus(null);
            comp.setSymbol(null);

        }

        return nameData;
    }

    @GetMapping("/feature3")
    public List<CompCSV> feature3 (RestTemplate restTemplate){

        List<CompCSV> nasData = StockCsvParser.readCSV();

        assert nasData != null;

        nasData.sort(Comparator.comparing(CompCSV::getName));

        for (CompCSV comp : nasData) {

            switch (comp.getExchange()) {
                case "NYSE":
                    comp.setName(null);
                    comp.setExchange(null);
                    comp.setSymbol(null);
                    comp.setAssetType(null);
                    comp.setIpoDate(null);
                    comp.setDelistingDate(null);
                    comp.setStatus(null);
                    break;
            }
        }

        return nasData;
    }

    @GetMapping("/feature4")
    public List<CompCSV> feature4 (RestTemplate restTemplate){

        List<CompCSV> nyseData = StockCsvParser.readCSV();

        assert nyseData != null;

        nyseData.sort(Comparator.comparing(CompCSV::getName));

        for (CompCSV comp : nyseData) {

            switch (comp.getExchange()) {
                case "NASDAQ":
                    comp.setName(null);
                    comp.setExchange(null);
                    comp.setSymbol(null);
                    comp.setAssetType(null);
                    comp.setIpoDate(null);
                    comp.setDelistingDate(null);
                    comp.setStatus(null);
                    break;
            }
        }


        return nyseData;
    }

    @GetMapping("/feature5") // return ALL the companies instead of 1
    public List<CompAV> compOVInfo (RestTemplate restTemplate){

        List<CompCSV> csvdata = StockCsvParser.readCSV();
        List<CompAV> allcompdata = new ArrayList<>();

        for (CompCSV compData : csvdata){

            System.out.println(compData.getSymbol());
            String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + compData.getSymbol();

            CompAV compApiData = restTemplate.getForObject(url, CompAV.class);
            allcompdata.add(compApiData);

        }

        return allcompdata;
    }



    // Sorting Methods
    public static class SortByName implements Comparator<CompCSV> {

        public int compare(CompCSV a, CompCSV b)
        {
            return a.getName().compareTo(b.getName());
        }
    }

}
