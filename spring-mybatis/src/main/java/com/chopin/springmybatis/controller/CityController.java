package com.chopin.springmybatis.controller;

import com.chopin.springmybatis.domain.City;
import com.chopin.springmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project spring-tutorials
 * Package com.chopin.springmybatis.controller
 *
 * @author Chopin
 * @date 2022/1/9 22:48
 */
@RequestMapping("/app")
@RestController
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/city")
    public City getCityInfo(@RequestParam("id") Integer id) {
        return cityService.getCity(id);
    }
}