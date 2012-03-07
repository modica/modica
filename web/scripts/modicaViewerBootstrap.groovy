import groovy.text.SimpleTemplateEngine

port = args.length ? Integer.valueOf(args[0]) : 8080

context = 'modica'

def viewerOpts = System.getenv('VIEWER_OPTS') ?: ''

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


println '''

    M O D i C A
    -----------

'''

new ModicaWebServer(port).start()
