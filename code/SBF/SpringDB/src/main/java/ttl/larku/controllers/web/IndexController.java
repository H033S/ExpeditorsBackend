package ttl.larku.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/")
    public String slash() {
        return "redirect:/admin";
    }

    @GetMapping({"/admin"})
    public ModelAndView index() {
        return new ModelAndView("startPage");
    }

    @GetMapping({"/error", "/eek"})
    public ModelAndView error(@RequestParam(name = "message",
          required = false) String message) {
        return new ModelAndView("error", "message", message);
    }

}
