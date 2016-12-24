package slack

import config.SecureConfig
import groovy.json.JsonBuilder

class SlackMessage {
    String title
    List<SlackAttachment> attachments = []

    def asJson() {
        def json = new JsonBuilder()
        json {
            channel SecureConfig.instance.slackChannel
            username 'Food Bot'
            icon_emoji ':knife_fork_plate:'
            text title
            attachments attachments*.intoJson(json)
        }
        json.content
    }

}
