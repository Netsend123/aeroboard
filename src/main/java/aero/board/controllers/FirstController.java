package aero.board.controllers;

import aero.board.ApiRequestAeroportLines;
import aero.board.JsonFromSearchList;
import aero.board.JsonParsingFlifgtList;
import aero.board.dao.SearchDAO;
import aero.board.model.Airport;
import aero.board.SearchAeroport;
import aero.board.model.DbObject;
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
    private final SearchDAO searchDAO;
    String airportNameForHtml = "";
    String cityDb;
    @Autowired
    public FirstController(SearchAeroport searchAeroport, SearchDAO searchDAO) {
        this.searchAeroport = searchAeroport;
        this.searchDAO = searchDAO;
    }



    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "icao", required = false) String icao,
                            @RequestParam(value = "inout", required = false) String inout, Model model) {

        String err = "";
        String finalJsonToHtml = "";
        String airportName = "";
        String listvisible = "hidden";
        String tableRadioVicible = "hidden";

        if (city != null) {

            cityDb = city;
            searchAeroport.airportList.clear();
            searchAeroport.parse(city);
            airportsArray = searchAeroport.airportList;
            System.out.println(searchAeroport.airportList);
            model.addAttribute("airportsArray", airportsArray);
            System.out.println(city);
            if (airportsArray.isEmpty()) {
                err = "Airport not found!";
            } else if (airportsArray.size() == 1) {
                JsonParsingFlifgtList jsonParsingFlifgtList = new JsonParsingFlifgtList();
                airportNameForHtml = airportsArray.get(0).getName();
                apiRequestAeroportLines.request(airportsArray.get(0).getIcao());
                searchDAO.saveSearch(new DbObject(city, airportsArray.get(0).getName()));
                tableRadioVicible = "visible";
                if (apiRequestAeroportLines.dataNotFound) {
                    err = "Airport " + airportsArray.get(0).getName() + " data not found!";

                } else {
                    finalJsonToHtml = jsonParsingFlifgtList.parse(apiRequestAeroportLines.responseApiAeroLines);
                    airportName = airportsArray.get(0).getName() + " |  DEPARTURES  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;
                }
            } else {
                listvisible = "visible";
            }
        }
        if (icao != null) {
            apiRequestAeroportLines.request(icao);
            tableRadioVicible = "visible";
            if (apiRequestAeroportLines.dataNotFound) {
                err = "Airport data not found!";
            } else {
                finalJsonToHtml = new JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);
                for (Airport airport : airportsArray) {
                    if (airport.getIcao().equals(icao)) {
                        airportNameForHtml = airport.getName();
                        searchDAO.saveSearch(new DbObject(cityDb, airportNameForHtml));
                        airportName = airport.getName() + "  |  DEPARTURES  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;
                    }
                }
            }
        }
        if (inout != null && inout.equals("arrivals") && apiRequestAeroportLines.responseApiAeroLines != null && !apiRequestAeroportLines.dataNotFound) {

            JsonParsingFlifgtList jsonParsingFlifgtList = new JsonParsingFlifgtList();
            jsonParsingFlifgtList.setInOut("arrivals");
            finalJsonToHtml = jsonParsingFlifgtList.parse(apiRequestAeroportLines.responseApiAeroLines);
            airportName = airportNameForHtml + " |  ARRIVALS  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;
        }
        if (inout != null && inout.equals("departures") && apiRequestAeroportLines.responseApiAeroLines != null && !apiRequestAeroportLines.dataNotFound) {
            JsonParsingFlifgtList jsonParsingFlifgtList = new JsonParsingFlifgtList();
            jsonParsingFlifgtList.setInOut("departures");
            finalJsonToHtml = jsonParsingFlifgtList.parse(apiRequestAeroportLines.responseApiAeroLines);
            airportName = airportNameForHtml + " |  DEPARTURES  |  local time: " + apiRequestAeroportLines.timeRange.currentTime;
        }
        model.addAttribute("finalJsonToHtml", finalJsonToHtml);
        model.addAttribute("error", err);
        model.addAttribute("airportName", airportName);
        model.addAttribute("listvisible", listvisible);
        model.addAttribute("tableRadioVicible", tableRadioVicible);
        return "aero";
    }

    @ModelAttribute
    public String getAirports(Model model, @ModelAttribute("airport") Airport airport) {
        model.addAttribute("airportsArray", airportsArray);
        return "airportsArray";
    }

    @GetMapping("/statistic")
    public String newPerson(Model model) {
        JsonFromSearchList json = new JsonFromSearchList();
        model.addAttribute("finalJsonToHtml", json.parse(searchDAO.listFromSearch()));
        return "statistic";
    }
}