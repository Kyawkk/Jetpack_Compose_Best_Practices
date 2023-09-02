package com.example.cmdownloadlinkgenerator.data.scraper

import org.jsoup.Jsoup

object Scraper {
    fun scrape(){
        val URL = "https://channelmyanmar.org/tvshows/seal-team-2017/"

        val document = Jsoup.connect(URL).get()
        val links = document.select("p>strong")
        links.forEach { println(it.text()) }
    }
}

fun main() {
    Scraper.scrape()
}