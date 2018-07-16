package jrdb.get.data.app

import jrdb.get.data.script.BasicHorseScraping

class BasicHorseScrapingRunner {
    static void main(String[] args) {
        def runner = new BasicHorseScraping()
        runner.run()
    }
}
