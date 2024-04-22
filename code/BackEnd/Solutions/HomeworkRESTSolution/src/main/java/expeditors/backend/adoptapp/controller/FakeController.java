package expeditors.backend.adoptapp.controller;

import expeditors.backend.adoptapp.service.FakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fakeit")
public class FakeController {

   @Autowired
   private FakeService fakeService;

   @GetMapping
   public String getStuff() {
      return fakeService.getStuff();
   }
}
