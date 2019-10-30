package io.yan.test.bean;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.yan.common.validator.ValidatorUtils;
import io.yan.common.validator.group.SaveGroup;

	/**  
	* @ClassName: ValidatorContrall  
	* @Description: TODO 
	* @author 刘彦青  
	* @date 2019年1月22日  
	*/

@Controller
public class ValidatorContrall {

	    @RequestMapping("/resign")
	    public String resign(@RequestBody @Valid  UserBean user ) {
	    	
	    	
	        return "success";
	    }
	    @RequestMapping("/resign2")
	    public String resign2(@RequestBody  UserBean user ) {
	    	ValidatorUtils.validateEntity(user , SaveGroup.class);
	    	return "success";
	    }
}