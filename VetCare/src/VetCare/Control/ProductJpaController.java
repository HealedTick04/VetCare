/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Control;

import VetCare.Control.exceptions.IllegalOrphanException;
import VetCare.Control.exceptions.NonexistentEntityException;
import VetCare.Control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import VetCare.Entities.Classes.Alarm;
import VetCare.Entities.Classes.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabri
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, Exception {
        if (product.getAlarmCollection() == null) {
            product.setAlarmCollection(new ArrayList<Alarm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Alarm> attachedAlarmCollection = new ArrayList<Alarm>();
            for (Alarm alarmCollectionAlarmToAttach : product.getAlarmCollection()) {
                alarmCollectionAlarmToAttach = em.getReference(alarmCollectionAlarmToAttach.getClass(), alarmCollectionAlarmToAttach.getAlarmId());
                attachedAlarmCollection.add(alarmCollectionAlarmToAttach);
            }
            product.setAlarmCollection(attachedAlarmCollection);
            em.persist(product);
            for (Alarm alarmCollectionAlarm : product.getAlarmCollection()) {
                Product oldProductIdOfAlarmCollectionAlarm = alarmCollectionAlarm.getProductId();
                alarmCollectionAlarm.setProductId(product);
                alarmCollectionAlarm = em.merge(alarmCollectionAlarm);
                if (oldProductIdOfAlarmCollectionAlarm != null) {
                    oldProductIdOfAlarmCollectionAlarm.getAlarmCollection().remove(alarmCollectionAlarm);
                    oldProductIdOfAlarmCollectionAlarm = em.merge(oldProductIdOfAlarmCollectionAlarm);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProduct(product.getProductId()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProductId());
            Collection<Alarm> alarmCollectionOld = persistentProduct.getAlarmCollection();
            Collection<Alarm> alarmCollectionNew = product.getAlarmCollection();
            List<String> illegalOrphanMessages = null;
            for (Alarm alarmCollectionOldAlarm : alarmCollectionOld) {
                if (!alarmCollectionNew.contains(alarmCollectionOldAlarm)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alarm " + alarmCollectionOldAlarm + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Alarm> attachedAlarmCollectionNew = new ArrayList<Alarm>();
            for (Alarm alarmCollectionNewAlarmToAttach : alarmCollectionNew) {
                alarmCollectionNewAlarmToAttach = em.getReference(alarmCollectionNewAlarmToAttach.getClass(), alarmCollectionNewAlarmToAttach.getAlarmId());
                attachedAlarmCollectionNew.add(alarmCollectionNewAlarmToAttach);
            }
            alarmCollectionNew = attachedAlarmCollectionNew;
            product.setAlarmCollection(alarmCollectionNew);
            product = em.merge(product);
            for (Alarm alarmCollectionNewAlarm : alarmCollectionNew) {
                if (!alarmCollectionOld.contains(alarmCollectionNewAlarm)) {
                    Product oldProductIdOfAlarmCollectionNewAlarm = alarmCollectionNewAlarm.getProductId();
                    alarmCollectionNewAlarm.setProductId(product);
                    alarmCollectionNewAlarm = em.merge(alarmCollectionNewAlarm);
                    if (oldProductIdOfAlarmCollectionNewAlarm != null && !oldProductIdOfAlarmCollectionNewAlarm.equals(product)) {
                        oldProductIdOfAlarmCollectionNewAlarm.getAlarmCollection().remove(alarmCollectionNewAlarm);
                        oldProductIdOfAlarmCollectionNewAlarm = em.merge(oldProductIdOfAlarmCollectionNewAlarm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = product.getProductId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Alarm> alarmCollectionOrphanCheck = product.getAlarmCollection();
            for (Alarm alarmCollectionOrphanCheckAlarm : alarmCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Alarm " + alarmCollectionOrphanCheckAlarm + " in its alarmCollection field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
