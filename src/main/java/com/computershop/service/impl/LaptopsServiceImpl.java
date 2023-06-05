package com.computershop.service.impl;

import com.computershop.models.Laptop;
import com.computershop.repository.LaptopsRepository;
import com.computershop.service.LaptopsService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaptopsServiceImpl implements LaptopsService {
    private final LaptopsRepository laptopsRepository;
    private final EntityManagerFactory entityManagerFactory;

    public LaptopsServiceImpl(LaptopsRepository laptopsRepository, EntityManagerFactory entityManagerFactory) {
        this.laptopsRepository = laptopsRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Laptop save(Laptop laptop) {
        laptopsRepository.saveOrUpdateQuantity(laptop, entityManagerFactory);
        return laptop;
    }

    @Override
    public Laptop edit(Laptop laptop, String id) {
        laptopsRepository.edit(
                laptop.getDiagonal(),
                laptop.getSeries_num(),
                laptop.getManufacturer(),
                laptop.getCost(),
                laptop.getQuantity(),
                id);
        return laptop;
    }

    @Override
    public List<Laptop> getAll() {
        return laptopsRepository.findAll();
    }

    @Override
    public Laptop getById(String id) {
        return laptopsRepository.getReferenceById(Integer.parseInt(id));
    }

}
