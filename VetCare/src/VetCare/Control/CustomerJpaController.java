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
import VetCare.Modelo.Appointment;
import VetCare.Modelo.Customer;
import java.util.ArrayList;
import java.util.Collection;
import VetCare.Modelo.Pet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabri
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) {
        if (customer.getAppointmentCollection() == null) {
            customer.setAppointmentCollection(new ArrayList<Appointment>());
        }
        if (customer.getPetCollection() == null) {
            customer.setPetCollection(new ArrayList<Pet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Appointment> attachedAppointmentCollection = new ArrayList<Appointment>();
            for (Appointment appointmentCollectionAppointmentToAttach : customer.getAppointmentCollection()) {
                appointmentCollectionAppointmentToAttach = em.getReference(appointmentCollectionAppointmentToAttach.getClass(), appointmentCollectionAppointmentToAttach.getAppointmentId());
                attachedAppointmentCollection.add(appointmentCollectionAppointmentToAttach);
            }
            customer.setAppointmentCollection(attachedAppointmentCollection);
            Collection<Pet> attachedPetCollection = new ArrayList<Pet>();
            for (Pet petCollectionPetToAttach : customer.getPetCollection()) {
                petCollectionPetToAttach = em.getReference(petCollectionPetToAttach.getClass(), petCollectionPetToAttach.getPetId());
                attachedPetCollection.add(petCollectionPetToAttach);
            }
            customer.setPetCollection(attachedPetCollection);
            em.persist(customer);
            for (Appointment appointmentCollectionAppointment : customer.getAppointmentCollection()) {
                Customer oldCustomerIdOfAppointmentCollectionAppointment = appointmentCollectionAppointment.getCustomerId();
                appointmentCollectionAppointment.setCustomerId(customer);
                appointmentCollectionAppointment = em.merge(appointmentCollectionAppointment);
                if (oldCustomerIdOfAppointmentCollectionAppointment != null) {
                    oldCustomerIdOfAppointmentCollectionAppointment.getAppointmentCollection().remove(appointmentCollectionAppointment);
                    oldCustomerIdOfAppointmentCollectionAppointment = em.merge(oldCustomerIdOfAppointmentCollectionAppointment);
                }
            }
            for (Pet petCollectionPet : customer.getPetCollection()) {
                Customer oldCustomerIdOfPetCollectionPet = petCollectionPet.getCustomerId();
                petCollectionPet.setCustomerId(customer);
                petCollectionPet = em.merge(petCollectionPet);
                if (oldCustomerIdOfPetCollectionPet != null) {
                    oldCustomerIdOfPetCollectionPet.getPetCollection().remove(petCollectionPet);
                    oldCustomerIdOfPetCollectionPet = em.merge(oldCustomerIdOfPetCollectionPet);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomerId());
            Collection<Appointment> appointmentCollectionOld = persistentCustomer.getAppointmentCollection();
            Collection<Appointment> appointmentCollectionNew = customer.getAppointmentCollection();
            Collection<Pet> petCollectionOld = persistentCustomer.getPetCollection();
            Collection<Pet> petCollectionNew = customer.getPetCollection();
            List<String> illegalOrphanMessages = null;
            for (Appointment appointmentCollectionOldAppointment : appointmentCollectionOld) {
                if (!appointmentCollectionNew.contains(appointmentCollectionOldAppointment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Appointment " + appointmentCollectionOldAppointment + " since its customerId field is not nullable.");
                }
            }
            for (Pet petCollectionOldPet : petCollectionOld) {
                if (!petCollectionNew.contains(petCollectionOldPet)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pet " + petCollectionOldPet + " since its customerId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Appointment> attachedAppointmentCollectionNew = new ArrayList<Appointment>();
            for (Appointment appointmentCollectionNewAppointmentToAttach : appointmentCollectionNew) {
                appointmentCollectionNewAppointmentToAttach = em.getReference(appointmentCollectionNewAppointmentToAttach.getClass(), appointmentCollectionNewAppointmentToAttach.getAppointmentId());
                attachedAppointmentCollectionNew.add(appointmentCollectionNewAppointmentToAttach);
            }
            appointmentCollectionNew = attachedAppointmentCollectionNew;
            customer.setAppointmentCollection(appointmentCollectionNew);
            Collection<Pet> attachedPetCollectionNew = new ArrayList<Pet>();
            for (Pet petCollectionNewPetToAttach : petCollectionNew) {
                petCollectionNewPetToAttach = em.getReference(petCollectionNewPetToAttach.getClass(), petCollectionNewPetToAttach.getPetId());
                attachedPetCollectionNew.add(petCollectionNewPetToAttach);
            }
            petCollectionNew = attachedPetCollectionNew;
            customer.setPetCollection(petCollectionNew);
            customer = em.merge(customer);
            for (Appointment appointmentCollectionNewAppointment : appointmentCollectionNew) {
                if (!appointmentCollectionOld.contains(appointmentCollectionNewAppointment)) {
                    Customer oldCustomerIdOfAppointmentCollectionNewAppointment = appointmentCollectionNewAppointment.getCustomerId();
                    appointmentCollectionNewAppointment.setCustomerId(customer);
                    appointmentCollectionNewAppointment = em.merge(appointmentCollectionNewAppointment);
                    if (oldCustomerIdOfAppointmentCollectionNewAppointment != null && !oldCustomerIdOfAppointmentCollectionNewAppointment.equals(customer)) {
                        oldCustomerIdOfAppointmentCollectionNewAppointment.getAppointmentCollection().remove(appointmentCollectionNewAppointment);
                        oldCustomerIdOfAppointmentCollectionNewAppointment = em.merge(oldCustomerIdOfAppointmentCollectionNewAppointment);
                    }
                }
            }
            for (Pet petCollectionNewPet : petCollectionNew) {
                if (!petCollectionOld.contains(petCollectionNewPet)) {
                    Customer oldCustomerIdOfPetCollectionNewPet = petCollectionNewPet.getCustomerId();
                    petCollectionNewPet.setCustomerId(customer);
                    petCollectionNewPet = em.merge(petCollectionNewPet);
                    if (oldCustomerIdOfPetCollectionNewPet != null && !oldCustomerIdOfPetCollectionNewPet.equals(customer)) {
                        oldCustomerIdOfPetCollectionNewPet.getPetCollection().remove(petCollectionNewPet);
                        oldCustomerIdOfPetCollectionNewPet = em.merge(oldCustomerIdOfPetCollectionNewPet);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customer.getCustomerId();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
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
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCustomerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Appointment> appointmentCollectionOrphanCheck = customer.getAppointmentCollection();
            for (Appointment appointmentCollectionOrphanCheckAppointment : appointmentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Appointment " + appointmentCollectionOrphanCheckAppointment + " in its appointmentCollection field has a non-nullable customerId field.");
            }
            Collection<Pet> petCollectionOrphanCheck = customer.getPetCollection();
            for (Pet petCollectionOrphanCheckPet : petCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Pet " + petCollectionOrphanCheckPet + " in its petCollection field has a non-nullable customerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
