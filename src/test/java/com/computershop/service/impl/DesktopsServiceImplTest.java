package com.computershop.service.impl;

import com.computershop.models.Desktops;
import com.computershop.repository.DesktopsRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class DesktopsServiceImplTest {

    @Mock
    private DesktopsRepository desktopsRepositoryMock;

    @Mock
    private EntityManagerFactory entityManagerFactoryMock;

    private DesktopsServiceImpl desktopsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        desktopsService = new DesktopsServiceImpl(desktopsRepositoryMock, entityManagerFactoryMock);
    }

    @Test
    public void testSave() {
        Desktops desktops = new Desktops();
        desktopsService.save(desktops);
        verify(desktopsRepositoryMock, times(1)).saveOrUpdateQuantity(desktops, entityManagerFactoryMock);
    }

    @Test
    public void testEdit() {
        Desktops desktops = new Desktops();
        String id = "1";
        desktopsService.edit(desktops, id);
        verify(desktopsRepositoryMock, times(1)).edit(
                desktops.getType_desktops(),
                desktops.getSeries_num(),
                desktops.getManufacturer(),
                desktops.getCost(),
                desktops.getQuantity(),
                id
        );
    }

    @Test
    public void testGetAll() {
        desktopsService.getAll();
        verify(desktopsRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testGetById() {
        String id = "1";
        desktopsService.getById(id);
        verify(desktopsRepositoryMock, times(1)).getReferenceById(Integer.parseInt(id));
    }
}