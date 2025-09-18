package com.infinite.ejb.admin;

import javax.ejb.Remote;

@Remote
public interface UserBeanRemote {
	String SignUp(User user);

}
