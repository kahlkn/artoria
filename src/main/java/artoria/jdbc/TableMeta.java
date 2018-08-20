package artoria.jdbc;

import java.io.Serializable;
import java.util.List;

/**
 * Database table necessary information.
 * @author Kahle
 */
public class TableMeta implements Serializable {

    /**
     * "TABLE_NAME"
     */
    private String name;

    /**
     * "REMARKS"
     */
    private String remarks;

    /**
     * Table primary keys.
     * If is Composite Primary Key, use "," separate.
     */
    private String primaryKey;

    /**
     * Table's column meta list.
     */
    private List<ColumnMeta> columnMetaList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ColumnMeta> getColumnMetaList() {
        return columnMetaList;
    }

    public void setColumnMetaList(List<ColumnMeta> columnMetaList) {
        this.columnMetaList = columnMetaList;
    }

}