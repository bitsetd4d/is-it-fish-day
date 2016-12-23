package exception

class ConfigurationNotSetupException extends Exception {

    ConfigurationNotSetupException() {
    }

    ConfigurationNotSetupException(String var1) {
        super(var1)
    }

    ConfigurationNotSetupException(String var1, Throwable var2) {
        super(var1, var2)
    }

    ConfigurationNotSetupException(Throwable var1) {
        super(var1)
    }

    ConfigurationNotSetupException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
