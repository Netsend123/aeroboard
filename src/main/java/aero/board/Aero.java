package aero.board;

public class Aero {
    public static void main(String[] args){
 //       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

//        aero.board.SearchAeroport searchAeroport = context.getBean(aero.board.SearchAeroport.class);
//        searchAeroport.parse();
//        aero.board.ApiRequestAeroportLines apiRequestAeroportLines = context.getBean(aero.board.ApiRequestAeroportLines.class);
//        apiRequestAeroportLines.request(searchAeroport.icao);
//        new aero.board.JsonParsingFlifgtList().parse(apiRequestAeroportLines.responseApiAeroLines);
   new Aero().time();
    }
    public void time () {
        String time= "2022-10-26 22:40+05:00";
        time = time.split("\\+")[0];
       // time = time.split("-")[1];
        System.out.println(time);
    }
}
