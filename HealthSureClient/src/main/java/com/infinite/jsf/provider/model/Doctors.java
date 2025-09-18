/**
 * This class represents a Doctor entity in the system. It holds various details about a doctor
 * including personal information, professional qualifications, and other attributes that define
 * the doctor's status and role.
 * 
**/
package com.infinite.jsf.provider.model;
public class Doctors {
    private String doctorId;
    private Provider providers;
    private String doctorName;
    private Gender gender;
    private String qualification;
    private String specialization;
    private String licenseNo;
    private String email;
    private String phoneNumber;
    private String address;
    private DoctorType type;  // STANDARD or ADHOC
    private DoctorStatus status;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Provider getProviders() {
        return providers;
    }

    public void setProviders(Provider providers) {
        this.providers = providers;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DoctorType getType() {
        return type;
    }

    public void setType(DoctorType type) {
        this.type = type;
    }

    public DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorStatus status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Doctors [doctorId=" + doctorId + ", providers=" + providers + ", doctorName=" + doctorName + ", gender="
				+ gender + ", qualification=" + qualification + ", specialization=" + specialization + ", licenseNo="
				+ licenseNo + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + ", type="
				+ type + ", status=" + status + "]";
	}

	public Doctors() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctors(String doctorId, Provider providers, String doctorName, Gender gender, String qualification,
			String specialization, String licenseNo, String email, String phoneNumber, String address, DoctorType type,
			DoctorStatus status) {
		super();
		this.doctorId = doctorId;
		this.providers = providers;
		this.doctorName = doctorName;
		this.gender = gender;
		this.qualification = qualification;
		this.specialization = specialization;
		this.licenseNo = licenseNo;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.type = type;
		this.status = status;
	}
    
    
}
