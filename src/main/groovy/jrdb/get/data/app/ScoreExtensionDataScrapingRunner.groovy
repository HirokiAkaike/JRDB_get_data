package jrdb.get.data.app

import jrdb.get.data.script.ScoreExtensionDataScraping



class ScoreExtensionDataScrapingRunner {
    static void main(String[] args) {
        def runner = new ScoreExtensionDataScraping()
        runner.run()
    }
}
