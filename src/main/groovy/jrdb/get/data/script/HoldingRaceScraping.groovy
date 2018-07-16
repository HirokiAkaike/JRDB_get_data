package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import jrdb.get.data.common.JrdbConstant
import org.jsoup.Jsoup

class HoldingRaceScraping extends BaseScraping {
    // 開催データURL
    def holding_race_list_page_url = "http://www.jrdb.com/member/datazip/Kab/index.html"
    def data_dir = config.data_base_dir.holding_race

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
        holding_race_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
