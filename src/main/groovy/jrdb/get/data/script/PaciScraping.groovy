package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class PaciScraping extends BaseScraping {
    // JRDB前走データURL
    def paci_list_page_url = "http://www.jrdb.com/member/datazip/Paci/index.html"
    def data_dir = config.data_base_dir.paci
    def table_name = "jrdb_data_patch.paci_file"

    def run() {
        def paci_page = PACIデータページへ遷移する()
        PACI前走データ取得(paci_page)
        return true
    }

    private def PACIデータページへ遷移する() {
        def conn = Jsoup.connect(paci_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def PACI前走データ取得(conn){
        def paci_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        paci_zip_list.each {
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
