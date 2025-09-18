/**
 * This `DoctorDao` interface defines the data access methods related to the `Doctor` and `Provider` entities.
 * It provides methods for:
 * 
 * - **Provider Review and Approval**: Operations to review and update the status of providers.
 * - **Provider Search and Inquiry**: Searching for providers and doctors based on various parameters.
 * - **Provider Update Details**: Fetching and updating specific provider and doctor details.
 */
package com.infinite.jsf.admin.dao;
import java.util.List;
import com.infinite.jsf.provider.model.Doctors;
import com.infinite.jsf.provider.model.Provider;

public interface DoctorDao {
	
	// Provider Review and Approval
	List<Provider> reviewProviderDetails();
	Provider searchProviderById(String providerId);
	String updateProviderStatus(Provider providerId, String status);
	
	
	// Provider Search and Inquiry
	List<Doctors> showDoctorDao();
	List<Doctors> searchDoctorDao(String doctorId, String doctorName, String providerId, String specialization,String licenseNo);
	List<Provider> searchProviderDao(String providerId, String providerName, String city);
	List<Provider> showProviders();
	
	// Provider Update Details
	Doctors getDoctorById(String doctorId);
	void updateDoctor(Doctors doctor) throws Exception;
	Provider getProviderById(String providerId);
	void updateProvider(Provider provider) throws Exception;
	
	


}
