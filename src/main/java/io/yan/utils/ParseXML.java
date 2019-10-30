package io.yan.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.yan.entity.DataStructureBean;

/**  
* @ClassName: ParseXML  
* @Description: TODO 
* @author 刘彦青  
* @date 2018年11月6日  
*  
*/
public class ParseXML {
	
	
	public static void main(String[] args) throws Exception {
		InputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\model.xml");
		List<DataStructureBean> importDataRequestBean = ParseXML.getDataStructureBean(fileInputStream);
		for (DataStructureBean dataStructureBean : importDataRequestBean) {
			System.out.println(dataStructureBean.toString());
		}
	}
	
	    /**
	     * @param inStream
	     * @return
	     * @throws Exception
	     */
	    public static List<DataStructureBean> getDataStructureBean(InputStream inStream)
	            throws Exception {
	 
	        List<DataStructureBean> list = new ArrayList<DataStructureBean>();
	        /**
	         * 文档解析
	         */
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.parse(inStream);
	      
	        Element root = document.getDocumentElement();//得到文档的根节点
	        NodeList personNodes = root.getElementsByTagName("field");//获取二级节点
	        
	       
	        
	        System.err.println(personNodes.getLength());
	        for (int i = 0; i < personNodes.getLength(); i++) {
	            Element personElement = (Element) personNodes.item(i);//获取子节点
	            
//	            int id = new Integer(personElement.getAttribute("id"));//获取元素值
	            
	            //获取
	            NodeList childNodes = personElement.getChildNodes();
	            DataStructureBean dataStructureBean = new DataStructureBean();
	            System.err.println(childNodes.getLength());
	            for (int y = 0; y < childNodes.getLength(); y++) {
	                if (childNodes.item(y).getNodeType() == Node.ELEMENT_NODE) {
	                    if ("name".equals(childNodes.item(y).getNodeName())) {
	                        String name = childNodes.item(y).getFirstChild()
	                                .getNodeValue();  
	                        
	                        dataStructureBean.setFieldName(name);
	                    }else if ("type".equals(childNodes.item(y).getNodeName())) {
	                        String type = childNodes.item(y).getFirstChild()
	                                .getNodeValue();
	                        dataStructureBean.setFieldType(type);
	                    }
	                }
	                
	            }
	            list.add(dataStructureBean);
	        }
	        
	        inStream.close();
	        return list;
	    }
}


