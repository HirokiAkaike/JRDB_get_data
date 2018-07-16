package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class TrainerDataScraping extends BaseScraping {
    // 調教師データURL
    def trainer_data_list_page_url = "http://www.jrdb.com/member/data/"
    def data_dir = config.data_base_dir.trainer_data

    def run() {
        def trainer_data_page = 調教師データページへ遷移する()
        調教師データ取得(trainer_data_page)
        return true
    }

    private def 調教師データページへ遷移する() {
        def conn = Jsoup.connect(trainer_data_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 調教師データ取得(conn){
        def trainer_data_zip_list = conn.select('a')
        trainer_data_zip_list.each {
            if (it.attr("href").toString() ==~ /.*CZA.*zip$/) {
                def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                        .ignoreContentType(true).execute()
                def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir +
                        (it.attr("href") =~ /CZA.*zip$/)[0])))
                out.write(response.body())
                out.close()
            }
        }
    }
}
