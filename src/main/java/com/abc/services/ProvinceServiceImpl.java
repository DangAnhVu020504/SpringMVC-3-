package com.abc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc.dao.ProvinceDAO;
import com.abc.entities.Province;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceDAO provinceDAO;

    @Override
    public List<Province> getAllProvinces() {
        return provinceDAO.getAllProvinces();
    }

    @Override
    public Province getProvinceById(int id) {
        return provinceDAO.getProvinceById(id);
    }
}
