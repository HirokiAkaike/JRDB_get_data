package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class ScoreExtensionDataScraping extends BaseScraping {
    // 成績拡張データURL
    def score_extension_data_list_page_url = "http://www.jrdb.com/member/datazip/Skb/index.html"
    def data_dir = config.data_base_dir.score_extension_data
    def table_name = "jrdb_data_patch.score_extension_data_file"

    def run() { 
        def score_extension_data_page = 成績拡張データページへ遷移する()
        成績拡張データ取得(score_extension_data_page)
        return true
    }

    private def 成績拡張データページへ遷移する() {
        def conn = Jsoup.connect(score_extension_data_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 成績拡張データ取得(conn){
        def score_extension_data_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        score_extension_data_zip_list.each {
            if (dataSourceFileDao.selectForDataSourceFileByFileName(table_name, it.text()) == null) {
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
