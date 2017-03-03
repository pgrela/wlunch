package com.pgrela.wlunch.server;


import com.pgrela.wlunch.hunger.ErrandBoy;
import com.pgrela.wlunch.hunger.MenuBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/")
public class RestaurantsWebService {

    private final ErrandBoy errandBoy;

    private final MenuBoard menuBoard;

    @Autowired
    public RestaurantsWebService(ErrandBoy errandBoy, MenuBoard menuBoard) {
        this.errandBoy = errandBoy;
        this.menuBoard = menuBoard;
    }

    @PostConstruct
    public void loadMenus(){
        errandBoy.getMenuForToday();
    }

    @RequestMapping(value = "restaurants")
    public String restaurant(ModelMap modelMap){
        modelMap.addAttribute("restaurantsList", errandBoy.whatRestaurantsDoYouKnow());
        return "menu";
    }
    @RequestMapping(value = "restaurants/{restaurantName}")
    public String restaurant(ModelMap modelMap, @PathVariable("restaurantName") String restaurantName){
        modelMap.addAttribute("menu", menuBoard.getMenu(restaurantName));
        modelMap.addAttribute("restaurantName", restaurantName);
        return "restaurant";
    }
}
