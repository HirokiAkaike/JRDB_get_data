package jrdb.get.data.base

import jrdb.get.data.common.JrdbConst

class groovy {

    def config = new ConfigSlurper().parse(new File("src/main/resources/config.groovy").toURI().toURL())
    def url_prefix = "http://"
    def baseUrl = config.jrdb_base_url
    def cont = new JrdbConst()

    'BaseScraping.groovy'() {
        println baseUrl
    }
}

