package jrdb.get.data.app

import jrdb.get.data.script.TvProgramScraping


class TvProgramScrapingRunner {
    static void main(String[] args) {
        def runner = new TvProgramScraping()
        runner.run()
    }
}
