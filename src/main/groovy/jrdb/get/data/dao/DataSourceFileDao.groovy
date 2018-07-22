package jrdb.get.data.dao

import jrdb.get.data.base.BaseDao
import jrdb.get.data.dto.DataSourceFileDto

import java.sql.SQLException

class DataSourceFileDao extends BaseDao {

    def selectForDataSourceFileByFileName(def table_name, def file_name) throws SQLException {
        String query =  "SELECT file_name FROM ${table_name.toString()} WHERE file_name = '${file_name}'"

        def dto = new DataSourceFileDto()
        dto.file_name = sql.rows(query)[0]
        if(dto.file_name == null){
            return null
        } else {
            return dto
        }

    }

    void insertDataSourceFile(def table_name,  DataSourceFileDto dto) {
        println dto.file_name
        String query = "INSERT INTO ${table_name} VALUES('${dto.file_name.toString()}')"
        sql.executeUpdate(query)
    }
}