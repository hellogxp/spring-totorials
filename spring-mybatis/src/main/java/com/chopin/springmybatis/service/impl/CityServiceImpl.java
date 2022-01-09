package com.chopin.springmybatis.service.impl;

import com.chopin.springmybatis.domain.City;
import com.chopin.springmybatis.mapper.CityMapper;
import com.chopin.springmybatis.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project spring-tutorials
 * Package com.chopin.springmybatis.service.impl
 *
 * @author Chopin
 * @date 2022/1/9 22:43
 */
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;
    /**
     * Get a city info
     *
     * @param id City id
     * @return The result
     */
    @Override
    public City getCity(Integer id) {
        return cityMapper.selectByPrimaryKey(id);
    }
}