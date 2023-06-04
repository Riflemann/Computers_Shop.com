package com.computershop.service;

import com.computershop.models.Monitor;

import java.util.List;

public interface MonitorsService {
    Monitor save(Monitor monitor);

    Monitor edit(Monitor monitor, String id);

    List<Monitor> getAll();

    Monitor getById(String id);
}
