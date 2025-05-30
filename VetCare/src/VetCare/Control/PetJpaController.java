/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Control;

import VetCare.Control.exceptions.IllegalOrphanException;
import VetCare.Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import VetCare.Modelo.Customer;
import VetCare.Modelo.Appointment;
import VetCare.Modelo.Pet;
import java.util.ArrayList;
import java.util.Collection;
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

    public void create(Pet pet) {
        if (pet.getAppointmentCollection() == null) {
            pet.setAppointmentCollection(new ArrayList<Appointment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerId = pet.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                pet.setCustomerId(customerId);
            }
            Collection<Appointment> attachedAppointmentCollection = new ArrayList<Appointment>();
            for (Appointment appointmentCollectionAppointmentToAttach : pet.getAppointmentCollection()) {
                appointmentCollectionAppointmentToAttach = em.getReference(appointmentCollectionAppointmentToAttach.getClass(), appointmentCollectionAppointmentToAttach.getAppointmentId());
                attachedAppointmentCollection.add(appointmentCollectionAppointmentToAttach);
            }
            pet.setAppointmentCollection(attachedAppointmentCollection);
            em.persist(pet);
            if (customerId != null) {
                customerId.getPetCollection().add(pet);
                customerId = em.merge(customerId);
            }
            for (Appointment appointmentCollectionAppointment : pet.getAppointmentCollection()) {
                Pet oldPetIdOfAppointmentCollectionAppointment = appointmentCollectionAppointment.getPetId();
                appointmentCollectionAppointment.setPetId(pet);
                appointmentCollectionAppointment = em.merge(appointmentCollectionAppointment);
                if (oldPetIdOfAppointmentCollectionAppointment != null) {
                    oldPetIdOfAppointmentCollectionAppointment.getAppointmentCollection().remove(appointmentCollectionAppointment);
                    oldPetIdOfAppointmentCollectionAppointment = em.merge(oldPetIdOfAppointmentCollectionAppointment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pet pet) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pet persistentPet = em.find(Pet.class, pet.getPetId());
            Customer customerIdOld = persistentPet.getCustomerId();
            Customer customerIdNew = pet.getCustomerId();
            Collection<Appointment> appointmentCollectionOld = persistentPet.getAppointmentCollection();
            Collection<Appointment> appointmentCollectionNew = pet.getAppointmentCollection();
            List<String> illegalOrphanMessages = null;
            for (Appointment appointmentCollectionOldAppointment : appointmentCollectionOld) {
                if (!appointmentCollectionNew.contains(appointmentCollectionOldAppointment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Appointment " + appointmentCollectionOldAppointment + " since its petId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                pet.setCustomerId(customerIdNew);
            }
            Collection<Appointment> attachedAppointmentCollectionNew = new ArrayList<Appointment>();
            for (Appointment appointmentCollectionNewAppointmentToAttach : appointmentCollectionNew) {
                appointmentCollectionNewAppointmentToAttach = em.getReference(appointmentCollectionNewAppointmentToAttach.getClass(), appointmentCollectionNewAppointmentToAttach.getAppointmentId());
                attachedAppointmentCollectionNew.add(appointmentCollectionNewAppointmentToAttach);
            }
            appointmentCollectionNew = attachedAppointmentCollectionNew;
            pet.setAppointmentCollection(appointmentCollectionNew);
            pet = em.merge(pet);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getPetCollection().remove(pet);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getPetCollection().add(pet);
                customerIdNew = em.merge(customerIdNew);
            }
            for (Appointment appointmentCollectionNewAppointment : appointmentCollectionNew) {
                if (!appointmentCollectionOld.contains(appointmentCollectionNewAppointment)) {
                    Pet oldPetIdOfAppointmentCollectionNewAppointment = appointmentCollectionNewAppointment.getPetId();
                    appointmentCollectionNewAppointment.setPetId(pet);
                    appointmentCollectionNewAppointment = em.merge(appointmentCollectionNewAppointment);
                    if (oldPetIdOfAppointmentCollectionNewAppointment != null && !oldPetIdOfAppointmentCollectionNewAppointment.equals(pet)) {
                        oldPetIdOfAppointmentCollectionNewAppointment.getAppointmentCollection().remove(appointmentCollectionNewAppointment);
                        oldPetIdOfAppointmentCollectionNewAppointment = em.merge(oldPetIdOfAppointmentCollectionNewAppointment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pet.getPetId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Appointment> appointmentCollectionOrphanCheck = pet.getAppointmentCollection();
            for (Appointment appointmentCollectionOrphanCheckAppointment : appointmentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pet (" + pet + ") cannot be destroyed since the Appointment " + appointmentCollectionOrphanCheckAppointment + " in its appointmentCollection field has a non-nullable petId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

    public Pet findPet(Integer id) {
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
