package jrdb.get.data.app

import jrdb.get.data.script.RefundInformationScraping


class RefundInformationScrapingRunner {
    static void main(String[] args) {
        def runner = new RefundInformationScraping()
        runner.run()
    }
}