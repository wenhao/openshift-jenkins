#!groovy
import hudson.security.*
import jenkins.model.*
import jenkins.install.*
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

// skip setup wizard
def wizard = instance.getSetupWizard()
wizard.completeSetup()
PluginServletFilter.removeFilter(wizard.FORCE_SETUP_WIZARD_FILTER)

instance.save()
