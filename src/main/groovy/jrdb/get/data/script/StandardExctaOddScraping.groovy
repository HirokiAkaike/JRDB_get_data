package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class StandardExctaOddScraping extends BaseScraping {
    // 馬単基準オッズデータデータURL
    def standard_excta_odd_list_page_url = "http://www.jrdb.com/member/datazip/Ou/index.html"
    def data_dir = config.data_base_dir.standard_excta_odd

    def run() {
        def standard_excta_odd_page = 馬単基準オッズデータデータページへ遷移する()
        馬単基準オッズデータデータ取得(standard_excta_odd_page)
        return true
    }

    private def 馬単基準オッズデータデータページへ遷移する() {
        def conn = Jsoup.connect(standard_excta_odd_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 馬単基準オッズデータデータ取得(conn){
        def standard_excta_odd_zip_list = conn.select('table li a')
        standard_excta_odd_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
