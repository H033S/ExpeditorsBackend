package ttl.larku.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping ("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/error")
    public String error() {
        //return new ModelAndView("error.html");
//        return new ModelAndView("runJpql");
        return "runJpql";
    }
}
