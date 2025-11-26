package com.dali.movie.controller;

import com.dali.movie.model.AdvertisersInfo;
import com.dali.movie.service.AdvertisersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/advertiser")
@RestController
public class AdvertisersController {
    @Autowired
    private AdvertisersService advertisersService;

    @RequestMapping("/getIdAdvertiser")
    public AdvertisersInfo getIdAdvertiser(Long id) {
        log.info("getIdAdvertiser,id:{}",id);
        AdvertisersInfo advertisersInfo = advertisersService.getIdAdvertiser(id);
        return advertisersInfo;
    }

    @RequestMapping("/getNameAdvertiser")
    public AdvertisersInfo getNameAdvertiser(String companyName) {
        log.info("getNameAdvertiser,companyName:{}",companyName);
        AdvertisersInfo advertisersInfo = advertisersService.getNameAdvertiser(companyName);
        return advertisersInfo;
    }
}
