package jrdb.get.data.app

import jrdb.get.data.script.ScoreDataScraping


class ScoreDataScrapingRunner {
    static void main(String[] args) {
        def runner = new ScoreDataScraping()
        runner.run()
    }
}
