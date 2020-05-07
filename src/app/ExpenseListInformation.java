package app;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListInformation {
	private String loginId;
	private String role;
	private List<Expense> expenseList = new ArrayList<>();

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<Expense> expenseList) {
		this.expenseList = expenseList;
	}
}
