package com.computershop.repository;

import com.computershop.models.HardDisc;
import com.computershop.models.Laptop;

import java.util.List;

public interface LaptopsRepository {
    List<Laptop> findAll();

    List<Laptop> findById(String id);

    Laptop save(Laptop laptop);
}
