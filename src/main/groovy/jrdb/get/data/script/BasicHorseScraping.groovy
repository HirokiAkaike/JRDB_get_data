package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class BasicHorseScraping extends BaseScraping {
    // 馬基本データURL
    def basic_horse_list_page_url = "http://www.jrdb.com/member/datazip/Ukc/index.html"
    def data_dir = config.data_base_dir.basic_horse

    def run() {
        def basic_horse_page = 馬基本データページへ遷移する()
        馬基本データ取得(basic_horse_page)
        return true
    }

    private def 馬基本データページへ遷移する() {
        def conn = Jsoup.connect(basic_horse_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 馬基本データ取得(conn){
        def basic_horse_zip_list = conn.select('table li a')
        basic_horse_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
