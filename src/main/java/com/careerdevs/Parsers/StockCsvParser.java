package com.careerdevs.Parsers;

import com.careerdevs.Models.CompCSV;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockCsvParser {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(readCSV());
    }
    public static ArrayList<CompCSV> readCSV() {
        try {
            CSVReader reader = new CSVReader(new FileReader("src/dataset2.csv"), ',');
            ArrayList<CompCSV> allCompanies = new ArrayList<>();
            // read line by line
            String[] record = null;
            while ((record = reader.readNext()) != null) {
                //symbol,name,exchange,assetType,ipoDate,delistingDate,status
                CompCSV company = new CompCSV();
                company.setSymbol(record[0]);
                company.setName(record[1]);
                company.setExchange(record[2]);
                company.setAssetType(record[3]);
                company.setIpoDate(record[4]);
                company.setDelistingDate(record[5]);
                company.setStatus(record[6]);
                allCompanies.add(company);
            }
            reader.close();
            return allCompanies;
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        } catch (IOException e) {
            System.out.println("ERROR READING DATA");
        }
        return null;
    }
}