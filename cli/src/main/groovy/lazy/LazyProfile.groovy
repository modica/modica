package lazy
 
import org.modica.afp.modca.StructuredFieldFactoryImpl
import org.modica.parser.StructuredFieldCreator
import org.modica.parser.StructuredFieldHandlers
import org.modica.parser.StructuredFieldIntroducerParser
import org.modica.parser.lazy.LazyAfpCreatingHandler


File afpFile = new File(args[0])

def withFileImputStream(file, closure) {
    def input = new FileInputStream(file)
    try {
        closure input
    } finally {
        input.close()
    }
}

def duration(closure) {
    final long t = System.currentTimeMillis()
    closure()
    return System.currentTimeMillis() - t
}

def total = (1..20).inject([:].withDefault{k->0}){ total, iteration ->
    withFileImputStream(afpFile) { input ->
        LazyAfpCreatingHandler lazyAfpCreator = new LazyAfpCreatingHandler(StructuredFieldHandlers.ignore(), input)
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input, lazyAfpCreator)
        final long t = System.currentTimeMillis()
        total['lazy'] += duration {
            preParser.parse()
            lazyAfpCreator.getLazyAfp().await()
        }
    }
    withFileImputStream(afpFile) { input ->
        StructuredFieldCreator creator = new StructuredFieldCreator(new StructuredFieldFactoryImpl(input.getChannel()),
                StructuredFieldHandlers.ignore())
        StructuredFieldIntroducerParser parser = new StructuredFieldIntroducerParser(input, creator)
        total['eager'] += duration {
            parser.parse()
        }
    }
    return total
}

println total
