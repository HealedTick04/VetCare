/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Control;

import VetCare.Control.exceptions.NonexistentEntityException;
import VetCare.Control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import VetCare.Entities.Classes.Customer;
import VetCare.Entities.Classes.Pet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabri
 */
public class PetJpaController implements Serializable {

    public PetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pet pet) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerId = pet.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                pet.setCustomerId(customerId);
            }
            em.persist(pet);
            if (customerId != null) {
                customerId.getPetCollection().add(pet);
                customerId = em.merge(customerId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPet(pet.getPetId()) != null) {
                throw new PreexistingEntityException("Pet " + pet + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pet pet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pet persistentPet = em.find(Pet.class, pet.getPetId());
            Customer customerIdOld = persistentPet.getCustomerId();
            Customer customerIdNew = pet.getCustomerId();
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                pet.setCustomerId(customerIdNew);
            }
            pet = em.merge(pet);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getPetCollection().remove(pet);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getPetCollection().add(pet);
                customerIdNew = em.merge(customerIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pet.getPetId();
                if (findPet(id) == null) {
                    throw new NonexistentEntityException("The pet with id " + id + " no longer exists.");
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
            Pet pet;
            try {
                pet = em.getReference(Pet.class, id);
                pet.getPetId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pet with id " + id + " no longer exists.", enfe);
            }
            Customer customerId = pet.getCustomerId();
            if (customerId != null) {
                customerId.getPetCollection().remove(pet);
                customerId = em.merge(customerId);
            }
            em.remove(pet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pet> findPetEntities() {
        return findPetEntities(true, -1, -1);
    }

    public List<Pet> findPetEntities(int maxResults, int firstResult) {
        return findPetEntities(false, maxResults, firstResult);
    }

    private List<Pet> findPetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pet.class));
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

    public Pet findPet(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pet.class, id);
        } finally {
            em.close();
        }
    }

    public int getPetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pet> rt = cq.from(Pet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
