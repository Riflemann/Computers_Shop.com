package com.computershop.repository;

import com.computershop.models.Desktops;
import com.computershop.models.HardDisc;
import com.computershop.models.enums.HardDriveVolumes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HardDiscsRepository extends JpaRepository<HardDisc, Integer> {


    @Transactional
    @org.springframework.data.jpa.repository.Query("update HardDisc d set d.hardDriveVolumes = :hardDriveVolumes, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(HardDriveVolumes hardDriveVolumes, int seriesNum, String manufacturer, double cost, int quantity, String id);

    @Transactional
    default void saveOrUpdateQuantity (HardDisc hardDisc, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("""
                    SELECT h FROM HardDisc h WHERE h.hardDriveVolumes = :hardDriveVolumes and \
                    h.series_num = :seriesNum and \
                    h.manufacturer = :manufacturer and \
                    h.cost = :cost""");
            query.setParameter("hardDriveVolumes", hardDisc.getHardDriveVolumes());
            query.setParameter("seriesNum", hardDisc.getSeries_num());
            query.setParameter("manufacturer", hardDisc.getManufacturer());
            query.setParameter("cost", hardDisc.getCost());

            List<HardDisc> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                HardDisc existingHardDisc = resultList.get(0);
                existingHardDisc.setQuantity(existingHardDisc.getQuantity() + hardDisc.getQuantity());
                entityManager.merge(existingHardDisc);
            } else {
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
