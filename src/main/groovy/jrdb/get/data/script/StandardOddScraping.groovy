package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class StandardOddScraping extends BaseScraping {
    // 基準オッズデータURL
    def standard_odd_list_page_url = "http://www.jrdb.com/member/datazip/Oz/index.html"
    def data_dir = config.data_base_dir.standard_odd

    def run() {
        def standard_odd_page = 基準オッズデータページへ遷移する()
        基準オッズデータ取得(standard_odd_page)
        return true
    }

    private def 基準オッズデータページへ遷移する() {
        def conn = Jsoup.connect(standard_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 基準オッズデータ取得(conn){
        def standard_odd_zip_list = conn.select('table li a')
        standard_odd_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
