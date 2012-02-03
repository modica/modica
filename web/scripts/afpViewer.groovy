import groovy.text.SimpleTemplateEngine

port = args.length ? args[0] : 8080
context = 'afp-viewer'

waitForServer {
    def confFile = new File(System.properties['user.home'], '.afp-viewer/viewer.config')
    def conf = confFile.file ? new ConfigSlurper().parse(confFile.toURL()) : [:]
    if (conf.browse) {
        new SimpleTemplateEngine().createTemplate(conf.browse)
            .make([host:'localhost', port: port, context: context]).toString().execute()
    } else {
        println "Browse @ http://localhost:$port/$context/"
    }
}

def proc = "java -jar build/libs/afp-viewer-0.1.war $port".execute()
proc.consumeProcessErrorStream(System.err)
proc.consumeProcessOutputStream(System.out)

def waitForServer(onReady) {
    Thread.start {
        while (true) {
            def proc = "curl -I http://localhost:$port/$context/".execute()
                proc.waitFor()
                if(proc.exitValue() == 0) {
                    break
                }
            Thread.currentThread().sleep(10)
        }
        onReady()
    }
}
