package com.computershop.service.impl;

import com.computershop.models.Monitor;
import com.computershop.repository.MonitorsRepository;
import com.computershop.service.MonitorsService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorsServiceImpl implements MonitorsService {

    private final MonitorsRepository monitorsRepository;
    private final EntityManagerFactory entityManagerFactory;

    public MonitorsServiceImpl(MonitorsRepository monitorsRepository, EntityManagerFactory entityManagerFactory) {
        this.monitorsRepository = monitorsRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Monitor save(Monitor monitor) {
        monitorsRepository.saveOrUpdateQuantity(monitor, entityManagerFactory);
        return monitor;
    }

    @Override
    public Monitor edit(Monitor monitor, String id) {
        monitorsRepository.edit(
                monitor.getDiagonal(),
                monitor.getSeries_num(),
                monitor.getManufacturer(),
                monitor.getCost(),
                monitor.getQuantity(),
                id);
        return monitor;
    }

    @Override
    public List<Monitor> getAll() {
        return monitorsRepository.findAll();
    }

    @Override
    public Monitor getById(String id) {
        return monitorsRepository.getReferenceById(Integer.parseInt(id));
    }

}
