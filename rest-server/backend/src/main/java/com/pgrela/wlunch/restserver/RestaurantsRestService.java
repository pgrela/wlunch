package com.pgrela.wlunch.restserver;


import com.pgrela.wlunch.hunger.ErrandBoy;
import com.pgrela.wlunch.hunger.MenuBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsRestService {

    private final ErrandBoy errandBoy;

    private final MenuBoard menuBoard;

    @Autowired
    public RestaurantsRestService(ErrandBoy errandBoy, MenuBoard menuBoard) {
        this.errandBoy = errandBoy;
        this.menuBoard = menuBoard;
    }

    @PostConstruct
    public void loadMenus() {
        errandBoy.getMenuForToday();
    }

    @GetMapping(value = "")
    public ResponseEntity<List<String>> restaurants() {
        return new ResponseEntity<>(errandBoy.whatRestaurantsDoYouKnow(), HttpStatus.OK);
    }
}
