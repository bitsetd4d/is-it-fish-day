package config

import exception.ConfigurationNotSetupException
import org.junit.Test

import java.nio.file.Paths

class ConfigLoaderTest {

    @Test
    void ensureSampleFileCreatedIfConfigMissing() {
        def testConfig = Paths.get("build", ".isitfishday", "test1.groovy")
        testConfig.toFile().delete()
        try {
            def loader = new ConfigLoader(testConfig)
            fail()
        } catch (ConfigurationNotSetupException e) {
            assert e.message == "Please customise configuration file at $testConfig which has just been created"
            println "Exception thrown as expected"
        }
        assert testConfig.toFile().exists()
    }


    @Test
    void ensureLoaderFailsIfSampleFileNotCustomised() {
        // given
        def testConfig = Paths.get("build", ".isitfishday", "test2.groovy")
        testConfig.toFile().delete()
        try {
            def loader = new ConfigLoader(testConfig)
            fail()
        } catch (ConfigurationNotSetupException e) {
            assert e.message == "Please customise configuration file at $testConfig which has just been created"
        }
        assert testConfig.toFile().exists()

        // when
        try {
            def loader = new ConfigLoader(testConfig)
            fail()

        // then
        } catch (ConfigurationNotSetupException e) {
            assert e.message == "Please customise configuration file at $testConfig"
        }

    }

}
