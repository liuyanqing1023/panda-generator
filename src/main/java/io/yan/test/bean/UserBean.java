package io.yan.test.bean;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.yan.common.validator.group.SaveGroup;
import io.yan.common.validator.group.UpdateGroup;

/**  
* @ClassName: asd  
* @Description: TODO 
* @author 刘彦青  
* @date 2019年1月22日  
*  
*/
public class UserBean {
    @NotBlank(message="用户名不能为空" , groups = { SaveGroup.class,UpdateGroup.class})
    private String userName;

    @NotBlank(message="年龄不能为空")
    @Pattern(regexp="^[0-9]{1,2}$",message="年龄不正确")
    private String age;

    @AssertFalse(message = "必须为false")
    private Boolean isFalse;
    /**
     * 如果是空，则不校验，如果不为空，则校验
     */
    @Pattern(regexp="^[0-9]{4}-[0-9]{2}-[0-9]{2}$",message="出生日期格式不正确", groups = { SaveGroup.class,UpdateGroup.class})
    private String birthday;
	
    
    
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Boolean getIsFalse() {
		return isFalse;
	}
	public void setIsFalse(Boolean isFalse) {
		this.isFalse = isFalse;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
    
    
    
}



