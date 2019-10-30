package io.yan.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yan.entity.ImportDataRequestBean;
import io.yan.service.SysGeneratorService;
import io.yan.utils.PageUtils;
import io.yan.utils.Query;
import io.yan.utils.R;
import io.yan.utils.ReadExcelProductSQL;

/**
 * 代码生成器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/import")
public class SysImportDataController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 显示模板
	 */
	@RequestMapping("/sm")
	public void showModel(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 
	}
	
	/**
	 * 生产SQL语句
	 * @param param
	 * @throws Exception 
	 */
	@RequestMapping("/sql")
	public R  productionSQL(@RequestBody ImportDataRequestBean param) throws Exception {
		//读取Excel文件中的数据
		String url = new ReadExcelProductSQL().productInsertSQL(param);
		return R.ok().put("url", url);
	}	
}
