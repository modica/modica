import groovy.text.SimpleTemplateEngine

port = args.length ? args[0] : 8080
context = 'modica'

waitForServer {
    def confFile = new File(System.properties['user.home'], '.modica/viewer.config')
    def conf = confFile.file ? new ConfigSlurper().parse(confFile.toURL()) : [:]
    if (conf.browse) {
        new SimpleTemplateEngine().createTemplate(conf.browse)
            .make([host:'localhost', port: port, context: context]).toString().execute()
    } else {
        println "Browse @ http://localhost:$port/$context/"
    }
}

def viewerOpts = System.getenv('VIEWER_OPTS') ?: ''

println '''

    M O D i C A
    -----------

'''

def proc = "java $viewerOpts -jar build/libs/modica-viewer-0.1.war $port".execute()
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
