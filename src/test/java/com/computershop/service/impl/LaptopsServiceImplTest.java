package com.computershop.service.impl;

import com.computershop.models.Laptop;
import com.computershop.models.enums.Diagonal;
import com.computershop.repository.LaptopsRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LaptopsServiceImplTest {

    @Mock
    private LaptopsRepository laptopsRepository;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @InjectMocks
    private LaptopsServiceImpl laptopsService;

    @Test
    public void testSave() {
        Laptop laptop = new Laptop();
        laptop.setDiagonal(Diagonal.DIAGONAL_13);
        laptop.setSeries_num(123);
        laptop.setManufacturer("Dell");
        laptop.setCost(999.99);
        laptop.setQuantity(10);

//        when(laptopsRepository.saveOrUpdateQuantity(laptop, entityManagerFactory)).thenReturn(laptop);

        Laptop savedLaptop = laptopsService.save(laptop);

        verify(laptopsRepository).saveOrUpdateQuantity(laptop, entityManagerFactory);

        assertEquals(laptop, savedLaptop);
    }

    @Test
    public void testEdit() {
        Laptop laptop = new Laptop();
        laptop.setDiagonal(Diagonal.DIAGONAL_14);
        laptop.setSeries_num(123);
        laptop.setManufacturer("Dell");
        laptop.setCost(999.99);
        laptop.setQuantity(10);

        String id = "1";

        Laptop editedLaptop = laptopsService.edit(laptop, id);

        verify(laptopsRepository).edit(
                laptop.getDiagonal(),
                laptop.getSeries_num(),
                laptop.getManufacturer(),
                laptop.getCost(),
                laptop.getQuantity(),
                id);

        assertEquals(laptop, editedLaptop);
    }

    @Test
    public void testGetAll() {
        List<Laptop> laptops = new ArrayList<>();
        laptops.add(new Laptop());
        laptops.add(new Laptop());

        when(laptopsRepository.findAll()).thenReturn(laptops);

        List<Laptop> allLaptops = laptopsService.getAll();

        verify(laptopsRepository).findAll();

        assertEquals(laptops, allLaptops);
    }

    @Test
    public void testGetById() {
        Laptop laptop = new Laptop();
        laptop.setDiagonal(Diagonal.DIAGONAL_14);
        laptop.setSeries_num(123);
        laptop.setManufacturer("Dell");
        laptop.setCost(999.99);
        laptop.setQuantity(10);

        String id = "1";

        when(laptopsRepository.getReferenceById(Integer.parseInt(id))).thenReturn(laptop);

        Laptop foundLaptop = laptopsService.getById(id);

        verify(laptopsRepository).getReferenceById(Integer.parseInt(id));

        assertEquals(laptop, foundLaptop);
    }
}