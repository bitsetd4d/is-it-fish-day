package slack

class SlackField {
    String title
    List<String> lines = []

    def intoJson(json) {
        json {
            title title
            value lines.join('\n')
            'short' false
        }
    }
}
