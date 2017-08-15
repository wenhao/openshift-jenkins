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

// Credential Permissions
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.CREATE, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.DELETE, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.MANAGE_DOMAINS, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.UPDATE, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.VIEW, "admin")
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.VIEW, "anonymous")

// Admin Slave Permissions
strategy.add(hudson.model.Computer.BUILD, "admin")
strategy.add(hudson.model.Computer.CONFIGURE, "admin")
strategy.add(hudson.model.Computer.CONNECT, "admin")
strategy.add(hudson.model.Computer.CREATE, "admin")
strategy.add(hudson.model.Computer.DELETE, "admin")
strategy.add(hudson.model.Computer.DISCONNECT, "admin")
// Anonymous Slave Permissions
strategy.add(hudson.model.Computer.CONNECT, "anonymous")
strategy.add(hudson.model.Computer.CREATE, "anonymous")

// Overall Permissions
strategy.add(hudson.model.Hudson.ADMINISTER, "admin")
strategy.add(hudson.PluginManager.CONFIGURE_UPDATECENTER,'anonymous')
strategy.add(hudson.model.Hudson.READ, "admin")
strategy.add(hudson.model.Hudson.READ, "anonymous")
strategy.add(hudson.model.Hudson.RUN_SCRIPTS,'anonymous')
strategy.add(hudson.PluginManager.UPLOAD_PLUGINS,'anonymous')

// Admin Job Permissions
strategy.add(hudson.model.Item.BUILD, "admin")
strategy.add(hudson.model.Item.CANCEL, "admin")
strategy.add(hudson.model.Item.CONFIGURE, "admin")
strategy.add(hudson.model.Item.CREATE,'admin')
strategy.add(hudson.model.Item.DELETE, "admin")
strategy.add(hudson.model.Item.DISCOVER, "admin")
strategy.add(hudson.model.Item.READ,'admin')
strategy.add(hudson.model.Item.WORKSPACE,'admin')
// anonymous Job Permissions
strategy.add(hudson.model.Item.READ, "anonymous")
strategy.add(hudson.model.Item.WORKSPACE, "anonymous")

// Admin Run Permissions
strategy.add(hudson.model.Run.DELETE, "admin")
strategy.add(hudson.model.Run.UPDATE, "admin")
// Anonymous Run Permissions
strategy.add(hudson.model.Run.DELETE, "anonymous")
strategy.add(hudson.model.Run.UPDATE, "anonymous")

// Admin View Permissions
strategy.add(hudson.model.View.CONFIGURE, "admin")
strategy.add(hudson.model.View.CREATE, "admin")
strategy.add(hudson.model.View.DELETE, "admin")
strategy.add(hudson.model.View.READ, "admin")

// Anonymous View Permissions
strategy.add(hudson.model.View.READ, "anonymous")

instance.setAuthorizationStrategy(strategy)

// set executors
instance.setNumExecutors(5)

instance.save()
