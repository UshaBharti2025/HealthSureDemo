
/**
 * Implementation of the RecipientDao interface that provides methods
 * for CRUD operations and search functionalities on Recipient entities.
 * 
 * This class uses Hibernate for data access operations and provides:
 * - Search functionalities for recipients by various criteria (HID, name, mobile, date range)
 * - CRUD operations for updating recipient details
 * - Comprehensive logging for all operations
 */
package com.infinite.jsf.admin.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.infinite.jsf.admin.dao.RecipientDao;
import com.infinite.jsf.recipient.model.Recipient;
import com.infinite.jsf.util.SessionHelper;




public class RecipientDaoImpl implements RecipientDao {
	
	private static final Logger log = Logger.getLogger(RecipientDaoImpl.class);
	
	//====ref variable to get the session objects for connecting with db====
	SessionFactory sf;
	
	//=====variable to open a single unit of work to interact with db===
	Session session;

	
	
	
	
	/**
	 * Searches for a recipient by their HID (Health ID).
	 * 
	 * This method queries the database to find a recipient using the specified HID.
	 * If a recipient with the given HID is found, it returns the corresponding Recipient object;
	 * otherwise, it returns null.
	 * 
	 * @param hId The HID of the recipient to search for.
	 * @return A Recipient object corresponding to the given HID, or null if not found.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public Recipient searchByHid(String hId) {
		log.info("Starting searchByHid method for HID: " + hId);
		
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		
		try {
			Query query = session.getNamedQuery("searchByHid");
			query.setParameter("hId", hId);
			Recipient recipient = (Recipient) query.uniqueResult();
			
			if (recipient == null) {
				log.warn("Recipient not found with HID: " + hId);
			} else {
				log.info("Recipient found with HID: " + hId);
			}
			
			return recipient;
		} catch (Exception e) {
			log.error("Error fetching recipient by HID " + hId + ": " + e.getMessage(), e);
			throw e;
		} finally {
			session.close();
			log.info("searchByHid method completed");
		}
	}
	
//	public Recipient getRecipientByhId(String hId) {
//		log.info("Starting getRecipientByhId method for HID: " + hId);
//		
//		sf = SessionHelper.getConnection();
//		session = sf.openSession();
//
//		try {
//			// Fetching by primary key
//			Recipient recipient = (Recipient) session.get(Recipient.class, hId);
//			
//			if (recipient == null) {
//				log.warn("Recipient not found with HID: " + hId);
//			} else {
//				log.info("Recipient found with HID: " + hId);
//			}
//			
//			return recipient;
//		} catch (Exception e) {
//			log.error("Error fetching recipient by HID " + hId + ": " + e.getMessage(), e);
//			throw e;
//		} finally {
//			session.close();
//			log.info("getRecipientByhId method completed");
//		}

	
	
	
	
	/**
	 * Searches for recipients whose first name starts with the specified string.
	 * 
	 * This method performs a case-insensitive search for recipients whose first name
	 * begins with the given string. It returns a list of matching Recipient objects.
	 * 
	 * @param firstName The starting string of the first name to search for.
	 * @return A list of Recipient objects whose first name starts with the specified string.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public List<Recipient> searchByFirstNameStartsWith(String firstName) {
		log.info("Starting searchByFirstNameStartsWith method for: " + firstName);
		
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		
		try {
			Query query = session.createQuery("from Recipient where lower(firstName) like :name");
			query.setParameter("name", firstName.toLowerCase() + "%");
			List<Recipient> result = query.list();
			
			log.info("Found " + result.size() + " recipients with first name starting with: " + firstName);
			return result;
		} catch (Exception e) {
			log.error("Error searching recipients by first name starts with " + firstName + 
					": " + e.getMessage(), e);
			throw e;
		} finally {
			session.close();
			log.info("searchByFirstNameStartsWith method completed");
		}
	}

	/**
	 * Searches for recipients whose first name contains the specified string.
	 * 
	 * This method performs a case-insensitive search for recipients whose first name
	 * contains the given string anywhere within it. It returns a list of matching Recipient objects.
	 * 
	 * @param firstName The string to search for within first names.
	 * @return A list of Recipient objects whose first name contains the specified string.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public List<Recipient> searchByFirstNameContains(String firstName) {
		log.info("Starting searchByFirstNameContains method for: " + firstName);
		
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		
		try {
			Query query = session.createQuery("from Recipient where lower(firstName) like :name");
			query.setParameter("name", "%" + firstName.toLowerCase() + "%");
			List<Recipient> result = query.list();
			
			log.info("Found " + result.size() + " recipients with first name containing: " + firstName);
			return result;
		} catch (Exception e) {
			log.error("Error searching recipients by first name contains " + firstName + 
					": " + e.getMessage(), e);
			throw e;
		} finally {
			session.close();
			log.info("searchByFirstNameContains method completed");
		}
	}
	
	/**
	 * Searches for recipients with an exact first name match.
	 * 
	 * This method performs a case-insensitive search for recipients whose first name
	 * exactly matches the given string. It returns a list of matching Recipient objects.
	 * 
	 * @param firstName The exact first name to search for.
	 * @return A list of Recipient objects whose first name exactly matches the specified string.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public List<Recipient> searchByFirstNameExact(String firstName) {
		log.info("Starting searchByFirstNameExact method for: " + firstName);
		
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		
		try {
			Query query = session.createQuery("from Recipient where lower(firstName) = :name");
			query.setParameter("name", firstName.toLowerCase());
			List<Recipient> result = query.list();
			
			log.info("Found " + result.size() + " recipients with exact first name: " + firstName);
			return result;
		} catch (Exception e) {
			log.error("Error searching recipients by exact first name " + firstName + 
					": " + e.getMessage(), e);
			throw e;
		} finally {
			session.close();
			log.info("searchByFirstNameExact method completed");
		}
	}

	
	
	
	
	
	
	/**
	 * Searches for recipients by mobile number.
	 * 
	 * This method searches for recipients whose mobile number contains the specified string.
	 * It returns a list of matching Recipient objects. If the mobile parameter is null or empty,
	 * it returns an empty list.
	 * 
	 * @param mobile The mobile number (or partial number) to search for.
	 * @return A list of Recipient objects whose mobile number contains the specified string.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public List<Recipient> searchByMobile(String mobile) {
		log.info("Starting searchByMobile method for: " + mobile);
		
		if (mobile == null || mobile.trim().isEmpty()) {
			log.warn("Mobile number is null or empty, returning empty list");
			return new ArrayList<>();
		}

		sf = SessionHelper.getConnection();
		session = sf.openSession();

		try {
			Query query = session.getNamedQuery("searchByMobile");
			query.setParameter("mobile", "%" + mobile + "%");
			List<Recipient> result = query.list();
			
			log.info("Found " + result.size() + " recipients with mobile containing: " + mobile);
			return result;
		} catch (Exception e) {
			log.error("Error searching recipients by mobile " + mobile + ": " + e.getMessage(), e);
			throw e;
		} finally {
			session.close();
			log.info("searchByMobile method completed");
		}
	}

	
	
	
	
	
	/**
	 * Retrieves all recipients from the database.
	 * 
	 * This method fetches all recipient details from the database without any filtering.
	 * 
	 * @return A list of all Recipient objects in the database.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public List<Recipient> showAllRecipients() {
		log.info("Starting showAllRecipients method");
		
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		
		try {
			Query query = session.getNamedQuery("showAllRecipients");
			List<Recipient> recipientList = query.list();
			
			log.info("Found " + recipientList.size() + " total recipients");
			return recipientList;
		} catch (Exception e) {
			log.error("Error fetching all recipients: " + e.getMessage(), e);
			throw e;
		} finally {
			session.close();
			log.info("showAllRecipients method completed");
		}
	}

	
	
	
	
	/**
	 * Updates a recipient's details in the database.
	 * 
	 * This method updates the details of an existing recipient identified by their HID.
	 * It updates fields such as first name, last name, mobile, email, address, and last modified timestamp.
	 * 
	 * @param recipient The Recipient object containing the updated information.
	 * @return true if the recipient was successfully updated, false if the recipient was not found.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public boolean updateRecipient(Recipient recipient) {
		log.info("Starting updateRecipient method for HID: " + recipient.gethId());
		
		sf = SessionHelper.getConnection();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			Recipient existing = (Recipient) session.get(Recipient.class, recipient.gethId());

			if (existing == null) {
				log.warn("Recipient not found for update with HID: " + recipient.gethId());
				return false; // Not found
			}

			
			
			existing.setFirstName(recipient.getFirstName());
			existing.setLastName(recipient.getLastName());
			existing.setMobile(recipient.getMobile());
			existing.setEmail(recipient.getEmail());
			existing.setAddress(recipient.getAddress());
			existing.setLastModifiedAt(new Date());

			session.update(existing);
			tx.commit();
			
			log.info("Recipient updated successfully for HID: " + recipient.gethId());
			return true;

		} catch (Exception e) {
			log.error("Error updating recipient with HID " + recipient.gethId() + 
					": " + e.getMessage(), e);
			if (tx != null) tx.rollback();
			throw e;
		} finally {
			session.close();
			log.info("updateRecipient method completed");
		}
	}
	   
	
	
	
	
	/**
	 * Retrieves a recipient by their HID.
	 * 
	 * This method fetches a single recipient from the database using their HID as the primary key.
	 * 
	 * @param hId The HID of the recipient to retrieve.
	 * @return A Recipient object corresponding to the given HID, or null if not found.
	 * @throws Exception if an error occurs during the database operation.
	 */
//	@Override
//	public Recipient getRecipientByhId(String hId) {
//		log.info("Starting getRecipientByhId method for HID: " + hId);
//		
//		sf = SessionHelper.getConnection();
//		session = sf.openSession();
//
//		try {
//			// Fetching by primary key
//			Recipient recipient = (Recipient) session.get(Recipient.class, hId);
//			
//			if (recipient == null) {
//				log.warn("Recipient not found with HID: " + hId);
//			} else {
//				log.info("Recipient found with HID: " + hId);
//			}
//			
//			return recipient;
//		} catch (Exception e) {
//			log.error("Error fetching recipient by HID " + hId + ": " + e.getMessage(), e);
//			throw e;
//		} finally {
//			session.close();
//			log.info("getRecipientByhId method completed");
//		}
//	} 

	
	
	
	
	
	
