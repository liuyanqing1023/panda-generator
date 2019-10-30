package io.yan.entity;

/**  
* @ClassName: DataStructureBean  
* @Description: TODO 
* @author 刘彦青  
* @date 2018年11月6日  
*  
*/
public class DataStructureBean {
	private String fieldName;
	private String fieldType;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	public String toString() {
		return "DataStructureBean [fieldName=" + fieldName + ", fieldType=" + fieldType + "]";
	}
	
}


