_A reproducer for [HTTPCORE-676](https://issues.apache.org/jira/projects/HTTPCORE/issues/HTTPCORE-676)_

# Prerequisites

- Docker needs to be installed as the HTTP-Server in use is started as Docker image
- `java_home` needs to be set to a >=java11 installation

# Overview

The docker image data can be found in `app/docker/`.
- The image exposes a HTTPs endpoint at port 8443.
- There are two resources:
  - `https://localhost:8443/test1.html`
    - Accessible thru a regular TLS-connection
  - `https://localhost:8443/withclientcert/test2.html`
    - Accessible thru a TLS-connection that involves renegotiation

Logging is configured to log to console in `app/src/test/java/h.t.r.Test#setupLog4J`.

`app/src/test/java/h.t.r.Test#testTLSRenegotiateHC5`:
- A test that accesses a resource causing TLS-renegotiation
  - Fails as of HC 5.0.3
  
`app/src/test/java/h.t.r.Test#testTLSRegularHC5`:
- A test that accesses a resource with a "regular" TLS connection
  - Succeeds as of HC 5.0.3

`app/src/test/java/h.t.r.Test#testTLSRenegotiateJavaHttpClient`:
- A test that accesses a resource causing TLS-renegotiation
  - Succeeds for java.net.http.HttpClient

# Run

- To execute all tests run `./gradlew test`
- To execute only the reproducer run `./gradlew test --tests *testTLSRenegotiateHC5`
