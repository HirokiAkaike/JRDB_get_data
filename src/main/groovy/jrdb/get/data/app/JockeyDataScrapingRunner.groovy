package jrdb.get.data.app

import jrdb.get.data.script.JockeyDataScraping


class JockeyDataScrapingRunner {
    static void main(String[] args) {
        def runner = new JockeyDataScraping()
        runner.run()
    }
}
