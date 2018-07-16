package jrdb.get.data.app

import jrdb.get.data.script.StandardOddScraping
import jrdb.get.data.script.StandardQuinellaPlaceOddsScraping


class StandardQuinellaPlaceOddScrapingRunner {
    static void main(String[] args) {
        def runner = new StandardQuinellaPlaceOddsScraping()
        runner.run()
    }
}
