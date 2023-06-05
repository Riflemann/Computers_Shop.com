package com.computershop.repository;

import com.computershop.models.Desktops;
import com.computershop.models.Laptop;
import com.computershop.models.enums.TypeDesktops;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Интерфейс DesktopsRepository предоставляет методы для выполнения операций с объектами Desktops в базе данных.
 *
 * @author [Данилов Константин]
 */
public interface DesktopsRepository extends JpaRepository<Desktops, Integer> {

    /**
     * Метод edit позволяет изменить значения полей объекта Desktops в базе данных по его идентификатору.
     *
     * @param typeDesktops тип настольного компьютера
     * @param seriesNum серийный номер настольного компьютера
     * @param manufacturer производитель настольного компьютера
     * @param cost стоимость настольного компьютера
     * @param quantity количество настольных компьютеров
     * @param id идентификатор настольного компьютера
     * @throws RuntimeException если возникает ошибка при выполнении операции
     */
    @Transactional
    @org.springframework.data.jpa.repository.Query("update Desktops d set d.type_desktops = :typeDesktops, d.series_num = :seriesNum, d.manufacturer = :manufacturer, d.cost = :cost, d.quantity = :quantity where d.id = :id")
    void edit(TypeDesktops typeDesktops, int seriesNum, String manufacturer, double cost, int quantity, String id);

    /**
     * Метод saveOrUpdateQuantity позволяет сохранить или обновить объект Desktops в базе данных и увеличить количество настольных компьютеров в базе данных, если объект уже существует.
     *
     * @param desktops объект Desktops, который нужно сохранить или обновить
     * @param entityManagerFactory объект EntityManagerFactory, используемый для создания EntityManager
     * @throws RuntimeException если возникает ошибка при выполнении операции
     */
    @Transactional
    default void saveOrUpdateQuantity (Desktops desktops, EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            try {
                Desktops result = entityManager.createQuery("""
                                SELECT h FROM Desktops h WHERE h.type_desktops = :type_desktops and \
                                h.series_num = :seriesNum and \
                                h.manufacturer = :manufacturer and \
                                h.cost = :cost""", Desktops.class)
                        .setParameter("type_desktops", desktops.getType_desktops())
                        .setParameter("seriesNum", desktops.getSeries_num())
                        .setParameter("manufacturer", desktops.getManufacturer())
                        .setParameter("cost", desktops.getCost())
                        .getSingleResult();
                result.setQuantity(result.getQuantity() + desktops.getQuantity());
                entityManager.merge(result);
            } catch (NoResultException e) {
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
