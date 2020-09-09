package cn.com.sgcc.vo;

public class FileEncryption {
	private int id;
	private int order;
	private String fileName;
	private String fileDir;
	private String isNotEncrypted;
	private String isSuccessed;
	private String isFailed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getIsNotEncrypted() {
		return isNotEncrypted;
	}
	public void setIsNotEncrypted(String isNotEncrypted) {
		this.isNotEncrypted = isNotEncrypted;
	}
	public String getIsSuccessed() {
		return isSuccessed;
	}
	public void setIsSuccessed(String isSuccessed) {
		this.isSuccessed = isSuccessed;
	}
	public String getIsFailed() {
		return isFailed;
	}
	public void setIsFailed(String isFailed) {
		this.isFailed = isFailed;
	}


}
