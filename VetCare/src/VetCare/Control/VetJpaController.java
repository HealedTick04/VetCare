/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Control;

import VetCare.Control.exceptions.NonexistentEntityException;
import VetCare.Control.exceptions.PreexistingEntityException;
import VetCare.EntitiesClasses.Vet;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author gabri
 */
public class VetJpaController implements Serializable {

    public VetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vet vet) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vet);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVet(vet.getVetId()) != null) {
                throw new PreexistingEntityException("Vet " + vet + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vet vet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vet = em.merge(vet);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vet.getVetId();
                if (findVet(id) == null) {
                    throw new NonexistentEntityException("The vet with id " + id + " no longer exists.");
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
            Vet vet;
            try {
                vet = em.getReference(Vet.class, id);
                vet.getVetId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vet with id " + id + " no longer exists.", enfe);
            }
            em.remove(vet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vet> findVetEntities() {
        return findVetEntities(true, -1, -1);
    }

    public List<Vet> findVetEntities(int maxResults, int firstResult) {
        return findVetEntities(false, maxResults, firstResult);
    }

    private List<Vet> findVetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vet.class));
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

    public Vet findVet(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vet.class, id);
        } finally {
            em.close();
        }
    }

    public int getVetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vet> rt = cq.from(Vet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
