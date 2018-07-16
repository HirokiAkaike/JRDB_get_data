package jrdb.get.data.dao

import jrdb.get.data.base.BaseDao
import jrdb.get.data.dto.DataSourceFileDto

class DataSourceFileDao extends BaseDao {

    def selectForDataSourceFileByFileName(def table_name, def file_name){
        return new DataSourceFileDto().setFile_name(
                sql.rows("SELECT file_name from ${table_name} WHERE file_name = ${file_name}")[0].file_name
        )
    }

    void insertDataSourceFile(def table_name,  DataSourceFileDto dto){
        sql.executeUpdate("INSERT INTO ${table_name} VALUES((?))", [dto.getFile_name().toString()])
    }
}