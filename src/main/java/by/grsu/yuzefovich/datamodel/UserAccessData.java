package by.grsu.yuzefovich.datamodel;

public class UserAccessData {
	
	private Long id;
	private String login;
	private String password;
	private Long tenantId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getTenantId() {
		return tenantId;
	}
	
	public void setTenantId(final Long tenantId) {
		this.tenantId = tenantId;
	}
	
	public UserAccessData() {
		
	}
	
	public UserAccessData(String login, String password) {
		this.login = login;
		this.password = password;
	}

}
