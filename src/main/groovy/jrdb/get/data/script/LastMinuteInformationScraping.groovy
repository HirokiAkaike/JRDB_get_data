package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class LastMinuteInformationScraping extends BaseScraping {
    // 直前情報データURL
    def paci_list_page_url = "http://www.jrdb.com/member/datazip/Tyb/index.html"
    def data_dir = config.data_base_dir.last_minute_information
    def table_name = "jrdb_data_patch.last_minute_information_file"

    def run() {
        def last_minute_information_page = 直前情報データページへ遷移する()
        直前情報データ取得(last_minute_information_page)
        return true
    }

    private def 直前情報データページへ遷移する() {
        def conn = Jsoup.connect(paci_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 直前情報データ取得(conn){
        def last_minute_information_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        last_minute_information_zip_list.each {
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
