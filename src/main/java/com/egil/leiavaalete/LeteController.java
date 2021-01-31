package com.egil.leiavaalete;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeteController {

    @RequestMapping(value="/egil")
    @GetMapping
    public String egilEndpoint(){
        return "egil";
    }
}
