package com.example.renyibang.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ServiceService {


    JSONObject searchServiceByPaging(String keyword, Pageable pageable, String timeBegin, String timeEnd, long priceLow, long priceHigh);

}
