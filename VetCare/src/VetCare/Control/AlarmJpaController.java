/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Control;

import VetCare.Control.exceptions.NonexistentEntityException;
import VetCare.Control.exceptions.PreexistingEntityException;
import VetCare.Entities.Classes.Alarm;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import VetCare.Entities.Classes.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabri
 */
public class AlarmJpaController implements Serializable {

    public AlarmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alarm alarm) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = alarm.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                alarm.setProductId(productId);
            }
            em.persist(alarm);
            if (productId != null) {
                productId.getAlarmCollection().add(alarm);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlarm(alarm.getAlarmId()) != null) {
                throw new PreexistingEntityException("Alarm " + alarm + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alarm alarm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alarm persistentAlarm = em.find(Alarm.class, alarm.getAlarmId());
            Product productIdOld = persistentAlarm.getProductId();
            Product productIdNew = alarm.getProductId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                alarm.setProductId(productIdNew);
            }
            alarm = em.merge(alarm);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getAlarmCollection().remove(alarm);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getAlarmCollection().add(alarm);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = alarm.getAlarmId();
                if (findAlarm(id) == null) {
                    throw new NonexistentEntityException("The alarm with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alarm alarm;
            try {
                alarm = em.getReference(Alarm.class, id);
                alarm.getAlarmId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alarm with id " + id + " no longer exists.", enfe);
            }
            Product productId = alarm.getProductId();
            if (productId != null) {
                productId.getAlarmCollection().remove(alarm);
                productId = em.merge(productId);
            }
            em.remove(alarm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alarm> findAlarmEntities() {
        return findAlarmEntities(true, -1, -1);
    }

    public List<Alarm> findAlarmEntities(int maxResults, int firstResult) {
        return findAlarmEntities(false, maxResults, firstResult);
    }

    private List<Alarm> findAlarmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alarm.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Alarm findAlarm(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alarm.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlarmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alarm> rt = cq.from(Alarm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
