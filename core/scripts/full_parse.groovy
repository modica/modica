import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

import org.afpparser.afp.modca.structuredfields.SfIntroducer
import org.afpparser.parser.AFPDocumentParser
import org.afpparser.parser.SFIntroducerHandler

class SFDataHandler implements SFIntroducerHandler {

    def Executor executor  = Executors.newFixedThreadPool(4)

        private List<FutureTask> dataList = []

        private List fields


        public void startAfp() {
        }

        public void endAfp() {
            fields = dataList.collect {
                it.get()
            }
            executor.shutdown()
        }

        public void handle(SfIntroducer sf) {
            handleSf(sf)
        }

        public void handleBegin(SfIntroducer sf) {
            handleSf(sf)
        }

        public void handleEnd(SfIntroducer sf) {
            handleSf(sf)
        }

        private void handleSf(SfIntroducer sf) {
            def future = new FutureTask({
                    [sf.type, sf.length]
                    })
            executor.execute {
                future.run()
            }
            dataList << future
        }

    def getFields() {
        fields
    }
}

// Usage

def sfDataHandler = new SFDataHandler()

new AFPDocumentParser(new File(args[0]), sfDataHandler).parse()

sfDataHandler.fields.each {
        println it
}

