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
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private Environment env;

    String stockURL = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=IBM&apikey=";

    @GetMapping("/feature1") // come back to
    public List<CompCSV> feature1 (RestTemplate restTemplate){

        // Sort later //

        List<CompCSV> tempCsvData = StockCsvParser.readCSV(); // change name later

        return tempCsvData;
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


}
