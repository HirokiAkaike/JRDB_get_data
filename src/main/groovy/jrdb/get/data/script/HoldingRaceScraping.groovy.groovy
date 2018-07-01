package jrdb.get.data.script

import org.jsoup.Jsoup

class groovy extends jrdb.get.data.base.groovy {
    def basic_auth_url = config.jrdb_id + cont.getCORON_MARK() + config.jrdb_pw
    def landing_page = url_prefix + Base64.getEncoder().encodeToString(basic_auth_url.getBytes()) +
            cont.getAT_MARK() + baseUrl + cont.getURL_SEPARETOR() + config.jrdb_login_url

    'HoldingRaceScraping.groovy'() {
        print landing_page

        //目的のページを開く際に取得したクッキーを設定する
        def conn = Jsoup.connect(landing_page).userAgent('Chrome/59.0.3071.115')
        def mainDoc = conn.get()
        print mainDoc

        //取得したHTMLを使って要素を取得したりできる
        def newsTable = mainDoc.select('#news-table')

    }


}
