package jrdb.get.data.base

import groovy.sql.Sql

class BaseDao {
    //汎用コンフィグ
    def config = new ConfigSlurper().parse(new File("src/main/resources/config_data_source.groovy").toURI().toURL())
    def dbServer = config.server_name
    def dbName = config.db_name
    def dbPort = config.db_port
    def url = "jdbc:postgresql://${dbServer}:${dbPort}/${dbName}"
    def user = config.user_id
    def password = config.user_pw
    def driver = 'org.postgresql.Driver'
    def sql = Sql.newInstance(url, user, password, driver)
}
