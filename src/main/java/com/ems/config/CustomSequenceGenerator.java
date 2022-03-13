package com.ems.config;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ems.dataobject.Task;
import com.ems.repo.ITaskRepository;

@Component
public class CustomSequenceGenerator extends SequenceStyleGenerator  {

	@Autowired
	ITaskRepository taskRepo;
	
	public static final String VALUE_PREFIX_PARAMETER="valuePrefix";
	public static final String VALUE_PREFIX_DEFAULT = "";
	private String valuePrefix;
	
	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
	public static final String NUMBER_FORMAT_DEFAULT = "%d";
	private String numberFormat;
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		/*
		 * // TODO Auto-generated method stub String prefix = "EMS"; Task task =
		 * taskRepo.findTopByOrderByIdDesc(); long id = 00; if(task!=null) { id =
		 * Long.parseLong(task.taskNumber.split("-")[1]); }
		 * 
		 * id++;
		 * 
		 * return prefix+id;
		 */
		return valuePrefix + String.format(numberFormat, super.generate(session, object));
	}
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		// TODO Auto-generated method stub
		super.configure(LongType.INSTANCE, params, serviceRegistry);
		valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER,params, VALUE_PREFIX_DEFAULT);
		numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT);
	}

	
	
	
	
}
