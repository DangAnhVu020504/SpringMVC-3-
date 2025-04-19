package com.abc.services;

import java.util.List;
import com.abc.entities.Province;

public interface ProvinceService {
    List<Province> getAllProvinces();
    Province getProvinceById(int id);
}
