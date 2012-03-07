import org.modica.serializer.xml.XmlSFIntroducerSerializer

new XmlSFIntroducerSerializer(new File(args[0])).writeTo(new File(args[1]))
