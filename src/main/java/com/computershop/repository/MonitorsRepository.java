package com.computershop.repository;

import com.computershop.models.Monitor;
import com.computershop.models.enums.Diagonal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorsRepository extends JpaRepository<Monitor, Integer> {
    @Transactional
    @org.springframework.data.jpa.repository.Query("update Monitor d set d.diagonal = :diagonal, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(Diagonal diagonal, int seriesNum, String manufacturer, double cost, int quantity, String id);

    @Transactional
    default void saveOrUpdateQuantity (Monitor monitor, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("""
                    SELECT h FROM Monitor h WHERE h.diagonal = :diagonal and \
                    h.series_num = :seriesNum and \
                    h.manufacturer = :manufacturer and \
                    h.cost = :cost""");
            query.setParameter("diagonal", monitor.getDiagonal());
            query.setParameter("seriesNum", monitor.getSeries_num());
            query.setParameter("manufacturer", monitor.getManufacturer());
            query.setParameter("cost", monitor.getCost());

            List<Monitor> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                Monitor existingMonitor = resultList.get(0);
                existingMonitor.setQuantity(existingMonitor.getQuantity() + monitor.getQuantity());
                entityManager.merge(existingMonitor);
            } else {
                entityManager.persist(monitor);
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
