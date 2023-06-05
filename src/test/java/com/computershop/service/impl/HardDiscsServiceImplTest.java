package com.computershop.service.impl;

import com.computershop.models.HardDisc;
import com.computershop.models.enums.HardDriveVolumes;
import com.computershop.repository.HardDiscsRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.computershop.models.enums.HardDriveVolumes.GB128;

public class HardDiscsServiceImplTest {

    @Mock
    private HardDiscsRepository hardDiscsRepository;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    private HardDiscsServiceImpl hardDiscsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hardDiscsService = new HardDiscsServiceImpl(hardDiscsRepository, entityManagerFactory);
    }

    @Test
    public void testSave() {
        HardDisc hardDisc = new HardDisc();
        hardDisc.setHardDriveVolumes(GB128);
        hardDisc.setSeries_num(123456);
        hardDisc.setManufacturer("Samsung");
        hardDisc.setCost(100);
        hardDisc.setQuantity(10);

        HardDisc result = hardDiscsService.save(hardDisc);

        Assertions.assertEquals(hardDisc, result);

        Mockito.verify(hardDiscsRepository).saveOrUpdateQuantity(hardDisc, entityManagerFactory);

    }

    @Test
    public void testEdit() {
        String id = "1";
        HardDisc hardDisc = new HardDisc();
        hardDisc.setHardDriveVolumes(GB128);
        hardDisc.setSeries_num(123456);
        hardDisc.setManufacturer("Samsung");
        hardDisc.setCost(100);
        hardDisc.setQuantity(10);

        HardDisc result = hardDiscsService.edit(hardDisc, id);

        Assertions.assertEquals(hardDisc, result);

        Mockito.verify(hardDiscsRepository).edit(hardDisc.getHardDriveVolumes(),
                hardDisc.getSeries_num(),
                hardDisc.getManufacturer(),
                hardDisc.getCost(),
                hardDisc.getQuantity(),
                id);

    }

    @Test
    public void testGetAll() {
        List<HardDisc> hardDiscList = new ArrayList<>();
        HardDisc hardDisc1 = new HardDisc();
        hardDisc1.setHardDriveVolumes(GB128);
        hardDisc1.setSeries_num(123456);
        hardDisc1.setManufacturer("Samsung");
        hardDisc1.setCost(100);
        hardDisc1.setQuantity(10);
        hardDiscList.add(hardDisc1);

        HardDisc hardDisc2 = new HardDisc();
        hardDisc2.setHardDriveVolumes(GB128);
        hardDisc2.setSeries_num(789012);
        hardDisc2.setManufacturer("Western Digital");
        hardDisc2.setCost(150);
        hardDisc2.setQuantity(5);
        hardDiscList.add(hardDisc2);

        Mockito.when(hardDiscsRepository.findAll()).thenReturn(hardDiscList);

        List<HardDisc> result = hardDiscsService.getAll();

        Assertions.assertEquals(hardDiscList, result);
    }

    @Test
    public void testGetById() {
        String id = "1";
        HardDisc hardDisc = new HardDisc();
        hardDisc.setHardDriveVolumes(GB128);
        hardDisc.setSeries_num(123456);
        hardDisc.setManufacturer("Samsung");
        hardDisc.setCost(100);
        hardDisc.setQuantity(10);

        Mockito.when(hardDiscsRepository.getReferenceById(Integer.parseInt(id))).thenReturn(hardDisc);

        HardDisc result = hardDiscsService.getById(id);

        Assertions.assertEquals(hardDisc, result);
    }
}