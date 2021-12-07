package com.careerdevs.Controllers;
    ///////////////////////////// Imports ///////////////////////////////////////////////
import com.careerdevs.Models.CompAV;
import com.careerdevs.Models.CompCSV;
import com.careerdevs.Utils.StockCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
    /////////////////////////////////////////////////////////////////////////////////////
@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private Environment env;

    String stockURL = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=IBM&apikey=";

    ///////////////////////////// GetMapping Methods///////////////////////////////////////////////

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

    @GetMapping("/feature2")
    public List<CompCSV> feature2 (RestTemplate restTemplate){

        List<CompCSV> nameData = StockCsvParser.readCSV();

        assert nameData != null;

        nameData.sort(Comparator.comparing(CompCSV::getIpoDate));

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

    @GetMapping("/feature6")
    public List<CompAV> compMarketcapInfo (RestTemplate restTemplate){

        ArrayList<CompCSV> csvdata = StockCsvParser.readCSV();
        ArrayList<CompAV> allcompdata = new ArrayList<>();

        assert csvdata != null;

        //csvdata.sort(Comparator.comparing(CompAV::getMarketCapitalization));

        for (CompCSV compData : csvdata){

            CompAV compApiData = restTemplate.getForObject("https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + compData.getSymbol() + "&apikey=" + env.getProperty("stock.key"), CompAV.class);

            assert compApiData != null;

            CompAV trimmedData = CompAV.feature6(compApiData.getSymbol(), compApiData.getName(), compApiData.getMarketCapitalization());

            allcompdata.add(trimmedData);

        }

        allcompdata.sort(new SortByNumber());
        Collections.reverse(allcompdata);

        return allcompdata;
    }

    @GetMapping("/feature7") // TODO: Sort by dividend date!
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

    ///////////////////////////// Sorting Methods///////////////////////////////////////////////
    public static class SortByName implements Comparator<CompCSV> {

        public int compare(CompCSV a, CompCSV b)
        {
            return a.getName().compareTo(b.getName());
        }

    }

    public static class SortByNumber implements Comparator<CompAV> {

        public int compare(CompAV a, CompAV b)
        {

            Long mc1 = Long.parseLong(a.getMarketCapitalization());
            Long mc2 = Long.parseLong(a.getMarketCapitalization());
            return Long.compare(mc1, mc2);

        }

    }

    public static class SortByIpoDate implements Comparator<CompCSV> {

        public int compare(CompCSV a, CompCSV b)
        {
            return a.getIpoDate().compareTo(b.getIpoDate());
        }

    }

    public static class SortByDividendDate implements Comparator<CompAV> {

        public int compare(CompAV a, CompAV b)
        {
            return a.getDividendDate().compareTo(b.getDividendDate());
        }

    } //TODO: apply date object!

}
