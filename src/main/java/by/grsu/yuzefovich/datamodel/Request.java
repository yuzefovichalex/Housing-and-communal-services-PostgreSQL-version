package by.grsu.yuzefovich.datamodel;

import java.io.Serializable;

public class Request extends AbstractModel implements Serializable {
	
	private String typeOfWork;
	private Integer scopeOfWork;
	private Integer leadTime;
	private Boolean isAccepted;
	private Long tenantId;
	private Long brigadeId;
	
	public String getTypeOfWork() {
		return typeOfWork;
	}
	
	public void setTypeOfWork(final String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}
	
	public Integer getScopeOfWork() {
		return scopeOfWork;
	}
	
	public void setScopeOfWork(final Integer scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}
	
	public Integer getLeadTime() {
		return leadTime;
	}
	
	public void setLeadTime(final Integer leadTime) {
		this.leadTime = leadTime;
	}
	
	public Long getBrigadeId() {
		return brigadeId;
	}
	
	public void setBrigadeId(final Long brigadeId) {
		this.brigadeId = brigadeId;
	}
	
	public Boolean getIsAccepted() {
		return isAccepted;
	}
	
	public void setIsAccepted(final Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	
	public Long getTenantId() {
		return tenantId;
	}
	
	public void setTenantId(final Long tenantId) {
		this.tenantId = tenantId;
	}
	
	public Request() {
		
	}
	
	public Request(Long id) {
		this.setId(id);
	}
	
	public Request(String typeOfWork, int scopeOfWork, int leadTime, boolean isAccepted) {
		this.typeOfWork = typeOfWork;
		this.scopeOfWork = scopeOfWork;
		this.leadTime = leadTime;
		this.isAccepted = isAccepted;
	}

}
