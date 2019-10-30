package io.yan.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.yan.common.TypeConversion;
import io.yan.common.config.GeneratorConfig;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import io.yan.common.exception.RRException;
import io.yan.entity.ColumnEntity;
import io.yan.entity.TableEntity;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {

    private static GeneratorConfig generatorConfig;

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        
        URL url = FileUtils.class.getClassLoader().getResource("template");
        File file = new File(url.getPath().substring(1, url.getPath().length()));
        List<File> files = FileUtils.getAllFile(file);
        for (File file2 : files) {
			String url2 = file2.getPath();
			if (!url2.contains("-off")) {
				String substring = url2.substring(url2.indexOf("template"), url2.length());
				templates.add(substring);
			}
		}
        
        return templates;
    }

    
    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip , GeneratorConfig cfg) {
        //配置信息
        generatorConfig = cfg;
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName" ));
        tableEntity.setComments(table.get("tableComment" ));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), generatorConfig.getTablePrefix());
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for(Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            Map<String, String> type = TypeConversion.type;
            type.get(columnEntity.getDataType());

            String attrType = type.get(columnEntity.getDataType());
            if (attrType == null) {
                System.err.println("没能找对对应的Java类型,数据库字段类型=" + columnEntity.getDataType());
                System.err.println("所在表=" + table.get("tableName" ));
            }
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String mainPath = generatorConfig.getMainPath();
        mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package",generatorConfig.getPackageName());
        map.put("moduleName",generatorConfig.getModuleName());
        map.put("author",generatorConfig.getAuthor());
        map.put("email", generatorConfig.getEmail());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        
        
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);
            try {
                //添加到zip
            	
            	String fileName = getFileName(template, tableEntity.getClassName(),generatorConfig.getPackageName(), generatorConfig.getModuleName());
            	System.err.println(fileName);
            	
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), generatorConfig.getPackageName(),generatorConfig.getModuleName())));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
            	e.printStackTrace();
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            //获取application
            PropertiesConfiguration configuration = new PropertiesConfiguration("application.properties");
            String profile = configuration.getString("profile");
            return new PropertiesConfiguration(profile+".properties" );
        } catch (ConfigurationException e) {
        	e.printStackTrace();
            throw new RRException("获取配置文件失败，", e);
        }
    }


    
    
    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        
        if (StringUtils.isBlank(packageName)) return null;
        
        String filedUrl = template.substring(0, template.lastIndexOf("\\"));//获取目录结构
        
        String filedNameSuffix = template.substring(template.lastIndexOf("\\")+1,template.lastIndexOf("."));                
        
        return packagePath + filedUrl + File.separator + className + filedNameSuffix;
    }
}
