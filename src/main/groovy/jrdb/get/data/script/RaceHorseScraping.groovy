package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.dao.DataSourceFileDao
import jrdb.get.data.dto.DataSourceFileDto
import org.jsoup.Jsoup

class RaceHorseScraping extends BaseScraping {
    // 競走馬データURL
    def holding_race_list_page_url = "http://www.jrdb.com/member/datazip/Kab/index.html"
    def data_dir = config.data_base_dir.race_horse
    def table_name = "jrdb_data_patch.race_horse_file"

    def run() {
        def holding_race_page = 競走馬データページへ遷移する()
        競走馬データ取得(holding_race_page)
        return true
    }

    private def 競走馬データページへ遷移する() {
        def conn = Jsoup.connect(holding_race_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 競走馬データ取得(conn){
        def race_horse_zip_list = conn.select('table li a')
        def dataSourceFileDao = new DataSourceFileDao()
        race_horse_zip_list.each {
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
