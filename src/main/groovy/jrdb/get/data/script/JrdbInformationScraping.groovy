package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup

class JrdbInformationScraping extends BaseScraping {
    // JRDB情報データURL
    def jrdb_information_list_page_url = "http://www.jrdb.com/member/data/Jo/index.html"
    def data_dir = config.data_base_dir.jrdb_information
    def table_name = "jrdb_data_patch.jrdb_information_file"

    def run() {
        def jrdb_information_page = JRDB情報データページへ遷移する()
        JRDB情報データ取得(jrdb_information_page)
        return true
    }

    private def JRDB情報データページへ遷移する() {
        def conn = Jsoup.connect(jrdb_information_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def JRDB情報データ取得(conn){
        def jrdb_information_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        jrdb_information_zip_list.each {
            if(dataSourceFileDao.selectForDataSourceFileByFileName(table_name, it.text()) == null){
                try {
                    def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                            .ignoreContentType(true).execute()
                    println data_dir + it.text()
                    def out = new OutputStreamWriter(new FileOutputStream(new File(data_dir + it.text())))
                    out.write(response.body())
                    out.close()
                    def dto = new DataSourceFileDto()
                    dto.file_name = it.text()
                    dataSourceFileDao.insertDataSourceFile(table_name, dto)
                } catch (HttpStatusException e) {
                    e.stackTrace
                }
            }
        }
    }
}
