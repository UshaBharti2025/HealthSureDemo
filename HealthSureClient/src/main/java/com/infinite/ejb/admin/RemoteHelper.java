package com.infinite.ejb.admin;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RemoteHelper {
	private static Context createInitialContext() throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, 
          "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES, 
          "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.PROVIDER_URL, 
           "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(jndiProperties);
    }
	public static UserBeanRemote lookUpRemoteStatelessUserNew() throws NamingException {
		  Context ctx = createInitialContext();
		   UserBeanRemote bean = (UserBeanRemote) ctx.lookup(
				    "ejb:/HealthSureEjb/UserBean!com.infinite.ejb.admin.UserBeanRemote");
		   return bean;

	}
}
