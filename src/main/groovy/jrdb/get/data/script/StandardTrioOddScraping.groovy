package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class StandardTrioOddScraping extends BaseScraping {
    // ３連複基準オッズデータURL
    def standard_trio_odd_list_page_url = "http://www.jrdb.com/member/datazip/Ot/index.html"
    def data_dir = config.data_base_dir.standard_trio_odd

    def run() {
        def standard_trio_odd_page = "３連複基準オッズデータページへ遷移する"()
        "３連複基準オッズデータ取得"(standard_trio_odd_page)
        return true
    }

    private def "３連複基準オッズデータページへ遷移する"() {
        def conn = Jsoup.connect(standard_trio_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def "３連複基準オッズデータ取得"(conn){
        def standard_trio_odd_zip_list = conn.select('table li a')
        standard_trio_odd_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
