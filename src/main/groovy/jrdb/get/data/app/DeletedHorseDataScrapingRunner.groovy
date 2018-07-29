package jrdb.get.data.app

import jrdb.get.data.script.DeletedHorseDataScraping


class DeletedHorseDataScrapingRunner {
    static void main(String[] args) {
        println getClass().getResource("config.groovy")
        def runner = new DeletedHorseDataScraping()
        runner.run()
    }
}
