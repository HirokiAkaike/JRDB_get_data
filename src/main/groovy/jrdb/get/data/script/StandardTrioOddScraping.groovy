package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class StandardTrioOddScraping extends BaseScraping {
    // ３連複基準オッズデータURL
    def standard_trio_odd_list_page_url = "http://www.jrdb.com/member/datazip/Ot/index.html"
    def data_dir = config.data_base_dir.standard_trio_odd
    def table_name = "jrdb_data_patch.standard_trio_odd_file"

    def run() {
        def standard_trio_odd_page = "３連複基準オッズデータページへ遷移する"()
        "３連複基準オッズデータ取得"(standard_trio_odd_page)
        return true
    }

    private def "３連複基準オッズデータページへ遷移する"() {
        def conn = Jsoup.connect(standard_trio_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def "３連複基準オッズデータ取得"(conn){
        def standard_trio_odd_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        standard_trio_odd_zip_list.each {
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
