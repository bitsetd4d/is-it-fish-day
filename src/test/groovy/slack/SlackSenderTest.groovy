package slack

import config.SecureConfig
import groovy.json.JsonBuilder
import org.junit.Test

class SlackSenderTest {

    def json = new JsonBuilder()

    @Test
    void createSomeJson() throws Exception {
        json {
            channel '#paulm-test-channel'
            username 'Foot Bot'
            icon_emoji ':knife_fork_plate:'
            attachments attachments()
        }
        println json.toPrettyString()
    }

    def attachments() {
        [ json { a 'av' }, json { b 'bv'} ]
    }

    @Test
    void checkJsonFromSampleMessage() {
        def expectedJson = """{"channel":"${SecureConfig.instance.getSlackChannel()}","username":"Food Bot","icon_emoji":":knife_fork_plate:","text":"Here are the menus for today","attachments":[{"fallback":"Menus today","pretext":"Menus today","color":"#00D000","fields":[{"title":"The Dining Room","value":"Line1\\nLine2","short":false},{"title":"Delphine","value":"Line3\\nLine4","short":false}]},{"fallback":"The Weather","pretext":"The Weather","color":"#00D000","fields":[{"title":"Partly Cloudy 10.5C. Nearest storm 35 miles.","value":"14:12 : Partly Cloudy 11C.\\n15:12 : Partly Cloudy 11C","short":false}]}]}"""
        assert new JsonBuilder(getSampleMessage().asJson()).toString() == expectedJson.toString()
    }

    static void main(String[] args) {
        SlackSender sender = new SlackSender()
        sender.send(sampleMessage)
    }

    static getSampleMessage() {
        def diningRoom = new SlackField(title: 'The Dining Room')
        diningRoom.lines << 'Line1'
        diningRoom.lines << 'Line2'

        def delphine = new SlackField(title: 'Delphine')
        delphine.lines << 'Line3'
        delphine.lines << 'Line4'

        def menus = new SlackAttachment(title: 'Menus today', fields: [diningRoom, delphine])

        def weather = new SlackField(title: "Partly Cloudy 10.5C. Nearest storm 35 miles.")
        weather.lines << '14:12 : Partly Cloudy 11C.'
        weather.lines << '15:12 : Partly Cloudy 11C'

        def weatherAttachment = new SlackAttachment(title: 'The Weather', fields: [weather])

        new SlackMessage(title: 'Here are the menus for today', attachments: [menus, weatherAttachment])
    }
}
