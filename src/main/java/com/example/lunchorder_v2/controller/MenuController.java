package com.example.lunchorder_v2.controller;


import com.example.lunchorder_v2.model.Menu;
import com.example.lunchorder_v2.model.Order;
import com.example.lunchorder_v2.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;


    @GetMapping("/daily/menu")
    public ResponseEntity<List<Menu>> getMenu(){
        try {
            List<Menu> menu = new ArrayList<Menu>();

            menuRepository.findAll().forEach(menu::add);

            if (menu.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
