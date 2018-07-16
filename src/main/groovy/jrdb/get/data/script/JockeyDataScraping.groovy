package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class JockeyDataScraping extends BaseScraping {
    // 騎手データURL
    def jockey_data_list_page_url = "http://www.jrdb.com/member/data/"
    def data_dir = config.data_base_dir.jockey_data

    def run() {
        def jockey_data_page = 騎手データページへ遷移する()
        騎手データ取得(jockey_data_page)
        return true
    }

    private def 騎手データページへ遷移する() {
        def conn = Jsoup.connect(jockey_data_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 騎手データ取得(conn){
        def jockey_data_zip_list = conn.select('a')
        jockey_data_zip_list.each {
            if (it.attr("href").toString() ==~ /.*KZA.*zip$/) {
                def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                        .ignoreContentType(true).execute()
                def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir +
                        (it.attr("href") =~ /KZA.*zip$/)[0])))
                out.write(response.body())
                out.close()
            }
        }
    }
}
