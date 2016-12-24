package slack

import config.SecureConfig
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.POST

class SlackSender {

    def send(SlackMessage message) {
        def url = SecureConfig.instance.slackUrl
        def http = new HTTPBuilder(url)
        http.request(POST, ContentType.JSON) {
            body = message.asJson()
            response.success = { resp -> println "Sent to slack!" }
            response.failure = { resp -> println "Send to slack failed with status ${resp.status}" }
        }
    }
}
