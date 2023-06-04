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

public interface LaptopsRepository extends JpaRepository<Laptop, Integer> {
    @Transactional
    @org.springframework.data.jpa.repository.Query("update Laptop d set d.diagonal = :diagonal, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(Diagonal diagonal, int seriesNum, String manufacturer, double cost, int quantity, String id);

    @Transactional
    default void saveOrUpdateQuantity (Laptop laptop, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("""
                    SELECT h FROM Laptop h WHERE h.diagonal = :diagonal and \
                    h.series_num = :seriesNum and \
                    h.manufacturer = :manufacturer and \
                    h.cost = :cost""");
            query.setParameter("hardDriveVolumes", laptop.getDiagonal());
            query.setParameter("seriesNum", laptop.getSeries_num());
            query.setParameter("manufacturer", laptop.getManufacturer());
            query.setParameter("cost", laptop.getCost());

            List<Desktops> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                Desktops existingHardDrive = resultList.get(0);
                existingHardDrive.setQuantity(existingHardDrive.getQuantity() + laptop.getQuantity());
                entityManager.merge(existingHardDrive);
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
