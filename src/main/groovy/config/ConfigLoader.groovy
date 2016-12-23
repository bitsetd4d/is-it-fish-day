package config

import exception.ConfigurationNotSetupException

import java.nio.file.Path

/**
 * Load configuration.
 * Create a sample config if none present
 */
class ConfigLoader {

    Path configPath
    @Delegate ConfigObject config

    ConfigLoader(configPath) {
        this.configPath = configPath
        loadConfig()
    }

    private loadConfig() {
        failIfConfigFileDoesntExist()
        def slurper = new ConfigSlurper()
        def url = configPath.toUri().toURL()
        config = slurper.parse(url)
        failIfConfigFileNotCustomised()
    }

    private failIfConfigFileDoesntExist() {
        if (!configPath.toFile().exists()) {
            createSampleConfiguration()
            throwConfigurationException("Please customise configuration file at $configPath which has just been created")
        }
    }

    private failIfConfigFileNotCustomised() {
        if (config.isSet('deleteme')) {
            throwConfigurationException("Please customise configuration file at $configPath")
        }
    }

    private void throwConfigurationException(message) {
        println message
        throw new ConfigurationNotSetupException(message)
    }

    private createSampleConfiguration() {
        configPath.parent.toFile().mkdirs()
        File sampleConfig = configPath.toFile()
        sampleConfig.withWriter { out ->
            out.println 'darkskyapikey = "replace_this_with_api_key"'
            out.println 'slackurl = "replace_this_with_slack_url_for_incomming_message_hook"'
            out.println ''
            out.println 'deleteme = true    // Delete this line when you have finished'
        }
    }
}
