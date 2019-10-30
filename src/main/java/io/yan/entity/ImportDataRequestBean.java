package io.yan.entity;

public class ImportDataRequestBean {
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 数据全路径
	 */
    private String excelFieldUrl;
    /**
     * 数据模型全路径
     */
    private String modelFieldUrl;
    
	public String getExcelFieldUrl() {
		return excelFieldUrl;
	}
	public void setExcelFieldUrl(String excelFieldUrl) {
		this.excelFieldUrl = excelFieldUrl;
	}
	public String getModelFieldUrl() {
		return modelFieldUrl;
	}
	public void setModelFieldUrl(String modelFieldUrl) {
		this.modelFieldUrl = modelFieldUrl;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String toString() {
		return "ImportDataRequestBean [tableName=" + tableName + ", excelFieldUrl=" + excelFieldUrl + ", modelFieldUrl="
				+ modelFieldUrl + "]";
	}
}
