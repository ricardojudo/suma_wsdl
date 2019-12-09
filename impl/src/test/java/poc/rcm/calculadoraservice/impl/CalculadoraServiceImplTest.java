package poc.rcm.calculadoraservice.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.Before;
import org.junit.Test;

import poc.rcm.calculadoraservice.ArgumentosType;
import poc.rcm.calculadoraservice.CalculadoraService;
import poc.rcm.calculadoraservice.CalculadoraService_Service;
import poc.rcm.calculadoraservice.ResultadoType;

public class CalculadoraServiceImplTest {

	private URL wsdlLocation;

	private static final String SECURE_ENDPOINT = "https://localhost:8443/suma_wsdl/CalculadoraService.jws?wsdl";

	@Before
	public void setUp() throws Exception {
		// wsdlLocation = new URL(
		// "http://localhost:8080/webapp/CalculadoraService.jws?wsdl");
		wsdlLocation = new URL(
				"http://localhost:8080/suma_wsdl/CalculadoraService.jws?wsdl");

	}

	private static TrustManager[] getTrustManagers(KeyStore trustStore)
			throws NoSuchAlgorithmException, KeyStoreException {
		String alg = KeyManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory fac = TrustManagerFactory.getInstance(alg);
		fac.init(trustStore);
		return fac.getTrustManagers();
	}

	private static KeyManager[] getKeyManagers(KeyStore keyStore,
			String keyPassword) throws GeneralSecurityException, IOException {
		String alg = KeyManagerFactory.getDefaultAlgorithm();
		char[] keyPass = keyPassword != null ? keyPassword.toCharArray() : null;
		KeyManagerFactory fac = KeyManagerFactory.getInstance(alg);
		fac.init(keyStore, keyPass);
		return fac.getKeyManagers();
	}

	private static void setupTLS(CalculadoraService port)
			throws FileNotFoundException, IOException, GeneralSecurityException {
		String contextPath = "";
		try {
			contextPath = "C:\\Users\\ricardo\\Documents\\poc\\workspace\\suma_wsdl\\webapp\\src\\main\\resources\\security\\";
//			contextPath = "security";
		} catch (Exception e) {
			e.printStackTrace();
		}
		HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port)
				.getConduit();

		TLSClientParameters tlsCP = new TLSClientParameters();
		String keyPassword = "keystore";
		
		KeyStore trustStore = KeyStore.getInstance("JKS");
		String trustStoreLoc = contextPath + "truststore";
		trustStore.load(new FileInputStream(trustStoreLoc),
				keyPassword.toCharArray());
		TrustManager[] myTrustStoreKeyManagers = getTrustManagers(trustStore);
		tlsCP.setTrustManagers(myTrustStoreKeyManagers);

		
//		tlsCP.setDisableCNCheck(true);
		httpConduit.setTlsClientParameters(tlsCP);

	}

	@Test
	public void testSumar_ProxySSL() throws Exception{
		CalculadoraService service = new CalculadoraService_Service(
				wsdlLocation).getCalculadoraServiceSOAP();

		BindingProvider bindingProvider = (BindingProvider) service;
		bindingProvider.getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SECURE_ENDPOINT);

		setupTLS(service);
		
		ArgumentosType argumentosType = new ArgumentosType();
		Integer sumandos[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		argumentosType.getSumando().addAll(Arrays.asList(sumandos));
		ResultadoType resultado = service.sumar(argumentosType);
		assertNotNull(resultado);
		assertEquals(45, resultado.getValor());
	}

	@Test
	public void testSumar_Proxy() {
		CalculadoraService service = new CalculadoraService_Service(
				wsdlLocation).getCalculadoraServiceSOAP();
		ArgumentosType argumentosType = new ArgumentosType();
		Integer sumandos[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		argumentosType.getSumando().addAll(Arrays.asList(sumandos));
		ResultadoType resultado = service.sumar(argumentosType);
		assertNotNull(resultado);
		assertEquals(45, resultado.getValor());
	}

	@Test
	public void testSumar_Dispatch_Message() throws Exception {
		Service service = Service.create(wsdlLocation, new QName(
				"http://rcm.poc/CalculadoraService/", "CalculadoraService"));
		Dispatch<Source> dispatch = service.createDispatch(
				new QName("http://rcm.poc/CalculadoraService/",
						"CalculadoraServiceSOAP"), Source.class,
				Service.Mode.MESSAGE);
		Source msg = new StreamSource(
				new StringReader(
						"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cal=\"http://rcm.poc/CalculadoraService/\">"
								+ "<soapenv:Header/>"
								+ "<soapenv:Body>"
								+ "   <cal:Argumentos>"
								+ "      <Sumando>3</Sumando>"
								+ "      <Sumando>5</Sumando>"
								+ "   </cal:Argumentos>"
								+ "</soapenv:Body>"
								+ "</soapenv:Envelope>"));
		Source response = dispatch.invoke(msg);
		// XMLInputFactory inputfactory = XMLInputFactory.newInstance();
		// XMLStreamReader xml=inputfactory.createXMLStreamReader(response);
		//
		// while(xml.hasNext()){
		// xml.next();
		// if(xml.getEventType() == XMLStreamReader.START_ELEMENT){
		// System.out.println(xml.getLocalName());
		// }
		// }
	}

	@Test
	public void testSumar_Dispatch_Payload() throws Exception {
		Service service = Service.create(wsdlLocation, new QName(
				"http://rcm.poc/CalculadoraService/", "CalculadoraService"));
		Dispatch<Source> dispatch = service.createDispatch(
				new QName("http://rcm.poc/CalculadoraService/",
						"CalculadoraServiceSOAP"), Source.class,
				Service.Mode.PAYLOAD);
		Source msg = new StreamSource(
				new StringReader(
						"   <cal:Argumentos xmlns:cal=\"http://rcm.poc/CalculadoraService/\">"
								+ "      <Sumando>3</Sumando>"
								+ "      <Sumando>5</Sumando>"
								+ "   </cal:Argumentos>"));
		Source response = dispatch.invoke(msg);
	}

	@Test
	public void testSumar_DynamicClient() {

	}

}
