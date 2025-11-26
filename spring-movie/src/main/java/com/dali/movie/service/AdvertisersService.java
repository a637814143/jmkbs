package com.dali.movie.service;

import com.dali.movie.mapper.AdvertisersInfoMapper;
import com.dali.movie.model.AdvertisersInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisersService {
    @Autowired
    private AdvertisersInfoMapper advertisersInfoMapper;

    public AdvertisersInfo getIdAdvertiser(Long id) {
        return advertisersInfoMapper.getIdAdvertiser(id);
    }

    public AdvertisersInfo getNameAdvertiser(String companyName) {
        return advertisersInfoMapper.getNameAdvertiser(companyName);
    }
}
