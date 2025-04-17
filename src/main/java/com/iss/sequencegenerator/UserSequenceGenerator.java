package com.iss.sequencegenerator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;

public class UserSequenceGenerator extends SequenceStyleGenerator{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
	    public static final String VALUE_PREFIX_DEFAULT = "";
	    private String valuePrefix;
	 
	    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
	    public static final String NUMBER_FORMAT_DEFAULT = "%d";
	    private String numberFormat;
	 
	    @Override
	    public Serializable generate(SharedSessionContractImplementor session,
	            Object object) throws HibernateException {
	        return valuePrefix + String.format(numberFormat, super.generate(session, object));
	    }
	 
//	    @Override
//	    public void configure(Type type, Properties params,
//	            ServiceRegistry serviceRegistry) throws MappingException {
//	        super.configure((org.hibernate.type.Type) LongJavaType.INSTANCE, params, serviceRegistry);
//	        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER,
//	                params, VALUE_PREFIX_DEFAULT);
//	        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER,
//	                params, NUMBER_FORMAT_DEFAULT);
//	    }
	    
	    @Override
	    public void configure(org.hibernate.type.Type type, Properties parameters, ServiceRegistry serviceRegistry)
	    		throws MappingException {
	    	super.configure(type, parameters, serviceRegistry);
	    	valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER,
	    			parameters, VALUE_PREFIX_DEFAULT);
	        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER,
	        		parameters, NUMBER_FORMAT_DEFAULT);
	    }
}
