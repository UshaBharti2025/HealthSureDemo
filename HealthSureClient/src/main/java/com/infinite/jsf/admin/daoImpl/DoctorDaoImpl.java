/**
 * Implementation of the DoctorDao interface that provides methods
 * for CRUD operations and validations on `Doctor` and `Provider` entities.
 * 
 * This class uses Hibernate for data access operations and provides:
 * - Search and inquiry functionalities for doctors and providers.
 * - CRUD operations for updating doctor and provider details.
 * - Validation methods to ensure the integrity of data.
 */
package com.infinite.jsf.admin.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.infinite.jsf.admin.dao.DoctorDao;
import com.infinite.jsf.provider.model.Doctors;
import com.infinite.jsf.provider.model.LoginStatus;
import com.infinite.jsf.provider.model.Provider;
import com.infinite.jsf.util.SessionHelper;

public class DoctorDaoImpl implements DoctorDao {

    private SessionFactory sessionFactory;
    private Session session;
    
    public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	 public DoctorDaoImpl() {
	        this.sessionFactory = SessionHelper.getConnection(); // Ensure sessionFactory is initialized
	    }



    //Method to validate Doctor ID (should start with D followed by 3 digits)
	 /**
	  * Validates the format of a given doctor ID.
	  * The doctor ID is considered valid if it starts with the letter 'D' followed by exactly 3 digits (e.g., D123).
	  *
	  * @param doctorId The doctor ID string to be validated.
	  * @return true if the doctor ID matches the expected format (D followed by 3 digits), otherwise false.
	  */
    private boolean isValidDoctorId(String doctorId) {
        Pattern pattern = Pattern.compile("^D\\d{3}$"); // D followed by 3 digits
        Matcher matcher = pattern.matcher(doctorId);
        return matcher.matches();
    }

    //Method to validate Doctor Name (min 6 characters)
    /**
     * Validates the doctor name. 
     * The name is considered valid if it is not null and has a length of at least 6 characters.
     *
     * @param doctorName The doctor name string to be validated.
     * @return true if the doctor name is valid (not null and length >= 6), otherwise false.
     */
    private boolean isValidDoctorName(String doctorName) {
        return doctorName != null && doctorName.length() >= 6;
    }

    //Method to validate Specialization (should be a recognized specialization)
    /**
     * Validates the specialization by checking if it is a recognized medical specialization.
     * The specialization is considered valid if it exists in a predefined list of specializations.
     *
     * @param specialization The specialization string to be validated.
     * @return true if the specialization is recognized, otherwise false.
     */
    private boolean isValidSpecialization(String specialization) {
        // List of valid medical specializations (can be expanded)
        List<String> validSpecializations = Arrays.asList(
            "Cardiology", "Neurology", "Pediatrics", "Dermatology", "Orthopedics",
            "General Medicine", "Psychiatry", "Surgery", "ENT"
        );
        return validSpecializations.contains(specialization);
    }

    //Method to validate Provider ID (should start with P followed by 3 digits)
    /**
     * Validates the provider ID format. 
     * The provider ID is considered valid if it starts with 'P' followed by exactly 3 digits.
     * 
     * @param providerId The provider ID string to be validated.
     * @return true if the provider ID matches the format "P" followed by 3 digits, otherwise false.
     */
    private boolean isValidProviderId(String providerId) {
        Pattern pattern = Pattern.compile("^P\\d{3}$"); // P followed by 3 digits
        Matcher matcher = pattern.matcher(providerId);
        return matcher.matches();
    }

    //Method to validate License Number (should start with L followed by 3 digits)
    /**
     * Validates the license number format. 
     * The license number is considered valid if it starts with 'L' followed by exactly 3 digits.
     * 
     * @param licenseNo The license number string to be validated.
     * @return true if the license number matches the format "L" followed by 3 digits, otherwise false.
     */
    private boolean isValidLicenseNo(String licenseNo) {
        Pattern pattern = Pattern.compile("^L\\d{3}$"); // L followed by 3 digits
        Matcher matcher = pattern.matcher(licenseNo);
        return matcher.matches();
    }

    
    // Methods For Provider Update Details
    
    
    

    /**
     * Fetches a single doctor from the database by their ID.
     * 
     * @param doctorId The ID of the doctor to be fetched.
     * @return A Doctors object representing the doctor with the specified ID.
     * @throws IllegalArgumentException if the doctor ID is null or empty.
     * @throws RuntimeException if an error occurs while fetching the doctor from the database.
     */
    public Doctors getDoctorById(String doctorId) {
    	System.out.println(doctorId + "Yes doctorId reached getDoctorById in DaoImpl");
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Doctor ID.");
        }

