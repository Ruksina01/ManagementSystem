package librarysystem;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class ResultSetTableModel extends AbstractTableModel {
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int rowCount;

    public ResultSetTableModel(ResultSet resultSet) throws SQLException {
        this.resultSet = resultSet;
        this.metaData = resultSet.getMetaData();
        resultSet.last();
        rowCount = resultSet.getRow();
        resultSet.beforeFirst();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        try {
            return metaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getColumnName(int column) {
        try {
            return metaData.getColumnName(column + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            resultSet.absolute(rowIndex + 1);
            return resultSet.getObject(columnIndex + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
