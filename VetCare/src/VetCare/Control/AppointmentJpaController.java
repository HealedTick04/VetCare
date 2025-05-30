/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Control;

import VetCare.Control.exceptions.NonexistentEntityException;
import VetCare.Modelo.Appointment;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import VetCare.Modelo.Customer;
import VetCare.Modelo.Pet;
import VetCare.Modelo.Vet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabri
 */
public class AppointmentJpaController implements Serializable {

    public AppointmentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Appointment appointment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerId = appointment.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                appointment.setCustomerId(customerId);
            }
            Pet petId = appointment.getPetId();
            if (petId != null) {
                petId = em.getReference(petId.getClass(), petId.getPetId());
                appointment.setPetId(petId);
            }
            Vet vetId = appointment.getVetId();
            if (vetId != null) {
                vetId = em.getReference(vetId.getClass(), vetId.getVetId());
                appointment.setVetId(vetId);
            }
            em.persist(appointment);
            if (customerId != null) {
                customerId.getAppointmentCollection().add(appointment);
                customerId = em.merge(customerId);
            }
            if (petId != null) {
                petId.getAppointmentCollection().add(appointment);
                petId = em.merge(petId);
            }
            if (vetId != null) {
                vetId.getAppointmentCollection().add(appointment);
                vetId = em.merge(vetId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Appointment appointment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Appointment persistentAppointment = em.find(Appointment.class, appointment.getAppointmentId());
            Customer customerIdOld = persistentAppointment.getCustomerId();
            Customer customerIdNew = appointment.getCustomerId();
            Pet petIdOld = persistentAppointment.getPetId();
            Pet petIdNew = appointment.getPetId();
            Vet vetIdOld = persistentAppointment.getVetId();
            Vet vetIdNew = appointment.getVetId();
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                appointment.setCustomerId(customerIdNew);
            }
            if (petIdNew != null) {
                petIdNew = em.getReference(petIdNew.getClass(), petIdNew.getPetId());
                appointment.setPetId(petIdNew);
            }
            if (vetIdNew != null) {
                vetIdNew = em.getReference(vetIdNew.getClass(), vetIdNew.getVetId());
                appointment.setVetId(vetIdNew);
            }
            appointment = em.merge(appointment);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getAppointmentCollection().remove(appointment);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getAppointmentCollection().add(appointment);
                customerIdNew = em.merge(customerIdNew);
            }
            if (petIdOld != null && !petIdOld.equals(petIdNew)) {
                petIdOld.getAppointmentCollection().remove(appointment);
                petIdOld = em.merge(petIdOld);
            }
            if (petIdNew != null && !petIdNew.equals(petIdOld)) {
                petIdNew.getAppointmentCollection().add(appointment);
                petIdNew = em.merge(petIdNew);
            }
            if (vetIdOld != null && !vetIdOld.equals(vetIdNew)) {
                vetIdOld.getAppointmentCollection().remove(appointment);
                vetIdOld = em.merge(vetIdOld);
            }
            if (vetIdNew != null && !vetIdNew.equals(vetIdOld)) {
                vetIdNew.getAppointmentCollection().add(appointment);
                vetIdNew = em.merge(vetIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = appointment.getAppointmentId();
                if (findAppointment(id) == null) {
                    throw new NonexistentEntityException("The appointment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Appointment appointment;
            try {
                appointment = em.getReference(Appointment.class, id);
                appointment.getAppointmentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appointment with id " + id + " no longer exists.", enfe);
            }
            Customer customerId = appointment.getCustomerId();
            if (customerId != null) {
                customerId.getAppointmentCollection().remove(appointment);
                customerId = em.merge(customerId);
            }
            Pet petId = appointment.getPetId();
            if (petId != null) {
                petId.getAppointmentCollection().remove(appointment);
                petId = em.merge(petId);
            }
            Vet vetId = appointment.getVetId();
            if (vetId != null) {
                vetId.getAppointmentCollection().remove(appointment);
                vetId = em.merge(vetId);
            }
            em.remove(appointment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Appointment> findAppointmentEntities() {
        return findAppointmentEntities(true, -1, -1);
    }

    public List<Appointment> findAppointmentEntities(int maxResults, int firstResult) {
        return findAppointmentEntities(false, maxResults, firstResult);
    }

    private List<Appointment> findAppointmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Appointment.class));
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

    public Appointment findAppointment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Appointment.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppointmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Appointment> rt = cq.from(Appointment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
