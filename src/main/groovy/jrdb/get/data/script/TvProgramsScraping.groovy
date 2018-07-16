package jrdb.get.data.script

import jrdb.get.data.base.BaseScraping
import org.jsoup.Jsoup

class TvProgramsScraping extends BaseScraping {
    // 番組データURL
    def tv_program_list_page_url = "http://www.jrdb.com/member/datazip/Bac/index.html"
    def data_dir = config.data_base_dir.tv_program

    def run() {
        def tv_program_page = 番組データページへ遷移する()
        番組データ取得(tv_program_page)
        return true
    }

    private def 番組データページへ遷移する() {
        def conn = Jsoup.connect(tv_program_list_page_url).header(headKey, headValue)
        def page = conn.get()
        return page

    }

    private def 番組データ取得(conn){
        def tv_program_zip_list = conn.select('table li a')
        tv_program_zip_list.each {
            def response = Jsoup.connect(it.attr("abs:href")).header(headKey, headValue)
                    .ignoreContentType(true).execute()
            def out = new OutputStreamWriter(new FileOutputStream(new java.io.File(data_dir + it.text())))
            out.write(response.body())
            out.close()
        }
    }
}
