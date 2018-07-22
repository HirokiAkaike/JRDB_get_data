package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class DeletedHorseDataScraping extends BaseScraping {
    // 騎手データURL
    def deleted_horse_data_list_page_url = "http://www.jrdb.com/member/data/"
    def data_dir = config.data_base_dir.deleted_horse_data

    def run() {
        def deleted_horse_data_page = 騎手データページへ遷移する()
        騎手データ取得(deleted_horse_data_page)
        return true
    }

    private def 騎手データページへ遷移する() {
        def conn = Jsoup.connect(deleted_horse_data_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 騎手データ取得(conn){
        def deleted_horse_data_zip_list = conn.select('a')
        deleted_horse_data_zip_list.each {
            if (it.attr("href").toString() ==~ /.*MZA.*zip$/) {
                def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                        .ignoreContentType(true).execute()
                def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir +
                        (it.attr("href") =~ /MZA.*zip$/)[0])))
                out.write(response.body())
                out.close()
            }
        }
    }
}