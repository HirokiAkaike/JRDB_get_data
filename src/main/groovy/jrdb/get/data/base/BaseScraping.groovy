package jrdb.get.data.base

import jrdb.get.data.common.JrdbConstant


class BaseScraping {
    //汎用コンフィグ
    def config = new ConfigSlurper().parse(new File("src/main/resources/config.groovy").toURI().toURL())
    //アカウント情報コンフィグ
    def config_account = new ConfigSlurper().parse(new File("src/main/resources/config_account.groovy").toURI().toURL())
    // Basic認証ID
    def basic_id = config_account.jrdb_id
    // Basic認証PW
    def basic_pass = config_account.jrdb_pw
    // IDとパスワードを送信用に結合
    def basic_id_pass = "${basic_id}${JrdbConstant.CORON_MARK}${basic_pass}"
    // IDとパスワードをベーシック認証用にbase64でエンコード
    def basic_id_pass64 = basic_id_pass.bytes.encodeBase64().toString()
    // ヘッダへ設定する情報を用意
    def headKey = "Authorization"
    def headValue = "Basic " + basic_id_pass64

}

