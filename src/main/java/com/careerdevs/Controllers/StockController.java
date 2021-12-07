package com.careerdevs.Controllers;

import ch.qos.logback.classic.pattern.CallerDataConverter;
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

    @GetMapping("/feature2") // Sort by date!!!
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

    @GetMapping("/feature5")
    public List<CompAV> compOVInfo (RestTemplate restTemplate){

        ArrayList<CompCSV> csvdata = StockCsvParser.readCSV();
        ArrayList<CompAV> allcompdata = new ArrayList<>();

        assert csvdata != null;

        for (CompCSV compData : csvdata){

            CompAV compApiData = restTemplate.getForObject("https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + compData.getSymbol() + "&apikey=" + env.getProperty("stock.key"), CompAV.class);

            assert compApiData != null;

            CompAV trimmedData = CompAV.feature5(compApiData.getSymbol(), compApiData.getAssetType(), compApiData.getName(), compApiData.getDescription(), compApiData.getAddress());

            allcompdata.add(trimmedData);

        }

        return allcompdata;
    }

    @GetMapping("/feature6") // Sort by highest to lowest!!
    public List<CompAV> compMarketcapInfo (RestTemplate restTemplate){

        ArrayList<CompCSV> csvdata = StockCsvParser.readCSV();
        ArrayList<CompAV> allcompdata = new ArrayList<>();

        assert csvdata != null;

        for (CompCSV compData : csvdata){

            CompAV compApiData = restTemplate.getForObject("https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + compData.getSymbol() + "&apikey=" + env.getProperty("stock.key"), CompAV.class);

            assert compApiData != null;

            CompAV trimmedData = CompAV.feature6(compApiData.getSymbol(), compApiData.getName(), compApiData.getMarketCapitalization());

            allcompdata.add(trimmedData);

        }

        return allcompdata;
    }

    @GetMapping("/feature7")
    public List<CompAV> compDividendData (RestTemplate restTemplate){

        ArrayList<CompCSV> csvdata = StockCsvParser.readCSV();
        ArrayList<CompAV> allcompdata = new ArrayList<>();

        assert csvdata != null;

        for (CompCSV compData : csvdata){

            CompAV compApiData = restTemplate.getForObject("https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + compData.getSymbol() + "&apikey=" + env.getProperty("stock.key"), CompAV.class);

            assert compApiData != null;

            CompAV trimmedData = CompAV.feature7(compApiData.getSymbol(), compApiData.getName(), compApiData.getDividendDate());

            allcompdata.add(trimmedData);

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