	/**
	 * Searches for recipients by last modified date range.
	 * 
	 * This method searches for recipients whose last modified date falls within the specified range.
	 * It returns a list of Recipient objects that were modified between the fromDate and toDate.
	 * 
	 * @param fromDate The start date of the range (inclusive).
	 * @param toDate The end date of the range (inclusive).
	 * @return A list of Recipient objects modified within the specified date range.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public List<Recipient> searchByLastModifiedRange(String fromDate, String toDate) {
		log.info("Starting searchByLastModifiedRange method from: " + fromDate + " to: " + toDate);
		
		Session session = SessionHelper.getConnection().openSession();
		Transaction tx = null;
		List<Recipient> list = new ArrayList<>();
		
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Recipient WHERE lastModifiedAt BETWEEN :fromDate AND :toDate");
			query.setParameter("fromDate", java.sql.Date.valueOf(fromDate));
			query.setParameter("toDate", java.sql.Date.valueOf(toDate));
			list = query.list();
			tx.commit();
			
			log.info("Found " + list.size() + " recipients in date range from " + fromDate + " to " + toDate);
			return list;
		} catch (Exception e) {
			log.error("Error searching recipients by last modified range from " + fromDate + 
					" to " + toDate + ": " + e.getMessage(), e);
			if (tx != null) tx.rollback();
			throw e;
		} finally {
			session.close();
			log.info("searchByLastModifiedRange method completed");
		}
	}
}








