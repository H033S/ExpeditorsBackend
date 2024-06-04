package ttl.larku.controllers;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

   private PasswordEncoder passwordEncoder =
         PasswordEncoderFactories.createDelegatingPasswordEncoder();

   @GetMapping("/encode/{pw}")
   public String encodePassword(@PathVariable("pw") String password) {
      return passwordEncoder.encode(password);
   }
}
