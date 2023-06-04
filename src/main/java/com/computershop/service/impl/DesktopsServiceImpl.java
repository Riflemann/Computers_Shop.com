package com.computershop.service.impl;

import com.computershop.models.Desktops;
import com.computershop.repository.DesktopsRepository;
import com.computershop.service.DesktopsService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DesktopsServiceImpl implements DesktopsService {

    private final DesktopsRepository desktopsRepository;
    private final EntityManagerFactory entityManagerFactory;

    public DesktopsServiceImpl(DesktopsRepository desktopsRepository, EntityManagerFactory entityManagerFactory) {
        this.desktopsRepository = desktopsRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Desktops save(@RequestBody @Valid Desktops desktops) {
        desktopsRepository.saveOrUpdateQuantity(desktops, entityManagerFactory);
        return desktops;
    }

    @Override
    public Desktops edit(Desktops desktops, String id) {
        desktopsRepository.edit(
                desktops.getType_desktops(),
                desktops.getSeries_num(),
                desktops.getManufacturer(),
                desktops.getCost(),
                desktops.getQuantity(),
                id);
        return desktops;
    }

    @Override
    public List<Desktops> getAll() {
        return desktopsRepository.findAll();
    }

    @Override
    public Desktops getById(String id) {
        return desktopsRepository.getReferenceById(Integer.parseInt(id));
    }
}
