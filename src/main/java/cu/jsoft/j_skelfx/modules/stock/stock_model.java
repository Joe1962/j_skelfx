package cu.jsoft.j_skelfx.modules.stock;

import java.util.Objects;

public class stock_model {

	private String name;
	private int quantity;
	private String status;

	public stock_model() {
	}

	public stock_model(String name, int quantity, String status) {
		this.name = name;
		this.quantity = quantity;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.name);
		hash = 97 * hash + this.quantity;
		hash = 97 * hash + Objects.hashCode(this.status);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final stock_model other = (stock_model) obj;
		if (this.quantity != other.quantity) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return Objects.equals(this.status, other.status);
	}

}
