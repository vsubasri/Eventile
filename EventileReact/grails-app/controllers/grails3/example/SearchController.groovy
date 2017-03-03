package grails3.example

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class SearchController {

    static responseFormats = ['json']

    def searchService
    def EventbriteService
    def springSecurityService

    def search(String q) {
        // Gets the current user name - can be used to control permissions
        def info = springSecurityService.currentUser.username
        log.debug("Searching by query = ${q}...")

        // perform a GET requestion to Eventbrite's API using EventbriteService class
        def response_eventbrite = EventbriteService.search(q)
        def getCategories = EventbriteService.get_eventbrite_categories()
        System.out.println(getCategories.toString())

        respond response_eventbrite
    }

    def handleIllegalArgument(IllegalArgumentException ex) {
        log.debug("Handling IllegalArgumentException ${ex}... Returning NO_CONTENT.")
        respond Collections.emptyList()
    }
}
