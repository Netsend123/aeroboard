package aero.board.controllers;

import aero.board.ApiRequestAeroportLines;
import aero.board.JsonParsingFlifgtList;
import aero.board.model.Airport;
import aero.board.SearchAeroport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/first")
public class FirstController {
    SearchAeroport searchAeroport;
    List<Airport> airportsArray = new ArrayList<>();
//    public List<Airport> getAirportsArray() {
//
//
//            List<Airport> airportsArray = new ArrayList<>();
//
//            airportsArray.add(new Airport("vnukovo", "vko"));
//            airportsArray.add(new Airport("koltsovo", "svx"));
//            airportsArray.add(new Airport("sherenetevo", "srm"));
//
//
//
//            return airportsArray;
//
//    }

    //    @ModelAttribute
//    public void listOfPort(Model model) {
//        model.addAttribute("airports", airports);
//
//    }
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "city", required = false) String city, @RequestParam(value = "icao", required = false) String icao, Model model) {

        searchAeroport = new SearchAeroport();
        if (city != null) {
            searchAeroport.parse(city);
            airportsArray = searchAeroport.airportList;

            System.out.println(city);
            ApiRequestAeroportLines apiRequestAeroportLines = new ApiRequestAeroportLines();
            String err = "";
            String get = "";
            System.out.println(searchAeroport.airportList.size());

            if (searchAeroport.airportList.isEmpty()) {
                err = "Airport not found";

            } else if (airportsArray.size() == 1) {
                apiRequestAeroportLines.request(airportsArray.get(0).getIcao());
                get = new JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);
            } else if (airportsArray.size() > 1){
                model.addAttribute("airportsArray", airportsArray);
                while (icao == null) {}
                apiRequestAeroportLines.request(icao);
                get = new JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);

            }


            // String get = "[{\"number\":\"YC 6049  \",\"aeroport\":\"Orenburg  \",\"aircraft\":null,\"company\":\"Yamal  \",\"time\":\"2022-10-26 17:00+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"XC 8186  \",\"aeroport\":\"Antalya  \",\"aircraft\":\"Boeing 737-800\",\"company\":\"Corendon Air  \",\"time\":\"2022-10-26 17:02+05:00  \",\"status\":\"Departed  \"},{\"number\":\"FZ 902  \",\"aeroport\":\"Dubai City  \",\"aircraft\":\"Boeing 737-800\",\"company\":\"flydubai  \",\"time\":\"2022-10-26 17:06+05:00  \",\"status\":\"Departed  \"},{\"number\":\"WZ 1049  \",\"aeroport\":\"Orenburg  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Red Wings  \",\"time\":\"2022-10-26 17:09+05:00  \",\"status\":\"Departed  \"},{\"number\":\"SU 1403  \",\"aeroport\":\"Moscow  \",\"aircraft\":\"Boeing 737-8MC\",\"company\":\"Aeroflot  \",\"time\":\"2022-10-26 17:22+05:00  \",\"status\":\"Departed  \"},{\"number\":\"U6 365  \",\"aeroport\":\"Irkutsk  \",\"aircraft\":\"Airbus A320\",\"company\":\"Ural  \",\"time\":\"2022-10-26 17:31+05:00  \",\"status\":\"Departed  \"},{\"number\":\"FV 6418  \",\"aeroport\":\"Saint-Petersburg  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Rossiya  \",\"time\":\"2022-10-26 17:46+05:00  \",\"status\":\"Departed  \"},{\"number\":\"SU 6418  \",\"aeroport\":\"Saint-Petersburg  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Aeroflot  \",\"time\":\"2022-10-26 17:46+05:00  \",\"status\":\"Departed  \"},{\"number\":\"ZF 9693  \",\"aeroport\":\"Antalya  \",\"aircraft\":\"Boeing 767-300\",\"company\":\"AZUR Air  \",\"time\":\"2022-10-26 18:08+05:00  \",\"status\":\"Departed  \"},{\"number\":\"JU 7016  \",\"aeroport\":\"Moscow  \",\"aircraft\":null,\"company\":\"Air Serbia  \",\"time\":\"2022-10-26 18:45+05:00  \",\"status\":\"CanceledUncertain  \"},{\"number\":\"SU 1413  \",\"aeroport\":\"Moscow  \",\"aircraft\":\"Boeing 737-8MC\",\"company\":\"Aeroflot  \",\"time\":\"2022-10-26 18:52+05:00  \",\"status\":\"Departed  \"},{\"number\":\"U6 664  \",\"aeroport\":\"Saint-Petersburg  \",\"aircraft\":\"Airbus A320 NEO\",\"company\":\"Ural  \",\"time\":\"2022-10-26 18:58+05:00  \",\"status\":\"Departed  \"},{\"number\":\"U6 266  \",\"aeroport\":\"Moscow  \",\"aircraft\":\"Airbus A320\",\"company\":\"Ural  \",\"time\":\"2022-10-26 19:02+05:00  \",\"status\":\"Departed  \"},{\"number\":\"WZ 4205  \",\"aeroport\":\"Antalya  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Red Wings  \",\"time\":\"2022-10-26 19:38+05:00  \",\"status\":\"Departed  \"},{\"number\":\"WZ 1045  \",\"aeroport\":\"Makhachkala  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Red Wings  \",\"time\":\"2022-10-26 20:05+05:00  \",\"status\":\"Boarding  \"},{\"number\":\"IO 291  \",\"aeroport\":\"Baku  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"IrAero  \",\"time\":\"2022-10-26 20:15+05:00  \",\"status\":\"Delayed  \"},{\"number\":\"U6 270  \",\"aeroport\":\"Moscow  \",\"aircraft\":\"Airbus A320\",\"company\":\"Ural  \",\"time\":\"null  \",\"status\":\"CanceledUncertain  \"},{\"number\":\"SU 1405  \",\"aeroport\":\"Moscow  \",\"aircraft\":\"Airbus A320 NEO\",\"company\":\"Aeroflot  \",\"time\":\"2022-10-26 20:50+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"FV 6937  \",\"aeroport\":\"Yerevan  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Rossiya  \",\"time\":\"2022-10-26 21:05+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"S7 5020  \",\"aeroport\":\"Novosibirsk  \",\"aircraft\":\"Embraer 170\",\"company\":\"S7  \",\"time\":\"2022-10-26 21:05+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"SU 6937  \",\"aeroport\":\"Yerevan  \",\"aircraft\":null,\"company\":\"Aeroflot  \",\"time\":\"2022-10-26 21:05+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"UT 112  \",\"aeroport\":\"Surgut  \",\"aircraft\":\"ATR 72\",\"company\":\"UTAir  \",\"time\":\"2022-10-26 21:10+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"UT 280  \",\"aeroport\":\"Tyumen  \",\"aircraft\":\"ATR 72\",\"company\":\"UTAir  \",\"time\":\"2022-10-26 22:00+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"FV 6404  \",\"aeroport\":\"Saint-Petersburg  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Rossiya  \",\"time\":\"2022-10-26 22:10+05:00  \",\"status\":\"CheckIn  \"},{\"number\":\"SU 6404  \",\"aeroport\":\"Saint-Petersburg  \",\"aircraft\":\"Sukhoi Superjet 100\",\"company\":\"Aeroflot  \",\"time\":\"2022-10-26 22:10+05:00  \",\"status\":\"CheckIn  \"}]";

            model.addAttribute("messege", get);
            model.addAttribute("error", err);



        }
        return "first/hello";
    }

    @ModelAttribute
    public String getAirports(Model model, @ModelAttribute("airport") Airport airport) {
        model.addAttribute("airportsArray", airportsArray);

        return "airportsArray";
    }

}