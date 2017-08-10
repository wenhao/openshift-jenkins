#!groovy
import jenkins.*
import hudson.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*;
import hudson.model.*
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// set slave port
def env = System.getenv()
int port = env['JENKINS_SLAVE_AGENT_PORT'].toInteger()
instance.setSlaveAgentPort(port)

// set default user
def hudsonRealm = new HudsonPrivateSecurityRealm(false)

def adminUsername = System.getenv('JENKINS_ADMIN_USERNAME') ?: 'admin'
def adminPassword = System.getenv('JENKINS_ADMIN_PASSWORD') ?: 'admin'
hudsonRealm.createAccount(adminUsername, adminPassword)
instance.setSecurityRealm(hudsonRealm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(hudson.model.Hudson.Administer, "admin")

strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.Create, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.Delete, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.Update, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.View, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.View, "anonymous")

strategy.add(hudson.model.Computer.Build, "admin")
strategy.add(hudson.model.Computer.Configure, "admin")
strategy.add(hudson.model.Computer.Connect, "admin")
strategy.add(hudson.model.Computer.Connect, "anonymous")
strategy.add(hudson.model.Computer.Create, "admin")
strategy.add(hudson.model.Computer.Delete, "admin")
strategy.add(hudson.model.Computer.Disconnect, "admin")
strategy.add(hudson.model.Computer.Provision, "admin")
strategy.add(hudson.model.Hudson.Administer, "admin")
strategy.add(hudson.model.Hudson.Read, "admin")
strategy.add(hudson.model.Hudson.Read, "anonymous")
strategy.add(hudson.model.Item.Build, "admin")
strategy.add(hudson.model.Item.Cancel, "admin")
strategy.add(hudson.model.Item.Configure, "admin")
strategy.add(hudson.model.Item.Create, "admin")
strategy.add(hudson.model.Item.Delete, "admin")
strategy.add(hudson.model.Item.Discover, "admin")
strategy.add(hudson.model.Item.Move, "admin")
strategy.add(hudson.model.Item.Read, "admin")
strategy.add(hudson.model.Item.Read, "anonymous")
strategy.add(hudson.model.Item.Workspace, "admin")
strategy.add(hudson.model.Run.Delete, "admin")
strategy.add(hudson.model.Run.Replay, "admin")
strategy.add(hudson.model.Run.Update, "admin")
strategy.add(hudson.model.View.Configure, "admin")
strategy.add(hudson.model.View.Create, "admin")
strategy.add(hudson.model.View.Delete, "admin")
strategy.add(hudson.model.View.Read, "admin")
strategy.add(hudson.model.View.Read, "anonymous")
strategy.add(hudson.scm.SCM.Tag, "admin")
strategy.add(jenkins.metrics.api.Metrics.HealthCheck, "admin")
strategy.add(jenkins.metrics.api.Metrics.ThreadDump, "admin")
strategy.add(jenkins.metrics.api.Metrics.View, "admin")
strategy.add(jenkins.metrics.api.Metrics.View, "anonymous")
instance.setAuthorizationStrategy(strategy)

// set executors
instance.setNumExecutors(5)

instance.save()
