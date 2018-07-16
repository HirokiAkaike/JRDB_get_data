package jrdb.get.data.app

import jrdb.get.data.script.StandardTrioOddScraping



class StandardTrioOddScrapingRunner {
    static void main(String[] args) {
        def runner = new StandardTrioOddScraping()
        runner.run()
    }
}
