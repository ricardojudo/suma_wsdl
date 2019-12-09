package poc.rcm.calculadoraservice.impl;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.juddi.v3.annotations.UDDIService;
import org.apache.juddi.v3.annotations.UDDIServiceBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poc.rcm.calculadoraservice.ArgumentosType;
import poc.rcm.calculadoraservice.CalculadoraService;
import poc.rcm.calculadoraservice.ResultadoType;

@WebService(endpointInterface = "poc.rcm.calculadoraservice.CalculadoraService", targetNamespace = "http://rcm.poc/CalculadoraService/", serviceName = "CalculadoraService", wsdlLocation = "CalculadoraService.wsdl")
@UDDIService(businessKey = "uddi:${keyDomain}:${department}-asf", serviceKey = "uddi:${keyDomain}:services-${department}", description = "Servicio calculadora")
@UDDIServiceBinding(bindingKey = "uddi:${keyDomain}:${serverName}-${serverPort}-${department}-wsdl", description = "WSDL endpoint for the ${department} Service. This service is used for "
		+ "testing the jUDDI annotation functionality", accessPointType = "wsdlDeployment", accessPoint = "http://${serverName}:${serverPort}/${appContext}/CalculadoraService.jws?wsdl")
public class CalculadoraServiceImpl implements CalculadoraService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CalculadoraServiceImpl.class);

	@Resource
	private WebServiceContext context;

	public ResultadoType sumar(ArgumentosType parameters) {

		ResultadoType resultado = new ResultadoType();
		int suma = 0;
		for (Integer s : parameters.getSumando()) {
			suma += s;
		}
		resultado.setValor(suma);

		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LOGGER.info("Sumar: {} = {}", parameters.getSumando(), suma);
		LOGGER.info("Invocado por: {}", context.getUserPrincipal());
		LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return resultado;
	}

}
