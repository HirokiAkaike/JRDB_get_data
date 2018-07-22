package jrdb.get.data.dao

import jrdb.get.data.base.BaseDao
import jrdb.get.data.dto.DataSourceFileDto

import java.sql.SQLException

class DataSourceFileDao extends BaseDao {

    def selectForDataSourceFileByFileName(def table_name, def file_name) throws SQLException {
        String query =  "SELECT file_name FROM ${table_name.toString()} WHERE file_name = '${file_name}'"
        try{
            sql.withTransaction {}
            return new DataSourceFileDto().setFile_name(
                    sql.rows(query)[0]
            )
        } catch (SQLException e) {
            sql.close()
            throw e
        } finally {
            if(!(sql == null)) {
                sql.close()
            }
        }

    }

    void insertDataSourceFile(def table_name,  DataSourceFileDto dto) {l
        String query = "INSERT INTO ${table_name} VALUES('${dto.getFile_name().toString()}')"
        try {
            sql.executeUpdate(query)
        } catch(SQLException e) {
            sql.close()
            throw e
        } finally {
            if(!(sql == null)){
                sql.close()
            }
        }
    }
}