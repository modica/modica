import org.afpparser.serializer.xml.XmlSfSerializer

new XmlSfSerializer(new File(args[0])).writeTo(new File(args[1]))
