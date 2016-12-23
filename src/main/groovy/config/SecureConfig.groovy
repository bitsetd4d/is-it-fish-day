package config

import java.nio.file.Paths

/**
 * Configuration for keys/passwords that shouldn't be kept with the
 * source
 */
@Singleton
class SecureConfig {

    @Delegate ConfigLoader configLoader = new ConfigLoader(Paths.get(System.properties['user.home'], ".isitfishday", "secure.groovy"))

}
