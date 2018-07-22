package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class StandardOddScraping extends BaseScraping {
    // 基準オッズデータURL
    def standard_odd_list_page_url = "http://www.jrdb.com/member/datazip/Oz/index.html"
    def data_dir = config.data_base_dir.standard_odd
    def table_name = "jrdb_data_patch.standard_odd_file"

    def run() {
        def standard_odd_page = 基準オッズデータページへ遷移する()
        基準オッズデータ取得(standard_odd_page)
        return true
    }

    private def 基準オッズデータページへ遷移する() {
        def conn = Jsoup.connect(standard_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 基準オッズデータ取得(conn){
        def standard_odd_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        standard_odd_zip_list.each {
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
