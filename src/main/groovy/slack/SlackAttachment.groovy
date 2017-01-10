package slack

class SlackAttachment {
    String title = ''
    String color = '#00D000'
    List<SlackField> fields = []

    def intoJson(json) {
        json {
            fallback title
            pretext title
            color color
            'mrkdwn_in' markdown()
            fields fields*.intoJson(json)
        }
    }

    String[] markdown() {
        ['pretext', 'text', 'fields']
    }
}
