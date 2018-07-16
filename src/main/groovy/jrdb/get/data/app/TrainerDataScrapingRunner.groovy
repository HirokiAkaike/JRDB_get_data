package jrdb.get.data.app

import jrdb.get.data.script.TrainerDataScraping


class TrainerDataScrapingRunner {
    static void main(String[] args) {
        def runner = new TrainerDataScraping()
        runner.run()
    }
}
