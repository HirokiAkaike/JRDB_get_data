package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class StandardQuinellaPlaceOddsScraping extends BaseScraping {
    // JRDBワイド基準オッズデータURL
    def standard_quinella_place_odd_list_page_url = "http://www.jrdb.com/member/datazip/Oz/index2.html"
    def data_dir = config.data_base_dir.standard_quinella_place_odd
    def table_name = "jrdb_data_patch.standard_quinella_place_odd_file"

    def run() {
        def standard_quinella_place_odd_page = JRDBワイド基準オッズデータページへ遷移する()
        JRDBワイド基準オッズデータ取得(standard_quinella_place_odd_page)
        return true
    }

    private def JRDBワイド基準オッズデータページへ遷移する() {
        def conn = Jsoup.connect(standard_quinella_place_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def JRDBワイド基準オッズデータ取得(conn){
        def standard_quinella_place_odd_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        standard_quinella_place_odd_zip_list.each {
            if(dataSourceFileDao.selectForDataSourceFileByFileName(table_name, it.text()) == null){
                def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                        .ignoreContentType(true).execute()
                println data_dir + it.text()
                def out = new OutputStreamWriter(new FileOutputStream(new File(data_dir + it.text())))
                out.write(response.body())
                out.close()
                def dto = new DataSourceFileDto()
                dto.file_name = it.text()
                dataSourceFileDao.insertDataSourceFile(table_name, dto)
            }
        }
    }
}
