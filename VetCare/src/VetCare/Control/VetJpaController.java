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
import VetCare.Modelo.Vet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public void create(Vet vet) {
        if (vet.getAppointmentCollection() == null) {
            vet.setAppointmentCollection(new ArrayList<Appointment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Appointment> attachedAppointmentCollection = new ArrayList<Appointment>();
            for (Appointment appointmentCollectionAppointmentToAttach : vet.getAppointmentCollection()) {
                appointmentCollectionAppointmentToAttach = em.getReference(appointmentCollectionAppointmentToAttach.getClass(), appointmentCollectionAppointmentToAttach.getAppointmentId());
                attachedAppointmentCollection.add(appointmentCollectionAppointmentToAttach);
            }
            vet.setAppointmentCollection(attachedAppointmentCollection);
            em.persist(vet);
            for (Appointment appointmentCollectionAppointment : vet.getAppointmentCollection()) {
                Vet oldVetIdOfAppointmentCollectionAppointment = appointmentCollectionAppointment.getVetId();
                appointmentCollectionAppointment.setVetId(vet);
                appointmentCollectionAppointment = em.merge(appointmentCollectionAppointment);
                if (oldVetIdOfAppointmentCollectionAppointment != null) {
                    oldVetIdOfAppointmentCollectionAppointment.getAppointmentCollection().remove(appointmentCollectionAppointment);
                    oldVetIdOfAppointmentCollectionAppointment = em.merge(oldVetIdOfAppointmentCollectionAppointment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vet vet) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vet persistentVet = em.find(Vet.class, vet.getVetId());
            Collection<Appointment> appointmentCollectionOld = persistentVet.getAppointmentCollection();
            Collection<Appointment> appointmentCollectionNew = vet.getAppointmentCollection();
            List<String> illegalOrphanMessages = null;
            for (Appointment appointmentCollectionOldAppointment : appointmentCollectionOld) {
                if (!appointmentCollectionNew.contains(appointmentCollectionOldAppointment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Appointment " + appointmentCollectionOldAppointment + " since its vetId field is not nullable.");
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
            vet.setAppointmentCollection(appointmentCollectionNew);
            vet = em.merge(vet);
            for (Appointment appointmentCollectionNewAppointment : appointmentCollectionNew) {
                if (!appointmentCollectionOld.contains(appointmentCollectionNewAppointment)) {
                    Vet oldVetIdOfAppointmentCollectionNewAppointment = appointmentCollectionNewAppointment.getVetId();
                    appointmentCollectionNewAppointment.setVetId(vet);
                    appointmentCollectionNewAppointment = em.merge(appointmentCollectionNewAppointment);
                    if (oldVetIdOfAppointmentCollectionNewAppointment != null && !oldVetIdOfAppointmentCollectionNewAppointment.equals(vet)) {
                        oldVetIdOfAppointmentCollectionNewAppointment.getAppointmentCollection().remove(appointmentCollectionNewAppointment);
                        oldVetIdOfAppointmentCollectionNewAppointment = em.merge(oldVetIdOfAppointmentCollectionNewAppointment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vet.getVetId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Appointment> appointmentCollectionOrphanCheck = vet.getAppointmentCollection();
            for (Appointment appointmentCollectionOrphanCheckAppointment : appointmentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vet (" + vet + ") cannot be destroyed since the Appointment " + appointmentCollectionOrphanCheckAppointment + " in its appointmentCollection field has a non-nullable vetId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

    public Vet findVet(Integer id) {
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
