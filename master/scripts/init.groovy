#!groovy
import hudson.security.*
import jenkins.model.*
import static jenkins.install.InstallState.INITIAL_SETUP_COMPLETED
import hudson.util.PluginServletFilter

def instance = Jenkins.getInstance()

// set slave port
def env = System.getenv()
int port = env['JENKINS_SLAVE_AGENT_PORT'].toInteger()
instance.setSlaveAgentPort(port)

// set default user
def hudsonRealm = new HudsonPrivateSecurityRealm(false)

hudsonRealm.createAccount('admin', 'admin')
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

// set executors
instance.setNumExecutors(5)

instance.save()
