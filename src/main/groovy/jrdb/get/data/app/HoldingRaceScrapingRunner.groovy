package jrdb.get.data.app

import jrdb.get.data.script.HoldingRaceScraping


class HoldingRaceScrapingRunner {
    static void main(String[] args) {
        def holding_race_scraping = new HoldingRaceScraping()
        holding_race_scraping.run()
    }
}
