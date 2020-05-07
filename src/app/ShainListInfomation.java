package app;

import java.util.ArrayList;
import java.util.List;

public class ShainListInfomation {
	private String loginId;
	private String role;
	private List<Shain> shainList = new ArrayList<>();

	public List<Shain> getShainList() {
		return shainList;
	}

	public void setShainList(List<Shain> shainList) {
		this.shainList = shainList;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
