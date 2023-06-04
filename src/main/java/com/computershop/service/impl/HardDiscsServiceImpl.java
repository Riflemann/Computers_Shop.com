package com.computershop.service.impl;

import com.computershop.models.HardDisc;
import com.computershop.repository.HardDiscsRepository;
import com.computershop.service.HardDiscsService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardDiscsServiceImpl implements HardDiscsService {

    private final HardDiscsRepository hardDiscsRepository;
    private final EntityManagerFactory entityManagerFactory;

    public HardDiscsServiceImpl(HardDiscsRepository hardDiscsRepository, EntityManagerFactory entityManagerFactory) {
        this.hardDiscsRepository = hardDiscsRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public HardDisc save(HardDisc hardDisc) {
        hardDiscsRepository.saveOrUpdateQuantity(hardDisc, entityManagerFactory);
        return hardDisc;
    }

    @Override
    public HardDisc edit(HardDisc hardDisc, String id) {
        hardDiscsRepository.edit(
                hardDisc.getHardDriveVolumes(),
                hardDisc.getSeries_num(),
                hardDisc.getManufacturer(),
                hardDisc.getCost(),
                hardDisc.getQuantity(),
                id);
        return hardDisc;
    }

    @Override
    public List<HardDisc> getAll() {
        return hardDiscsRepository.findAll();
    }

    @Override
    public HardDisc getById(String id) {
        return hardDiscsRepository.getReferenceById(Integer.parseInt(id));
    }

}
