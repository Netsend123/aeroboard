package aero.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HelloController {
    @GetMapping("/hello-world")
    public String sayHallo(){
        return "helloworld";
    }
}