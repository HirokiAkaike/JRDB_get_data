package jrdb.get.data.app

import jrdb.get.data.script.RaceHorseScraping


class RaceHorseScrapingRunner {
    static void main(String[] args) {
        def runner = new RaceHorseScraping()
        runner.run()
    }
}
