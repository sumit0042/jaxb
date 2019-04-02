//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.24 at 02:13:04 PM IST 
//


package com.backend.unfound.xmlparser;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.backend.unfound.xmlparser package. 
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

    private final static QName _Vehicles_QNAME = new QName("", "vehicles");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.backend.unfound.xmlparser
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VehiclesType }
     * 
     */
    public VehiclesType createVehiclesType() {
        return new VehiclesType();
    }

    /**
     * Create an instance of {@link WheelsType }
     * 
     */
    public WheelsType createWheelsType() {
        return new WheelsType();
    }

    /**
     * Create an instance of {@link WheelType }
     * 
     */
    public WheelType createWheelType() {
        return new WheelType();
    }

    /**
     * Create an instance of {@link FrameType }
     * 
     */
    public FrameType createFrameType() {
        return new FrameType();
    }

    /**
     * Create an instance of {@link VehicleType }
     * 
     */
    public VehicleType createVehicleType() {
        return new VehicleType();
    }

    /**
     * Create an instance of {@link PowertrainType }
     * 
     */
    public PowertrainType createPowertrainType() {
        return new PowertrainType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VehiclesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "vehicles")
    public JAXBElement<VehiclesType> createVehicles(VehiclesType value) {
        return new JAXBElement<VehiclesType>(_Vehicles_QNAME, VehiclesType.class, null, value);
    }

}