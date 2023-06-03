package com.computershop.repository;

import com.computershop.models.Monitor;

import java.util.List;

public interface MonitorsRepository {
    List<Monitor> findAll();

    List<Monitor> findById(String id);

    Monitor save(Monitor monitor);
}
