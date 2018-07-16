package jrdb.get.data.app

import jrdb.get.data.script.StandardOddScraping


class StandardOddScrapingRunner {
    static void main(String[] args) {
        def runner = new StandardOddScraping()
        runner.run()
    }
}
