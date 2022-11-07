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
import java.util.List;


@Controller
@RequestMapping("/first")
public class FirstController {
    SearchAeroport searchAeroport;
    List<Airport> airportsArray = new ArrayList<>();
    ApiRequestAeroportLines apiRequestAeroportLines = new ApiRequestAeroportLines();
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "city", required = false) String city, @RequestParam(value = "icao", required = false) String icao, Model model) {
        String err = "";
        String finalJsonToHtml = "";
        searchAeroport = new SearchAeroport();
        if (city != null) {
            searchAeroport.parse(city);
            airportsArray = searchAeroport.airportList;
            model.addAttribute("airportsArray", airportsArray);
            System.out.println(city);
            if (searchAeroport.airportList.isEmpty()) {
                err = "Airport not found";
            } else if (airportsArray.size() == 1) {
                apiRequestAeroportLines.request(airportsArray.get(0).getIcao());
                finalJsonToHtml = new JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);
            }
        }
        if (icao != null) {
            apiRequestAeroportLines.request(icao);
            finalJsonToHtml = new JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);
        }
        model.addAttribute("finalJsonToHtml", finalJsonToHtml);
        model.addAttribute("error", err);
        return "first/hello";
    }

    @ModelAttribute
    public String getAirports(Model model, @ModelAttribute("airport") Airport airport) {
        model.addAttribute("airportsArray", airportsArray);

        return "airportsArray";
    }

}