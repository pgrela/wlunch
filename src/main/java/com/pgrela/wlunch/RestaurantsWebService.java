package com.pgrela.wlunch;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebService;

@RestController
@RequestMapping("/data")
public class RestaurantsWebService {
    @RequestMapping("menu")
    public String menu(){
        return "Menu na dziś";
    }
}
