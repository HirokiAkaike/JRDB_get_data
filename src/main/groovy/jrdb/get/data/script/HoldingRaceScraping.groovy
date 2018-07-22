package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.common.JrdbConstant
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class HoldingRaceScraping extends BaseScraping {
    // 開催データURL
    def holding_race_list_page_url = "http://www.jrdb.com/member/datazip/Kab/index.html"
    def data_dir = config.data_base_dir.holding_race
    def table_name = "jrdb_data_patch.holding_race_file"

    def run() {
        def holding_race_page = 開催データページへ遷移する()
        開催データ取得(holding_race_page)
        return true
    }

    private def 開催データページへ遷移する() {
        def conn = Jsoup.connect(holding_race_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 開催データ取得(conn){
        def holding_race_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        holding_race_zip_list.each {
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
