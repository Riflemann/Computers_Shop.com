package com.computershop.repository;

import com.computershop.models.Desktops;
import com.computershop.models.HardDisc;
import com.computershop.models.Laptop;
import com.computershop.models.enums.HardDriveVolumes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Интерфейс репозитория для жестких дисков.
 * Расширяет JpaRepository для сущности HardDisc.
 */
public interface HardDiscsRepository extends JpaRepository<HardDisc, Integer> {

    /**
     * Метод для редактирования сущности HardDisc.
     * Обновляет жесткий диск с указанными параметрами:
     * hardDriveVolumes, seriesNum, manufacturer, cost, quantity и id.
     *
     * @param hardDriveVolumes объем жесткого диска.
     * @param seriesNum номер серии жесткого диска.
     * @param manufacturer производитель жесткого диска.
     * @param cost стоимость жесткого диска.
     * @param quantity количество жестких дисков.
     * @param id идентификатор жесткого диска.
     */
    @Transactional
    @org.springframework.data.jpa.repository.Query("update HardDisc d set d.hardDriveVolumes = :hardDriveVolumes, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(HardDriveVolumes hardDriveVolumes, int seriesNum, String manufacturer, double cost, int quantity, String id);

    /**
     * Метод для сохранения или обновления сущности HardDisc.
     * Сохраняет или обновляет жесткий диск с указанными параметрами:
     * hardDriveVolumes, seriesNum, manufacturer, cost и quantity.
     * Если сущность с такими параметрами уже существует, обновляет количество, добавляя новое количество к существующему.
     * В противном случае создает новую сущность с указанными параметрами.
     *
     * @param hardDisc жесткий диск для сохранения или обновления.
     * @param entityManagerFactory фабрика менеджеров сущностей.
     * @throws RuntimeException при ошибке сохранения или обновления жесткого диска.
     */
    @Transactional
    default void saveOrUpdateQuantity (HardDisc hardDisc, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            try {
                HardDisc result = entityManager.createQuery("""
                                SELECT h FROM HardDisc h WHERE h.hardDriveVolumes = :diagonal and \
                                h.series_num = :seriesNum and \
                                h.manufacturer = :manufacturer and \
                                h.cost = :cost""", HardDisc.class)
                        .setParameter("hardDriveVolumes", hardDisc.getHardDriveVolumes())
                        .setParameter("seriesNum", hardDisc.getSeries_num())
                        .setParameter("manufacturer", hardDisc.getManufacturer())
                        .setParameter("cost", hardDisc.getCost())
                        .getSingleResult();
                result.setQuantity(result.getQuantity() + hardDisc.getQuantity());
                entityManager.merge(result);
            } catch (NoResultException e) {
                entityManager.persist(hardDisc);
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
