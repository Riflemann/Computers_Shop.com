package com.computershop.service.impl;

import com.computershop.models.Desktops;
import com.computershop.repository.DesktopsRepository;
import com.computershop.service.DesktopsService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
/**
 * Описание класса:
 * <p>Класс DesktopsServiceImpl реализует интерфейс DesktopsService и предоставляет методы для работы с объектами Desktops.
 * <p>Атрибуты класса:
 * <p>- desktopsRepository: объект класса DesktopsRepository, используемый для выполнения операций с базой данных;
 * <p>- entityManagerFactory: объект класса EntityManagerFactory, используемый для создания EntityManager.
 * <p>Конструктор класса:
 * <p>- DesktopsServiceImpl(DesktopsRepository desktopsRepository, EntityManagerFactory entityManagerFactory): конструктор класса, который принимает в качестве параметров объекты классов DesktopsRepository и EntityManagerFactory.
 * <p>Методы класса:
 * <p>- save(Desktops desktops): метод для сохранения объекта Desktops в базе данных. Метод вызывает метод saveOrUpdateQuantity объекта desktopsRepository и передает ему в качестве параметров объект Desktops и entityManagerFactory. Метод возвращает сохраненный объект Desktops;
 * <p>- edit(Desktops desktops, String id): метод для редактирования объекта Desktops в базе данных. Метод вызывает метод edit объекта desktopsRepository и передает ему в качестве параметров значения полей объекта Desktops, идентификатор объекта id. Метод возвращает отредактированный объект Desktops;
 * <p>- getAll(): метод для получения списка всех объектов Desktops из базы данных. Метод вызывает метод findAll объекта desktopsRepository. Метод возвращает список объектов Desktops;
 * <p>- getById(String id): метод для получения объекта Desktops по идентификатору. Метод вызывает метод getReferenceById объекта desktopsRepository и передает ему в качестве параметра идентификатор объекта id. Метод возвращает объект Desktops.
 * */
@Service
public class DesktopsServiceImpl implements DesktopsService {

    private final DesktopsRepository desktopsRepository;
    private final EntityManagerFactory entityManagerFactory;

    public DesktopsServiceImpl(DesktopsRepository desktopsRepository, EntityManagerFactory entityManagerFactory) {
        this.desktopsRepository = desktopsRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Desktops save(Desktops desktops) {
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
