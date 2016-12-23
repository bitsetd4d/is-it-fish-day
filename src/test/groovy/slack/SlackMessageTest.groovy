package slack

import org.junit.Test

class SlackMessageTest {

    @Test
    void createSimpleMessage() throws Exception {

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

        def root = new SlackMessage(title: 'Here are the menus for today', attachments: [menus, weatherAttachment])

        def allLines = root*.attachments*.fields*.lines.flatten()
        assert allLines == ['Line1', 'Line2', 'Line3', 'Line4', '14:12 : Partly Cloudy 11C.', '15:12 : Partly Cloudy 11C']
    }
}
