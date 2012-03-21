import org.modica.parser.StructuredFieldIntroducerParser
import org.modica.serializer.xml.XmlSerializingSFIntroducerHandler

XmlSerializingSFIntroducerHandler handler = new XmlSerializingSFIntroducerHandler(System.out)
new StructuredFieldIntroducerParser(new FileInputStream(new File(args[0])), handler).parse()
