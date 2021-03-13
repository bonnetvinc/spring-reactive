package poc.vb.springreactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactiveController {


  @GetMapping("/pay")
  ResponseEntity<String> pay() {
    return ResponseEntity.ok("you paid");
  }

}