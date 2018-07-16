package jrdb.get.data.app

import jrdb.get.data.script.PaciScraping

class PaciScrapingRunner {
    static void main(String[] args) {
        def runner = new PaciScraping()
        runner.run()
    }
}
