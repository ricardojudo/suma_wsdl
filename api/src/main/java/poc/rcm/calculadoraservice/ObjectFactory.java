
package poc.rcm.calculadoraservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the poc.rcm.calculadoraservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Argumentos_QNAME = new QName("http://rcm.poc/CalculadoraService/", "Argumentos");
    private final static QName _Resultado_QNAME = new QName("http://rcm.poc/CalculadoraService/", "Resultado");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: poc.rcm.calculadoraservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResultadoType }
     * 
     */
    public ResultadoType createResultadoType() {
        return new ResultadoType();
    }

    /**
     * Create an instance of {@link ArgumentosType }
     * 
     */
    public ArgumentosType createArgumentosType() {
        return new ArgumentosType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArgumentosType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rcm.poc/CalculadoraService/", name = "Argumentos")
    public JAXBElement<ArgumentosType> createArgumentos(ArgumentosType value) {
        return new JAXBElement<ArgumentosType>(_Argumentos_QNAME, ArgumentosType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rcm.poc/CalculadoraService/", name = "Resultado")
    public JAXBElement<ResultadoType> createResultado(ResultadoType value) {
        return new JAXBElement<ResultadoType>(_Resultado_QNAME, ResultadoType.class, null, value);
    }

}