        sessionFactory = SessionHelper.getConnection();
        session = sessionFactory.openSession();
        Doctors doctor = null;

        try {
            doctor = (Doctors) session.createCriteria(Doctors.class)
                    .add(Restrictions.eq("doctorId", doctorId.trim()))
                    .uniqueResult();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Optionally, you can throw a custom exception or return null
            throw new RuntimeException("Error fetching doctor with ID: " + doctorId, e);
        } finally {
            session.close();
        }

        return doctor;
    }

    /**
     * Fetches a provider from the database by their ID.
     * 
     * @param providerId The ID of the provider to be fetched.
     * @return A Provider object representing the provider with the specified ID.
     * @throws IllegalArgumentException if the provider ID is null or empty.
     * @throws RuntimeException if an error occurs while fetching the provider from the database.
     */
    public Provider getProviderById(String providerId) {
        System.out.println(providerId + " Yes providerId reached getProviderById in DaoImpl");
        if (providerId == null || providerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Provider ID.");
        }

        sessionFactory = SessionHelper.getConnection();
        session = sessionFactory.openSession();
        Provider provider = null;

        try {
            provider = (Provider) session.createCriteria(Provider.class)
                    .add(Restrictions.eq("providerId", providerId.trim()))
                    .uniqueResult();
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Optionally, you can throw a custom exception or return null
            throw new RuntimeException("Error fetching provider with ID: " + providerId, e);
        } finally {
            session.close();
        }

        return provider;
    }


/**
 * Updates a doctor's details in the database.
 * 
 * @param doctor The Doctors object containing the updated information.
 * @throws RuntimeException if an error occurs while updating the doctor.
 */
    public void updateDoctor(Doctors doctor) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            // Don't overwrite the doctorId, it's part of the object
            session.update(doctor);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating doctor", e);
        } finally {
            session.close();
        }
    }

