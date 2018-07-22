package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class StandardExctaOddScraping extends BaseScraping {
    // 馬単基準オッズデータデータURL
    def standard_excta_odd_list_page_url = "http://www.jrdb.com/member/datazip/Ou/index.html"
    def data_dir = config.data_base_dir.standard_excta_odd
    def table_name = "jrdb_data_patch.standard_excta_odd_file"

    def run() {
        def standard_excta_odd_page = 馬単基準オッズデータデータページへ遷移する()
        馬単基準オッズデータデータ取得(standard_excta_odd_page)
        return true
    }

    private def 馬単基準オッズデータデータページへ遷移する() {
        def conn = Jsoup.connect(standard_excta_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 馬単基準オッズデータデータ取得(conn){
        def standard_excta_odd_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        standard_excta_odd_zip_list.each {
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
