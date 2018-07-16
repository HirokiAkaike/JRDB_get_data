package jrdb.get.data.app

import jrdb.get.data.script.JrdbInformationScraping

class JrdbInformationScrapingRunner {
    static void main(String[] args) {
        def runner = new JrdbInformationScraping()
        runner.run()
    }
}
