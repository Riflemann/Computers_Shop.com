package com.computershop.repository;

import com.computershop.models.Desktops;
import com.computershop.models.Laptop;
import com.computershop.models.enums.Diagonal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс репозитория для ноутбуков. Расширяет JpaRepository для сущности Laptop.
 */
public interface LaptopsRepository extends JpaRepository<Laptop, Integer> {

    /**
     * Метод для редактирования сущности Laptop. Обновляет ноутбук с указанными параметрами: diagonal, seriesNum, manufacturer, cost, quantity и id.
     *
     * @param diagonal     диагональ экрана
     * @param seriesNum    серийный номер
     * @param manufacturer производитель
     * @param cost         стоимость
     * @param quantity     количество
     * @param id           идентификатор
     */
    @Transactional
    @org.springframework.data.jpa.repository.Query("update Laptop d set d.diagonal = :diagonal, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(Diagonal diagonal, int seriesNum, String manufacturer, double cost, int quantity, String id);

    /**
     * Метод для сохранения или обновления сущности Laptop.
     * Сохраняет или обновляет ноутбук с указанными параметрами:
     * diagonal, seriesNum, manufacturer, cost и quantity.
     * Если сущность с такими параметрами уже существует, обновляет количество, добавляя новое количество к существующему.
     * В противном случае создает новую сущность с указанными параметрами.
     *
     * @param  laptop ноутбук для сохранения или обновления.
     * @param entityManagerFactory фабрика менеджеров сущностей.
     * @throws RuntimeException при ошибке сохранения или обновления жесткого диска.
     */
    @Transactional
    default void saveOrUpdateQuantity(Laptop laptop, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("""
                    SELECT h FROM Laptop h WHERE h.diagonal = :diagonal and \
                    h.series_num = :seriesNum and \
                    h.manufacturer = :manufacturer and \
                    h.cost = :cost""");
            query.setParameter("diagonal", laptop.getDiagonal());
            query.setParameter("seriesNum", laptop.getSeries_num());
            query.setParameter("manufacturer", laptop.getManufacturer());
            query.setParameter("cost", laptop.getCost());

            List<Laptop> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                Laptop existingLaptop = resultList.get(0);
                existingLaptop.setQuantity(existingLaptop.getQuantity() + laptop.getQuantity());
                entityManager.merge(existingLaptop);
            } else {
                entityManager.persist(laptop);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving or updating hard drive", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
