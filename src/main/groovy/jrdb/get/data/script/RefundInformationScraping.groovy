package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class RefundInformationScraping extends BaseScraping {
    // 払戻データURL
    def refund_information_list_page_url = "http://www.jrdb.com/member/datazip/Hjc/index.html"
    def data_dir = config.data_base_dir.refund_information
    def table_name = "jrdb_data_patch.refund_information_file"

    def run() {
        def refund_information_page = 払戻データページへ遷移する()
        払戻データ取得(refund_information_page)
        return true
    }

    private def 払戻データページへ遷移する() {
        def conn = Jsoup.connect(refund_information_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 払戻データ取得(conn){
        def refund_information_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        refund_information_zip_list.each {
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
