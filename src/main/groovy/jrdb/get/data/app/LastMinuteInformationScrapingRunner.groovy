package jrdb.get.data.app

import jrdb.get.data.script.LastMinuteInformationScraping


class LastMinuteInformationScrapingRunner {
    static void main(String[] args) {
        def runner = new LastMinuteInformationScraping()
        runner.run()
    }
}