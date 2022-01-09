package com.chopin.springmybatis.service;

import com.chopin.springmybatis.domain.City;

/**
 * Project spring-tutorials
 * Package com.chopin.springmybatis.service
 *
 * @author Chopin
 * @date 2022/1/9 22:24
 */
public interface CityService {
    /**
     * Get a city info
     * @param id City id
     * @return The result
     */
    City getCity(Integer id);
}
