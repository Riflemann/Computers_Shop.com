package com.computershop.repository;

import com.computershop.models.Desktops;
import com.computershop.models.enums.TypeDesktops;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesktopsRepository extends JpaRepository<Desktops, Integer> {


    @Transactional
    @org.springframework.data.jpa.repository.Query("update Desktops d set d.type_desktops = :typeDesktops, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(TypeDesktops typeDesktops, int seriesNum, String manufacturer, double cost, int quantity, String id);

    @Transactional
    default void saveOrUpdateQuantity (Desktops desktops, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("""
                    SELECT h FROM Desktops h WHERE h.type_desktops = :typeDesktops and \
                    h.series_num = :seriesNum and \
                    h.manufacturer = :manufacturer and \
                    h.cost = :cost""");
            query.setParameter("typeDesktops", desktops.getType_desktops());
            query.setParameter("seriesNum", desktops.getSeries_num());
            query.setParameter("manufacturer", desktops.getManufacturer());
            query.setParameter("cost", desktops.getCost());

            List<Desktops> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                Desktops existingHardDrive = resultList.get(0);
                existingHardDrive.setQuantity(existingHardDrive.getQuantity() + desktops.getQuantity());
                entityManager.merge(existingHardDrive);
            } else {
                entityManager.persist(desktops);
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
