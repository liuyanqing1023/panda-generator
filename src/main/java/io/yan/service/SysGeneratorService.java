package io.yan.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import io.yan.common.config.GeneratorConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import io.yan.dao.SysGeneratorDao;
import io.yan.utils.GenUtils;

/**
 * 代码生成器
 * 
 * @author LYQ
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class SysGeneratorService {

	@Autowired
	private GeneratorConfig generatorConfig;

	@Autowired(required = false)
	private SysGeneratorDao sysGeneratorDao;

	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return sysGeneratorDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorDao.queryTotal(map);
	}

	public Map<String, String> queryTable(String tableName) {
		return sysGeneratorDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorDao.queryColumns(tableName);
	}

	/**
	 * 生成代码
	 * @param tableNames
	 * @return
	 */
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			
			System.err.println("table : "+JSON.toJSONString(table));
			System.err.println("columns : "+JSON.toJSONString(columns ));
			System.err.println("zip : "+JSON.toJSONString(zip));
//			GenUtils.generatorCode(table, columns, zip);
			new GenUtils().generatorCode(table, columns, zip , generatorConfig);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
