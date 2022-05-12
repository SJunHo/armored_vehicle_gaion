package kr.gaion.railroad2;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticWebController implements ErrorController {
  @RequestMapping(path = "/error")
  public String error404() { return "/index.html"; }
}
