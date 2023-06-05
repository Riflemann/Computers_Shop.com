package com.computershop.service;

import com.computershop.models.Laptop;

import java.util.List;

public interface LaptopsService {
    Laptop save(Laptop laptop);

    Laptop edit(Laptop laptop, String id);

    List<Laptop> getAll();

    Laptop getById(String id);
}
