/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.global.db2table;

import java.util.Objects;

/**
 *
 * @author joe1962
 */
public class TYP_SysFieldTitlePK {
	private String tableName;
	private Integer fieldOrder;

	public TYP_SysFieldTitlePK() {
	}

	public TYP_SysFieldTitlePK(String tableName, Integer fieldOrder) {
		this.tableName = tableName;
		this.fieldOrder = fieldOrder;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + Objects.hashCode(this.tableName);
		hash = 53 * hash + Objects.hashCode(this.fieldOrder);
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
		final TYP_SysFieldTitlePK other = (TYP_SysFieldTitlePK) obj;
		if (!Objects.equals(this.tableName, other.tableName)) {
			return false;
		}
		return Objects.equals(this.fieldOrder, other.fieldOrder);
	}

}
