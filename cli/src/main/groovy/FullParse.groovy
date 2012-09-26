import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer
import org.modica.parser.AfpParser
import org.modica.parser.StructuredFieldIntroducerHandler

class SFDataHandler implements StructuredFieldIntroducerHandler {

    def ExecutorService executor  = Executors.newFixedThreadPool(4)

    private List<FutureTask> dataList = []

    private List fields


    public void startAfp() {
    }

    public void endAfp() {
        fields = dataList.collect { it.get() }
        executor.shutdown()
    }

    public void handle(StructuredFieldIntroducer sf) {
        handleSf(sf)
    }

    public void handleBegin(StructuredFieldIntroducer sf) {
        handleSf(sf)
    }

    public void handleEnd(StructuredFieldIntroducer sf) {
        handleSf(sf)
    }

    private void handleSf(StructuredFieldIntroducer sf) {
        dataList << executor.submit { [sf.type, sf.length]}
    }

    def getFields() {
        fields
    }
}

// Usage

def sfDataHandler = new SFDataHandler()

new AfpParser(new File(args[0]), sfDataHandler).parse()

sfDataHandler.fields.each { println it }

