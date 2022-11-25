package aero.board.controllers;

import aero.board.ApiRequestAeroportLines;
import aero.board.JsonParsingFlifgtList;
import aero.board.model.Airport;
import aero.board.SearchAeroport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import java.util.List;


@Controller

public class FirstController {
    SearchAeroport searchAeroport;
    List<Airport> airportsArray = new ArrayList<>();
    ApiRequestAeroportLines apiRequestAeroportLines = new ApiRequestAeroportLines();

    @Autowired
    public FirstController(SearchAeroport searchAeroport) {
        this.searchAeroport = searchAeroport;
    }
    String airportNameForHtml = "";

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "icao", required = false) String icao,
                            @RequestParam(value = "inout", required = false) String inout, Model model) {
        String err = "";
        String finalJsonToHtml = "";
        String airportName = "";
        String listvisible = "hidden";



        if (city != null) {
            searchAeroport.airportList.clear();
            searchAeroport.parse(city);
            airportsArray = searchAeroport.airportList;

            System.out.println(searchAeroport.airportList);
            model.addAttribute("airportsArray", airportsArray);
            System.out.println(city);
            if (airportsArray.isEmpty()) {
                err = "Airport not found";
            } else if (airportsArray.size() == 1) {
                JsonParsingFlifgtList jsonParsingFlifgtList = new JsonParsingFlifgtList();
                airportNameForHtml = airportsArray.get(0).getName();
                apiRequestAeroportLines.request(airportsArray.get(0).getIcao());
                finalJsonToHtml = jsonParsingFlifgtList.parse(apiRequestAeroportLines.responseApiAeroLines);
                airportName = airportsArray.get(0).getName() + " |  Derpartures  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;

            } else {
                listvisible = "visible";

            }
        }
        if (icao != null) {
            apiRequestAeroportLines.request(icao);

            finalJsonToHtml = new JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);
            for (Airport airport : airportsArray) {
                if (airport.getIcao().equals(icao)) {
                    airportNameForHtml = airport.getName();

                    airportName = airport.getName() + "  |  Departures  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;

                }
            }

        }
        if (inout != null && inout.equals("arrivals") && apiRequestAeroportLines.responseApiAeroLines != null) {

            JsonParsingFlifgtList jsonParsingFlifgtList = new JsonParsingFlifgtList();
            jsonParsingFlifgtList.setInOut("arrivals");
            finalJsonToHtml = jsonParsingFlifgtList.parse(apiRequestAeroportLines.responseApiAeroLines);

            airportName = airportNameForHtml + " |  Arrivals  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;


        }
        if (inout != null && inout.equals("departures") && apiRequestAeroportLines.responseApiAeroLines != null) {
            JsonParsingFlifgtList jsonParsingFlifgtList = new JsonParsingFlifgtList();
            jsonParsingFlifgtList.setInOut("departures");
            finalJsonToHtml = jsonParsingFlifgtList.parse(apiRequestAeroportLines.responseApiAeroLines);

            airportName = airportNameForHtml + "  Departures  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;
        }

        model.addAttribute("finalJsonToHtml", finalJsonToHtml);
        model.addAttribute("error", err);
        model.addAttribute("airportName", airportName);
        model.addAttribute("listvisible", listvisible);
        return "aero";
    }

    @ModelAttribute
    public String getAirports(Model model, @ModelAttribute("airport") Airport airport) {
        model.addAttribute("airportsArray", airportsArray);

        return "airportsArray";
    }

}