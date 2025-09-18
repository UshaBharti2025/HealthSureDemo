package com.infinite.jsf.admin.dao;

import java.util.List;


import com.infinite.jsf.recipient.model.Recipient;



/**
 * RecipientDao.java
 * 
 */

public interface RecipientDao {

	 //----Search by health ID (exact match)----
    Recipient searchByHid(String hId);
//    List<Recipient> searchByHid(String hId);

    // Search by mobile (LIKE %mobile%)
    List<Recipient> searchByMobile(String mobile);

    // Show all recipients
    List<Recipient> showAllRecipients();

    // Update recipient
    boolean updateRecipient(Recipient recipient);
    
//    Recipient getRecipientByhId(String hId);


    /* ---------- NEW name based search methods ---------- */

    /**
     * Returns recipients whose first name starts with the supplied prefix
     * (case insensitive, SQL LIKE 'prefix%').
     */
    List<Recipient> searchByFirstNameStartsWith(String firstNamePrefix);

    /**
     * Returns recipients whose first name contains the supplied text anywhere
     * (case insensitive, SQL LIKE '%text%').
     */
    List<Recipient> searchByFirstNameContains(String firstNameFragment);
    
    List<Recipient> searchByFirstNameExact(String firstName);

 // Add new method signature
    List<Recipient> searchByLastModifiedRange(String fromDate, String toDate);

}
