package io.yan.entity;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/production/importData.properties")
//@ConfigurationProperties(prefix = "alipay")
public class ImportDataBean {
    private List<String> fieldList;
    private Map<String, String> fieldType;
	public List<String> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<String> fieldList) {
		this.fieldList = fieldList;
	}
	public Map<String, String> getFieldType() {
		return fieldType;
	}
	public void setFieldType(Map<String, String> fieldType) {
		this.fieldType = fieldType;
	}


    
	
}
