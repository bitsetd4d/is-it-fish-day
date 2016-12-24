package config

import java.nio.file.Paths

/**
 * Configuration for keys/passwords that shouldn't be kept with the
 * source
 */
@Singleton
class SecureConfig {

    ConfigLoader config = new ConfigLoader(Paths.get('/', System.properties['user.home'], ".isitfishday", "secure.groovy"))

    def getDarkSpyApiKey() {
        config.darkSkyApiKey
    }

    def getSlackChannel() {
        config.slackchannel
    }

    def getSlackUrl() {
        config.slackurl
    }
}
