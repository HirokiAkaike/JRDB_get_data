package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class ScoreExtensionDataScraping extends BaseScraping {
    // 成績拡張データURL
    def score_extension_data_list_page_url = "http://www.jrdb.com/member/datazip/Skb/index.html"
    def data_dir = config.data_base_dir.score_extension_data

    def run() { 
        def score_extension_data_page = 成績拡張データページへ遷移する()
        成績拡張データ取得(score_extension_data_page)
        return true
    }

    private def 成績拡張データページへ遷移する() {
        def conn = Jsoup.connect(score_extension_data_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 成績拡張データ取得(conn){
        def score_extension_data_zip_list = conn.select('table li a')
        score_extension_data_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