/**
 * Updates a provider's details in the database.
 * 
 * @param provider The Provider object containing the updated information.
 * @throws RuntimeException if an error occurs while updating the provider.
 */
    public void updateProvider(Provider provider) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Don't overwrite the providerId, it's part of the object
            session.update(provider);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating provider", e);
        } finally {
            session.close();
        }
    }




    /**
     * Utility method to validate doctor fields.
    * Validates the properties of a given Doctor object to ensure they meet the required criteria.
    * 
    * The validation checks the following:
    * - Doctor ID: Must not be null, empty, and should match the valid format (e.g., "D123").
    * - Doctor Name: Must not be null, empty, and should have at least 6 characters.
    * - License Number: Must not be null, empty, and should match the valid format (e.g., "L123").
    * 
    * @param doctor The Doctor object to be validated.
    * @throws IllegalArgumentException if any of the doctor's properties are invalid.
    */
    private void validateDoctor(Doctors doctor) {
        if (doctor.getDoctorId() == null || doctor.getDoctorId().isEmpty() || !isValidDoctorId(doctor.getDoctorId())) {
            throw new IllegalArgumentException("Doctor ID cannot be null, empty or invalid.");
        }
        if (doctor.getDoctorName() == null || doctor.getDoctorName().isEmpty() || !isValidDoctorName(doctor.getDoctorName())) {
            throw new IllegalArgumentException("Doctor Name must be at least 6 characters long.");
        }
        if (doctor.getLicenseNo() == null || doctor.getLicenseNo().isEmpty() || !isValidLicenseNo(doctor.getLicenseNo())) {
            throw new IllegalArgumentException("License No cannot be null, empty or invalid.");
        }
    }

    

    
    
    // Methods For Provider Review And Approval
    
    
    
    /**
     * Retrieves a list of all providers from the database.
     * 
     * This method fetches all provider details from the database by querying the `Provider` table 
     * without any specific filtering or criteria. It returns a list of `Provider` objects.
     * 
     * @return A list of Provider objects representing all providers in the database.
     */
    @Override
    public List<Provider> reviewProviderDetails() {
        Session session = sessionFactory.openSession();
        List<Provider> providers = null;
        try {
            Criteria criteria = session.createCriteria(Provider.class);
            providers = criteria.list();  // Fetch all provider details
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return providers;
    }

    
    /**
     * Searches for a provider by their ID.
     * 
     * This method queries the database to find a provider using the specified provider ID. 
     * If a provider with the given ID is found, it returns the corresponding `Provider` object; 
     * otherwise, it returns null.
     * 
     * @param providerId The ID of the provider to search for.
     * @return A Provider object corresponding to the given provider ID, or null if not found.
     */
    @Override
    public Provider searchProviderById(String providerId) {
        Session session = sessionFactory.openSession();
        Provider provider = null;
        try {
            Criteria criteria = session.createCriteria(Provider.class);
            criteria.add(Restrictions.eq("providerId", providerId));  // Search by provider ID
            provider = (Provider) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return provider;
    }
    /**
     * Updates the status of a provider in the database.
     * 
     * This method retrieves a provider by their ID, checks if the provider exists, 
     * and then updates the provider's status to the given status value. The status is 
     * updated using an enumeration (`LoginStatus`). If the provider is found and the update is successful, 
     * it commits the transaction and returns a success message. If not, it returns an error message.
     * 
     * @param providerId The `Provider` object containing the provider's ID whose status needs to be updated.
     * @param status The new status to set for the provider (as a string).
     * @return A message indicating the result of the status update operation.
     */
    @Override
    public String updateProviderStatus(Provider providerId, String status) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Fetch the provider by ID
            Criteria criteria = session.createCriteria(Provider.class);
            criteria.add(Restrictions.eq("providerId", providerId.getProviderId()));
            Provider provider = (Provider) criteria.uniqueResult();

            // If provider exists, update status
            if (provider != null) {
                provider.setStatus(LoginStatus.valueOf(status.toUpperCase()));
                session.update(provider);  // Save the changes
                tx.commit();
                return "Provider status updated successfully";
            } else {
                return "Provider not found";
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return "Error updating provider status";
        } finally {
            session.close();
        }
    }
    
    //Methods for Provider Search And Inquiry 
    
    
    /**
     * This method searches for doctors based on various search criteria.
     * 
     * This method allows for flexible search by any combination of the following fields:
     * - Doctor ID
     * - Doctor Name (supports partial matching)
     * - Provider ID
     * - Specialization (supports partial matching)
     * - License Number (supports partial matching)
     * 
     * @param doctorId The ID of the doctor to search for (optional).
     * @param doctorName The name of the doctor to search for (optional).
     * @param providerId The provider ID associated with the doctor (optional).
     * @param specialization The specialization of the doctor (optional).
     * @param licenseNo The license number of the doctor (optional).
     * @return A list of doctors that match the search criteria.
     */
    @Override
    public List<Doctors> searchDoctorDao(String doctorId, String doctorName, String providerId, String specialization, String licenseNo) {
        Session session = sessionFactory.openSession();
        try {
            // Create a Criteria query
            Criteria criteria = session.createCriteria(Doctors.class);
            
            // Apply filters based on the search criteria
            if (doctorId != null && !doctorId.isEmpty()) {
                criteria.add(Restrictions.eq("doctorId", doctorId));
            }
            if (doctorName != null && !doctorName.isEmpty()) {
                criteria.add(Restrictions.like("doctorName", doctorName, MatchMode.ANYWHERE));
            }
            if (providerId != null && !providerId.isEmpty()) {
                criteria.createAlias("providers", "p"); // Assuming `providers` is a related entity in `Doctors`
                criteria.add(Restrictions.eq("p.providerId", providerId));
            }
            if (specialization != null && !specialization.isEmpty()) {
                criteria.add(Restrictions.like("specialization", specialization, MatchMode.ANYWHERE));
            }
            if (licenseNo != null && !licenseNo.isEmpty()) {
                criteria.add(Restrictions.like("licenseNo", licenseNo, MatchMode.ANYWHERE));
            }

            // Execute the query and return the list of matching doctors
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
    
    /**
     * Searches for providers based on various search criteria.
     * 
     * This method allows for flexible search by any combination of the following fields:
     * - Provider ID
     * - Provider Name (supports partial matching)
     * - City (supports partial matching)
     * 
     * @param providerId The ID of the provider to search for (optional).
     * @param providerName The name of the provider to search for (optional).
     * @param city The city where the provider is located (optional).
     * @return A list of providers that match the search criteria.
     */
    @Override
    public List<Provider> searchProviderDao(String providerId, String providerName, String city) {
        Session session = sessionFactory.openSession();
        try {
            // Create a Criteria query for the Provider class
            Criteria criteria = session.createCriteria(Provider.class);

            // Apply filters based on the search criteria
            if (providerId != null && !providerId.isEmpty()) {
                criteria.add(Restrictions.eq("providerId", providerId));
            }
            if (providerName != null && !providerName.isEmpty()) {
                criteria.add(Restrictions.like("providerName", providerName, MatchMode.ANYWHERE));
            }
            if (city != null && !city.isEmpty()) {
                criteria.add(Restrictions.like("city", city, MatchMode.ANYWHERE));
            }

            // Execute the query and return the list of matching providers
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }


    /**
     * Retrieves all the doctors from the database.
     * 
     * This method fetches all the doctor details from the database without any filtering.
     * 
     * @return A list of all doctors.
     */
    @Override
    public List<Doctors> showDoctorDao() {
        Session session = sessionFactory.openSession();
        try {
            // Create a Criteria query to fetch all doctors
            Criteria criteria = session.createCriteria(Doctors.class);
            return criteria.list();
        } finally {
            session.close();
        }
    }
    /**
     * Retrieves all the providers from the database.
     * 
     * This method fetches all the provider details from the database without any filtering.
     * 
     * @return A list of all providers.
     */
    @Override
    public List<Provider> showProviders() {
        Session session = sessionFactory.openSession();
        try {
            // Create a Criteria query to fetch all providers
            Criteria criteria = session.createCriteria(Provider.class);

            // Return the list of all providers
            return criteria.list();
        } finally {
            session.close();
        }
    }


    // Pagination Method for doctor search using Criteria
    /**
     * Searches for doctors with pagination support based on various search criteria.
     * 
     * This method allows for flexible search by any combination of the following fields:
     * - Doctor ID
     * - Doctor Name (supports partial matching)
     * - Provider ID
     * - Specialization (supports partial matching)
     * - License Number (supports partial matching)
     * 
     * In addition, it supports pagination through `page` and `pageSize` parameters.
     * 
     * @param doctorId The ID of the doctor to search for (optional).
     * @param doctorName The name of the doctor to search for (optional).
     * @param providerId The provider ID associated with the doctor (optional).
     * @param specialization The specialization of the doctor (optional).
     * @param licenseNo The license number of the doctor (optional).
     * @param page The page number for pagination (zero-based index).
     * @param pageSize The number of results per page.
     * @return A list of doctors that match the search criteria and pagination settings.
     * @throws IllegalArgumentException if no search criteria are provided or if any invalid criteria are detected.
     */
    public List<Doctors> searchDoctorDaoWithPagination(String doctorId, String doctorName, String providerId, String specialization, String licenseNo, int page, int pageSize) {
        Session session = sessionFactory.openSession();
        
        	
        	 try {
              // Validate inputs before querying
               if (doctorId == null && doctorName == null && providerId == null && specialization == null && licenseNo == null) {
                   throw new IllegalArgumentException("At least one search criteria must be provided.");
               }
               
               // Validate inputs
               if (doctorId != null && !doctorId.isEmpty() && !isValidDoctorId(doctorId)) {
                   throw new IllegalArgumentException("Invalid Doctor ID.");
               }
               if (doctorName != null && doctorName.length() < 6) {
                   throw new IllegalArgumentException("Doctor Name must be at least 6 characters long.");
               }
               if (specialization != null && !isValidSpecialization(specialization)) {
                   throw new IllegalArgumentException("No match found for Specialization.");
               }
               if (providerId != null && !providerId.isEmpty() && !isValidProviderId(providerId)) {
                   throw new IllegalArgumentException("Invalid Provider ID.");
               }
               if (licenseNo != null && !licenseNo.isEmpty() && !isValidLicenseNo(licenseNo)) {
                   throw new IllegalArgumentException("Invalid License Number.");
               }
   
            // Create a Criteria query
            Criteria criteria = session.createCriteria(Doctors.class);

            // Apply filters based on the search criteria
            if (doctorId != null && !doctorId.isEmpty()) {
                criteria.add(Restrictions.eq("doctorId", doctorId.trim().toLowerCase()));
            }
            if (doctorName != null && !doctorName.isEmpty()) {
                criteria.add(Restrictions.like("doctorName", doctorName.trim().toLowerCase(), MatchMode.ANYWHERE));

            }
            if (providerId != null && !providerId.isEmpty()) {
                criteria.createAlias("providers", "p"); // Assuming `providers` is a related entity in `Doctors`
                criteria.add(Restrictions.eq("p.providerId",providerId.trim().toLowerCase()));
            }
            if (specialization != null && !specialization.isEmpty()) {
                criteria.add(Restrictions.like("specialization", specialization, MatchMode.ANYWHERE));
            }
            if (licenseNo != null && !licenseNo.isEmpty()) {
                criteria.add(Restrictions.like("licenseNo", licenseNo, MatchMode.ANYWHERE));
            }

            // Apply pagination
            criteria.setFirstResult(page * pageSize);
            criteria.setMaxResults(pageSize);

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
