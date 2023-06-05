package com.computershop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


import com.computershop.models.Monitor;
import com.computershop.models.enums.Diagonal;
import com.computershop.repository.MonitorsRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class MonitorsServiceImplTest {

    private MonitorsServiceImpl monitorsService;

    @Mock
    private MonitorsRepository monitorsRepository;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        monitorsService = new MonitorsServiceImpl(monitorsRepository, entityManagerFactory);
    }

    @Test
    public void testSave() {
        Monitor monitor = new Monitor();
        monitor.setId(1);
        monitor.setDiagonal(Diagonal.DIAGONAL_13);
        monitor.setSeries_num(123);
        monitor.setManufacturer("Dell");
        monitor.setCost(200);
        monitor.setQuantity(10);

        Monitor savedMonitor = monitorsService.save(monitor);

        assertEquals(monitor.getId(), savedMonitor.getId());
        assertEquals(monitor.getDiagonal(), savedMonitor.getDiagonal());
        assertEquals(monitor.getSeries_num(), savedMonitor.getSeries_num());
        assertEquals(monitor.getManufacturer(), savedMonitor.getManufacturer());
        assertEquals(monitor.getCost(), savedMonitor.getCost());
        assertEquals(monitor.getQuantity(), savedMonitor.getQuantity());

        verify(monitorsRepository).saveOrUpdateQuantity(monitor, entityManagerFactory);
    }

    @Test
    public void testEdit() {
        Monitor monitor = new Monitor();
        monitor.setId(1);
        monitor.setDiagonal(Diagonal.DIAGONAL_14);
        monitor.setSeries_num(123);
        monitor.setManufacturer("Dell");
        monitor.setCost(200);
        monitor.setQuantity(10);

        String id = "1";

        Monitor editedMonitor = monitorsService.edit(monitor, id);

        assertEquals(monitor.getId(), editedMonitor.getId());
        assertEquals(monitor.getDiagonal(), editedMonitor.getDiagonal());
        assertEquals(monitor.getSeries_num(), editedMonitor.getSeries_num());
        assertEquals(monitor.getManufacturer(), editedMonitor.getManufacturer());
        assertEquals(monitor.getCost(), editedMonitor.getCost());
        assertEquals(monitor.getQuantity(), editedMonitor.getQuantity());

        verify(monitorsRepository).edit(
                monitor.getDiagonal(),
                monitor.getSeries_num(),
                monitor.getManufacturer(),
                monitor.getCost(),
                monitor.getQuantity(),
                id
        );
    }

    @Test
    public void testGetAll() {
        List<Monitor> monitors = new ArrayList<>();

        Monitor monitor1 = new Monitor();
        monitor1.setId(1);
        monitor1.setDiagonal(Diagonal.DIAGONAL_13);
        monitor1.setSeries_num(123);
        monitor1.setManufacturer("Dell");
        monitor1.setCost(200);
        monitor1.setQuantity(10);

        Monitor monitor2 = new Monitor();
        monitor2.setId(2);
        monitor2.setDiagonal(Diagonal.DIAGONAL_13);
        monitor2.setSeries_num(456);
        monitor2.setManufacturer("HP");
        monitor2.setCost(300);
        monitor2.setQuantity(5);

        monitors.add(monitor1);
        monitors.add(monitor2);

        when(monitorsRepository.findAll()).thenReturn(monitors);

        List<Monitor> allMonitors = monitorsService.getAll();

        assertEquals(monitors.size(), allMonitors.size());
        assertEquals(monitors.get(0).getId(), allMonitors.get(0).getId());
        assertEquals(monitors.get(0).getDiagonal(), allMonitors.get(0).getDiagonal());
        assertEquals(monitors.get(0).getSeries_num(), allMonitors.get(0).getSeries_num());
        assertEquals(monitors.get(0).getManufacturer(), allMonitors.get(0).getManufacturer());
        assertEquals(monitors.get(0).getCost(), allMonitors.get(0).getCost());
        assertEquals(monitors.get(0).getQuantity(), allMonitors.get(0).getQuantity());
        assertEquals(monitors.get(1).getId(), allMonitors.get(1).getId());
        assertEquals(monitors.get(1).getDiagonal(), allMonitors.get(1).getDiagonal());
        assertEquals(monitors.get(1).getSeries_num(), allMonitors.get(1).getSeries_num());
        assertEquals(monitors.get(1).getManufacturer(), allMonitors.get(1).getManufacturer());
        assertEquals(monitors.get(1).getCost(), allMonitors.get(1).getCost());
        assertEquals(monitors.get(1).getQuantity(), allMonitors.get(1).getQuantity());
    }

    @Test
    public void testGetById() {
        Monitor monitor = new Monitor();
        monitor.setId(1);
        monitor.setDiagonal(Diagonal.DIAGONAL_14);
        monitor.setSeries_num(123);
        monitor.setManufacturer("Dell");
        monitor.setCost(200);
        monitor.setQuantity(10);

        String id = "1";

        when(monitorsRepository.getReferenceById(Integer.parseInt(id))).thenReturn(monitor);

        Monitor foundMonitor = monitorsService.getById(id);

        assertEquals(monitor.getId(), foundMonitor.getId());
        assertEquals(monitor.getDiagonal(), foundMonitor.getDiagonal());
        assertEquals(monitor.getSeries_num(), foundMonitor.getSeries_num());
        assertEquals(monitor.getManufacturer(), foundMonitor.getManufacturer());
        assertEquals(monitor.getCost(), foundMonitor.getCost());
        assertEquals(monitor.getQuantity(), foundMonitor.getQuantity());
    }
}