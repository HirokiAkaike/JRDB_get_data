package jrdb.get.data.app

import jrdb.get.data.script.StandardExctaOddScraping


class StandardExctaOddScrapingRunner {
    static void main(String[] args) {
        def runner = new StandardExctaOddScraping()
        runner.run()
    }
}
