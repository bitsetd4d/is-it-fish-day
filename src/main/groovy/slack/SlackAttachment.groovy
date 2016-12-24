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
            fields fields*.intoJson(json)
        }
    }
}
