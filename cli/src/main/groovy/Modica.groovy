
import org.apache.commons.cli.HelpFormatter
import org.modica.parser.AfpParser
import org.modica.parser.PrintingSFHandler
import org.modica.parser.PrintingSFIntroducerHandler
import org.modica.parser.StructuredFieldIntroducerParser
import org.modica.serializer.xml.XmlSerializingSFIntroducerHandler
import org.modica.serializer.xml.XmlSerializingStructuredFieldHandler


def cli = new CliBuilder(usage: 'modica [options] file').with {
    header = 'An AFP parsing library\nOptions:'
    h longOpt: 'help', 'usage information'
    f longOpt: 'full',  'Full parse'
    x longOpt: 'xml-out',  'Print xml'
    delegate
}

def options = cli.parse(args)

if (options.h || ! options.arguments()) {
    cli.usage()
    System.exit(0)
}

use(FileCatagory) {

    File afpFile = new File(options.arguments()[0])

    if (!afpFile.file) {
        println "$afpFile is not a file"
        System.exit(0)
    }

    afpFile.withFileInputStream {inStream->
        def sfiHandler = (options.x) ? new XmlSerializingSFIntroducerHandler(System.out) :
                new PrintingSFIntroducerHandler(System.out)
        if (options.f) {
            def sfHandler = (options.x) ? new XmlSerializingStructuredFieldHandler(System.out) :
                    new PrintingSFHandler(System.out)
            AfpParser.forInput(inStream)
                    .withHandler(sfiHandler)
                    .withHandler(sfHandler)
                    .parse();
        } else {
            new StructuredFieldIntroducerParser(inStream, sfiHandler).parse();
        }
    }
}

@Category(File)
class FileCatagory {
    void withFileInputStream(closure) {
        def input = new FileInputStream(this)
        try {
            closure input
        } finally {
            input.close()
        }
    }
}
