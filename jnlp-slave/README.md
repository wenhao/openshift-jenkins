# Openshift jnlp Slave

## Running

Required environment variables:

* `JENKINS_URL`: url for the Jenkins server
* `SLAVE_NAME`: slave name, if not set as an argument
* `HOME`: slave home

## Configuration specifics

### Enabled JNLP protocols

By default, the [JNLP3-connect](https://github.com/jenkinsci/remoting/blob/master/docs/protocols.md#jnlp3-connect) is disabled due to the known stability and scalability issues.
You can enable this protocol on your own risk using the
`JNLP_PROTOCOL_OPTS=-Dorg.jenkinsci.remoting.engine.JnlpProtocol3.disabled=false` property (the protocol should be enabled on the master side as well).

In Jenkins versions starting from `2.27` there is a [JNLP4-connect](https://github.com/jenkinsci/remoting/blob/master/docs/protocols.md#jnlp4-connect) protocol.
If you use Jenkins `2.32.x LTS`, it is recommended to enable the protocol on your instance.
