package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class PaciScraping extends BaseScraping {
    // JRDB前走データURL
    def paci_list_page_url = "http://www.jrdb.com/member/datazip/Paci/index.html"
    def data_dir = config.data_base_dir.paci

    def run() {
        def paci_page = JRDB前走データページへ遷移する()
        JRDB前走データ取得(paci_page)
        return true
    }

    private def JRDB前走データページへ遷移する() {
        def conn = Jsoup.connect(paci_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def JRDB前走データ取得(conn){
        def paci_zip_list = conn.select('table li a')
        paci_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
