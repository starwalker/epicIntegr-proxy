package edu.musc.bi.cxf;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AlertProtocolState", namespace = "urn:ihe:qrph:rpe:2009")
@XmlType(
        name = "alertProtocolState",
        propOrder = {"processState", "comment", "patient", "study", "actDefinition"})
public class AlertProtocolState {
    @XmlElement(required = true, namespace = "urn:ihe:qrph:rpe:2009")
    protected String processState;

    @XmlElement(required = false, namespace = "urn:ihe:qrph:rpe:2009")
    protected String comment;

    @XmlElement(required = false, namespace = "urn:ihe:qrph:rpe:2009")
    protected AlertProtocolState.Patient patient;

    @XmlElement(required = true, namespace = "urn:hl7-org:v3")
    protected AlertProtocolState.Study study;

    @XmlElement(required = false, namespace = "urn:ihe:qrph:rpe:2009")
    protected AlertProtocolState.ActDefinition actDefinition;

    public AlertProtocolState() {}

    /**
     * Gets the value of the processState property.
     *
     * @return possible object is {@link Object }
     */
    public String getProcessState() {
        return this.processState;
    }

    public void setProcessState(String var1) {
        this.processState = var1;
    }

    /**
     * Gets the value of the comment property.
     *
     * @return possible object is {@link Object }
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     *
     * @param value allowed object is {@link Object }
     */
    public void setComment(final String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the patient property.
     *
     * @return possible object is {@link AlertProtocolState.Patient }
     */
    public AlertProtocolState.Patient getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     *
     * @param value allowed object is {@link AlertProtocolState.Patient }
     */
    public void setPatient(AlertProtocolState.Patient value) {
        this.patient = value;
    }

    /**
     * Gets the value of the study property.
     *
     * @return possible object is {@link AlertProtocolState.Study }
     */
    public AlertProtocolState.Study getStudy() {
        return study;
    }

    /**
     * Sets the value of the study property.
     *
     * @param value allowed object is {@link AlertProtocolState.Study }
     */
    public void setStudy(AlertProtocolState.Study value) {
        this.study = value;
    }

    /**
     * Gets the value of the actDefinition property.
     *
     * @return possible object is {@link AlertProtocolState.ActDefinition }
     */
    public AlertProtocolState.ActDefinition getActDefinition() {
        return actDefinition;
    }

    /**
     * Sets the value of the actDefinition property.
     *
     * @param value allowed object is {@link AlertProtocolState.ActDefinition }
     */
    public void setActDefinition(AlertProtocolState.ActDefinition value) {
        this.actDefinition = value;
    }

    /**
     * Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="armStub" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="id"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                           &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "",
            propOrder = {"armStub"})
    public static class ActDefinition {

        @XmlElement(required = true, namespace = "urn:hl7-org:v3")
        protected List<AlertProtocolState.ActDefinition.ArmStub> armStub;

        /**
         * Gets the value of the armStub property.
         *
         * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore
         * any modification you make to the returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the armStub property.
         *
         * <p>For example, to add a new item, do as follows:
         *
         * <pre>
         *    getArmStub().add(newItem);
         * </pre>
         *
         * <p>Objects of the following type(s) are allowed in the list {@link
         * AlertProtocolState.ActDefinition.ArmStub }
         */
        public List<AlertProtocolState.ActDefinition.ArmStub> getArmStub() {
            if (armStub == null) {
                armStub = new ArrayList<AlertProtocolState.ActDefinition.ArmStub>();
            }
            return this.armStub;
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="id"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                 &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(
                name = "",
                propOrder = {"id"})
        public static class ArmStub {

            @XmlElement(required = true, namespace = "urn:hl7-org:v3")
            protected AlertProtocolState.ActDefinition.ArmStub.Id id;

            /**
             * Gets the value of the id property.
             *
             * @return possible object is {@link AlertProtocolState.ActDefinition.ArmStub.Id }
             */
            public AlertProtocolState.ActDefinition.ArmStub.Id getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             *
             * @param value allowed object is {@link AlertProtocolState.ActDefinition.ArmStub.Id }
             */
            public void setId(AlertProtocolState.ActDefinition.ArmStub.Id value) {
                this.id = value;
            }

            /**
             * Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this
             * class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Id {

                @XmlAttribute(name = "root")
                @XmlSchemaType(name = "anySimpleType")
                protected String root;

                @XmlAttribute(name = "extension")
                @XmlSchemaType(name = "anySimpleType")
                protected String extension;

                /**
                 * Gets the value of the root property.
                 *
                 * @return possible object is {@link String }
                 */
                public String getRoot() {
                    return root;
                }

                /**
                 * Sets the value of the root property.
                 *
                 * @param value allowed object is {@link String }
                 */
                public void setRoot(String value) {
                    this.root = value;
                }

                /**
                 * Gets the value of the extension property.
                 *
                 * @return possible object is {@link String }
                 */
                public String getExtension() {
                    return extension;
                }

                /**
                 * Sets the value of the extension property.
                 *
                 * @param value allowed object is {@link String }
                 */
                public void setExtension(String value) {
                    this.extension = value;
                }
            }
        }
    }

    /**
     * Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="candidateID"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                 &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="subjectID"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                 &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="name"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="given" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                   &lt;element name="family" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="address"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="streetAddressLine" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                   &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                   &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                   &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                   &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="dob"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "",
            propOrder = {"candidateID", "subjectID", "name", "address", "dob"},
            namespace = "urn:ihe:qrph:rpe:2009")
    public static class Patient {

        @XmlElement(required = true, namespace = "urn:ihe:qrph:rpe:2009")
        protected AlertProtocolState.Patient.CandidateID candidateID;

        @XmlElement(required = true, namespace = "urn:ihe:qrph:rpe:2009")
        protected AlertProtocolState.Patient.SubjectID subjectID;

        @XmlElement(required = true, namespace = "urn:ihe:qrph:rpe:2009")
        protected AlertProtocolState.Patient.Name name;

        @XmlElement(required = true, namespace = "urn:ihe:qrph:rpe:2009")
        protected AlertProtocolState.Patient.Address address;

        @XmlElement(required = true, namespace = "urn:ihe:qrph:rpe:2009")
        protected AlertProtocolState.Patient.Dob dob;

        /**
         * Gets the value of the candidateID property.
         *
         * @return possible object is {@link AlertProtocolState.Patient.CandidateID }
         */
        public AlertProtocolState.Patient.CandidateID getCandidateID() {
            return candidateID;
        }

        /**
         * Sets the value of the candidateID property.
         *
         * @param value allowed object is {@link AlertProtocolState.Patient.CandidateID }
         */
        public void setCandidateID(AlertProtocolState.Patient.CandidateID value) {
            this.candidateID = value;
        }

        /**
         * Gets the value of the subjectID property.
         *
         * @return possible object is {@link AlertProtocolState.Patient.SubjectID }
         */
        public AlertProtocolState.Patient.SubjectID getSubjectID() {
            return subjectID;
        }

        /**
         * Sets the value of the subjectID property.
         *
         * @param value allowed object is {@link AlertProtocolState.Patient.SubjectID }
         */
        public void setSubjectID(AlertProtocolState.Patient.SubjectID value) {
            this.subjectID = value;
        }

        /**
         * Gets the value of the name property.
         *
         * @return possible object is {@link AlertProtocolState.Patient.Name }
         */
        public AlertProtocolState.Patient.Name getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value allowed object is {@link AlertProtocolState.Patient.Name }
         */
        public void setName(AlertProtocolState.Patient.Name value) {
            this.name = value;
        }

        /**
         * Gets the value of the address property.
         *
         * @return possible object is {@link AlertProtocolState.Patient.Address }
         */
        public AlertProtocolState.Patient.Address getAddress() {
            return address;
        }

        /**
         * Sets the value of the address property.
         *
         * @param value allowed object is {@link AlertProtocolState.Patient.Address }
         */
        public void setAddress(AlertProtocolState.Patient.Address value) {
            this.address = value;
        }

        /**
         * Gets the value of the dob property.
         *
         * @return possible object is {@link AlertProtocolState.Patient.Dob }
         */
        public AlertProtocolState.Patient.Dob getDob() {
            return dob;
        }

        /**
         * Sets the value of the dob property.
         *
         * @param value allowed object is {@link AlertProtocolState.Patient.Dob }
         */
        public void setDob(AlertProtocolState.Patient.Dob value) {
            this.dob = value;
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="streetAddressLine" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(
                name = "",
                propOrder = {"streetAddressLine", "city", "state", "postalCode", "country"},
                namespace = "urn:ihe:qrph:rpe:2009")
        public static class Address {

            @XmlElement(required = false, namespace = "urn:hl7-org:v3")
            protected Object streetAddressLine;

            @XmlElement(required = false, namespace = "urn:hl7-org:v3")
            protected Object city;

            @XmlElement(required = false, namespace = "urn:hl7-org:v3")
            protected Object state;

            @XmlElement(required = false, namespace = "urn:hl7-org:v3")
            protected Object postalCode;

            @XmlElement(required = false, namespace = "urn:hl7-org:v3")
            protected Object country;

            /**
             * Gets the value of the streetAddressLine property.
             *
             * @return possible object is {@link Object }
             */
            public Object getStreetAddressLine() {
                return streetAddressLine;
            }

            /**
             * Sets the value of the streetAddressLine property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setStreetAddressLine(Object value) {
                this.streetAddressLine = value;
            }

            /**
             * Gets the value of the city property.
             *
             * @return possible object is {@link Object }
             */
            public Object getCity() {
                return city;
            }

            /**
             * Sets the value of the city property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setCity(Object value) {
                this.city = value;
            }

            /**
             * Gets the value of the state property.
             *
             * @return possible object is {@link Object }
             */
            public Object getState() {
                return state;
            }

            /**
             * Sets the value of the state property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setState(Object value) {
                this.state = value;
            }

            /**
             * Gets the value of the postalCode property.
             *
             * @return possible object is {@link Object }
             */
            public Object getPostalCode() {
                return postalCode;
            }

            /**
             * Sets the value of the postalCode property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setPostalCode(Object value) {
                this.postalCode = value;
            }

            /**
             * Gets the value of the country property.
             *
             * @return possible object is {@link Object }
             */
            public Object getCountry() {
                return country;
            }

            /**
             * Sets the value of the country property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setCountry(Object value) {
                this.country = value;
            }
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", namespace = "urn:ihe:qrph:rpe:2009")
        public static class CandidateID {

            @XmlAttribute(name = "root")
            @XmlSchemaType(name = "anySimpleType")
            protected String root;

            @XmlAttribute(name = "extension")
            @XmlSchemaType(name = "anySimpleType")
            protected String extension;

            /**
             * Gets the value of the root property.
             *
             * @return possible object is {@link String }
             */
            public String getRoot() {
                return root;
            }

            /**
             * Sets the value of the root property.
             *
             * @param value allowed object is {@link String }
             */
            public void setRoot(String value) {
                this.root = value;
            }

            /**
             * Gets the value of the extension property.
             *
             * @return possible object is {@link String }
             */
            public String getExtension() {
                return extension;
            }

            /**
             * Sets the value of the extension property.
             *
             * @param value allowed object is {@link String }
             */
            public void setExtension(String value) {
                this.extension = value;
            }
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", namespace = "urn:ihe:qrph:rpe:2009")
        public static class Dob {

            @XmlAttribute(name = "value")
            @XmlSchemaType(name = "anySimpleType")
            protected String value;

            /**
             * Gets the value of the value property.
             *
             * @return possible object is {@link String }
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             *
             * @param value allowed object is {@link String }
             */
            public void setValue(String value) {
                this.value = value;
            }
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="given" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *         &lt;element name="family" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        // XmlAccessType.FIELD causes likely skip element, in this case, lost given and family
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "nameAlert")
        protected static class Name {

            @XmlElementWrapper(name = "given", namespace = "urn:hl7-org:v3")
            @XmlElement(required = true, name = "given", namespace = "urn:hl7-org:v3")
            protected List<String> given;

            @XmlElement(required = true, namespace = "urn:hl7-org:v3")
            protected String family;

            /**
             * Gets the value of the given property.
             *
             * @return possible object is {@link Object }
             */
            public List<String> getGiven() {
                return given;
            }

            /**
             * Sets the value of the given property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setGiven(List<String> value) {
                this.given = value;
            }

            /**
             * Gets the value of the family property.
             *
             * @return possible object is {@link Object }
             */
            public String getFamily() {
                return family;
            }

            /**
             * Sets the value of the family property.
             *
             * @param value allowed object is {@link Object }
             */
            public void setFamily(final String value) {
                this.family = value;
            }
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", namespace = "urn:ihe:qrph:rpe:2009")
        public static class SubjectID {

            @XmlAttribute(name = "root")
            @XmlSchemaType(name = "anySimpleType")
            protected String root;

            @XmlAttribute(name = "extension")
            @XmlSchemaType(name = "anySimpleType")
            protected String extension;

            /**
             * Gets the value of the root property.
             *
             * @return possible object is {@link String }
             */
            public String getRoot() {
                return root;
            }

            /**
             * Sets the value of the root property.
             *
             * @param value allowed object is {@link String }
             */
            public void setRoot(String value) {
                this.root = value;
            }

            /**
             * Gets the value of the extension property.
             *
             * @return possible object is {@link String }
             */
            public String getExtension() {
                return extension;
            }

            /**
             * Sets the value of the extension property.
             *
             * @param value allowed object is {@link String }
             */
            public void setExtension(String value) {
                this.extension = value;
            }
        }
    }

    /**
     * Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
     *         &lt;element name="instantiation"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="plannedStudy"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="id"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                     &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="component1"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="studyActivitiesAtSite"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="subject1"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="experimentalUnit"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="effectiveTime"&gt;
     *                                                   &lt;complexType&gt;
     *                                                     &lt;complexContent&gt;
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                         &lt;sequence&gt;
     *                                                           &lt;element name="low"&gt;
     *                                                             &lt;complexType&gt;
     *                                                               &lt;complexContent&gt;
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                                   &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                                                 &lt;/restriction&gt;
     *                                                               &lt;/complexContent&gt;
     *                                                             &lt;/complexType&gt;
     *                                                           &lt;/element&gt;
     *                                                           &lt;element name="high"&gt;
     *                                                             &lt;complexType&gt;
     *                                                               &lt;complexContent&gt;
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                                   &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                                                 &lt;/restriction&gt;
     *                                                               &lt;/complexContent&gt;
     *                                                             &lt;/complexType&gt;
     *                                                           &lt;/element&gt;
     *                                                         &lt;/sequence&gt;
     *                                                       &lt;/restriction&gt;
     *                                                     &lt;/complexContent&gt;
     *                                                   &lt;/complexType&gt;
     *                                                 &lt;/element&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="secondaryPerformer"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="serviceProvider"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="id"&gt;
     *                                                   &lt;complexType&gt;
     *                                                     &lt;complexContent&gt;
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                         &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                                         &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                                       &lt;/restriction&gt;
     *                                                     &lt;/complexContent&gt;
     *                                                   &lt;/complexType&gt;
     *                                                 &lt;/element&gt;
     *                                                 &lt;element name="code"&gt;
     *                                                   &lt;complexType&gt;
     *                                                     &lt;complexContent&gt;
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                         &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
     *                                                       &lt;/restriction&gt;
     *                                                     &lt;/complexContent&gt;
     *                                                   &lt;/complexType&gt;
     *                                                 &lt;/element&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "",
            propOrder = {"id", "instantiation", "component1"},
            namespace = "urn:hl7-org:v3")
    public static class Study {

        @XmlElement(required = true, namespace = "urn:hl7-org:v3")
        protected String id;

        @XmlElement(required = true, namespace = "urn:hl7-org:v3")
        protected AlertProtocolState.Study.Instantiation instantiation;

        @XmlElement(required = true, namespace = "urn:hl7-org:v3")
        protected AlertProtocolState.Study.Component1 component1;

        /**
         * Gets the value of the processState property.
         *
         * <p>Wei
         *
         * @return possible object is {@link Object }
         */
        public String getStudyCode() {
            String rst = "";
            // System.out.println(study);
            AlertProtocolState.Study.Instantiation instantiation = this.getInstantiation();
            if (instantiation != null) {
                AlertProtocolState.Study.Instantiation.PlannedStudy plannedStudy =
                        instantiation.getPlannedStudy();
                if (plannedStudy != null) {
                    AlertProtocolState.Study.Instantiation.PlannedStudy.Id id =
                            plannedStudy.getId();
                    if (id != null) {
                        final String root = id.getRoot();
                        if (!StringUtils.isBlank(root)) {
                            if (StringUtils.equalsIgnoreCase(root, "1.1.8.7.7")) {
                                final String extension = id.getExtension();
                                if (!StringUtils.isBlank(extension)) {
                                    rst = extension;
                                }
                            }
                        }
                    }
                }
            }
            return rst;
        }

        /**
         * Gets the value of the id property.
         *
         * @return possible object is {@link Object }
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         *
         * @param value allowed object is {@link Object }
         */
        public void setId(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the instantiation property.
         *
         * @return possible object is {@link AlertProtocolState.Study.Instantiation }
         */
        public AlertProtocolState.Study.Instantiation getInstantiation() {
            return instantiation;
        }

        /**
         * Sets the value of the instantiation property.
         *
         * @param value allowed object is {@link AlertProtocolState.Study.Instantiation }
         */
        public void setInstantiation(AlertProtocolState.Study.Instantiation value) {
            this.instantiation = value;
        }

        /**
         * Gets the value of the component1 property.
         *
         * @return possible object is {@link AlertProtocolState.Study.Component1 }
         */
        public AlertProtocolState.Study.Component1 getComponent1() {
            return component1;
        }

        /**
         * Sets the value of the component1 property.
         *
         * @param value allowed object is {@link AlertProtocolState.Study.Component1 }
         */
        public void setComponent1(AlertProtocolState.Study.Component1 value) {
            this.component1 = value;
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="studyActivitiesAtSite"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="subject1"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="experimentalUnit"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="effectiveTime"&gt;
         *                                         &lt;complexType&gt;
         *                                           &lt;complexContent&gt;
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                               &lt;sequence&gt;
         *                                                 &lt;element name="low"&gt;
         *                                                   &lt;complexType&gt;
         *                                                     &lt;complexContent&gt;
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                                         &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                                                       &lt;/restriction&gt;
         *                                                     &lt;/complexContent&gt;
         *                                                   &lt;/complexType&gt;
         *                                                 &lt;/element&gt;
         *                                                 &lt;element name="high"&gt;
         *                                                   &lt;complexType&gt;
         *                                                     &lt;complexContent&gt;
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                                         &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                                                       &lt;/restriction&gt;
         *                                                     &lt;/complexContent&gt;
         *                                                   &lt;/complexType&gt;
         *                                                 &lt;/element&gt;
         *                                               &lt;/sequence&gt;
         *                                             &lt;/restriction&gt;
         *                                           &lt;/complexContent&gt;
         *                                         &lt;/complexType&gt;
         *                                       &lt;/element&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="secondaryPerformer"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="serviceProvider"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="id"&gt;
         *                                         &lt;complexType&gt;
         *                                           &lt;complexContent&gt;
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                               &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                                               &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                                             &lt;/restriction&gt;
         *                                           &lt;/complexContent&gt;
         *                                         &lt;/complexType&gt;
         *                                       &lt;/element&gt;
         *                                       &lt;element name="code"&gt;
         *                                         &lt;complexType&gt;
         *                                           &lt;complexContent&gt;
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                               &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                                             &lt;/restriction&gt;
         *                                           &lt;/complexContent&gt;
         *                                         &lt;/complexType&gt;
         *                                       &lt;/element&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(
                name = "",
                propOrder = {"studyActivitiesAtSite"})
        public static class Component1 {

            @XmlElement(required = false, namespace = "urn:hl7-org:v3")
            protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                    studyActivitiesAtSite;

            /**
             * Gets the value of the studyActivitiesAtSite property.
             *
             * @return possible object is {@link AlertProtocolState.Study.Component1
             *     .StudyActivitiesAtSite }
             */
            public AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                    getStudyActivitiesAtSite() {
                return studyActivitiesAtSite;
            }

            /**
             * Sets the value of the studyActivitiesAtSite property.
             *
             * @param value allowed object is {@link AlertProtocolState.Study.Component1
             *     .StudyActivitiesAtSite }
             */
            public void setStudyActivitiesAtSite(
                    AlertProtocolState.Study.Component1.StudyActivitiesAtSite value) {
                this.studyActivitiesAtSite = value;
            }

            /**
             * Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this
             * class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="subject1"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="experimentalUnit"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="effectiveTime"&gt;
             *                               &lt;complexType&gt;
             *                                 &lt;complexContent&gt;
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                     &lt;sequence&gt;
             *                                       &lt;element name="low"&gt;
             *                                         &lt;complexType&gt;
             *                                           &lt;complexContent&gt;
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                               &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *                                             &lt;/restriction&gt;
             *                                           &lt;/complexContent&gt;
             *                                         &lt;/complexType&gt;
             *                                       &lt;/element&gt;
             *                                       &lt;element name="high"&gt;
             *                                         &lt;complexType&gt;
             *                                           &lt;complexContent&gt;
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                               &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *                                             &lt;/restriction&gt;
             *                                           &lt;/complexContent&gt;
             *                                         &lt;/complexType&gt;
             *                                       &lt;/element&gt;
             *                                     &lt;/sequence&gt;
             *                                   &lt;/restriction&gt;
             *                                 &lt;/complexContent&gt;
             *                               &lt;/complexType&gt;
             *                             &lt;/element&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="secondaryPerformer"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="serviceProvider"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="id"&gt;
             *                               &lt;complexType&gt;
             *                                 &lt;complexContent&gt;
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                     &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *                                     &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *                                   &lt;/restriction&gt;
             *                                 &lt;/complexContent&gt;
             *                               &lt;/complexType&gt;
             *                             &lt;/element&gt;
             *                             &lt;element name="code"&gt;
             *                               &lt;complexType&gt;
             *                                 &lt;complexContent&gt;
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                     &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *                                   &lt;/restriction&gt;
             *                                 &lt;/complexContent&gt;
             *                               &lt;/complexType&gt;
             *                             &lt;/element&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(
                    name = "",
                    propOrder = {"subject1", "secondaryPerformer"})
            public static class StudyActivitiesAtSite {

                @XmlElement(required = false, namespace = "urn:hl7-org:v3")
                protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                        subject1;

                @XmlElement(required = false, namespace = "urn:hl7-org:v3")
                protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                .SecondaryPerformer
                        secondaryPerformer;

                /**
                 * Gets the value of the subject1 property.
                 *
                 * @return possible object is {@link AlertProtocolState.Study.Component1
                 *     .StudyActivitiesAtSite.Subject1 }
                 */
                public AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                        getSubject1() {
                    return subject1;
                }

                /**
                 * Sets the value of the subject1 property.
                 *
                 * @param value allowed object is {@link AlertProtocolState.Study.Component1
                 *     .StudyActivitiesAtSite.Subject1 }
                 */
                public void setSubject1(
                        AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1 value) {
                    this.subject1 = value;
                }

                /**
                 * Gets the value of the secondaryPerformer property.
                 *
                 * @return possible object is {@link AlertProtocolState.Study.Component1
                 *     .StudyActivitiesAtSite.SecondaryPerformer }
                 */
                public AlertProtocolState.Study.Component1.StudyActivitiesAtSite.SecondaryPerformer
                        getSecondaryPerformer() {
                    return secondaryPerformer;
                }

                /**
                 * Sets the value of the secondaryPerformer property.
                 *
                 * @param value allowed object is {@link AlertProtocolState.Study.Component1
                 *     .StudyActivitiesAtSite.SecondaryPerformer }
                 */
                public void setSecondaryPerformer(
                        AlertProtocolState.Study.Component1.StudyActivitiesAtSite.SecondaryPerformer
                                value) {
                    this.secondaryPerformer = value;
                }

                /**
                 * Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within
                 * this class.
                 *
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="serviceProvider"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="id"&gt;
                 *                     &lt;complexType&gt;
                 *                       &lt;complexContent&gt;
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                           &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *                           &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *                         &lt;/restriction&gt;
                 *                       &lt;/complexContent&gt;
                 *                     &lt;/complexType&gt;
                 *                   &lt;/element&gt;
                 *                   &lt;element name="code"&gt;
                 *                     &lt;complexType&gt;
                 *                       &lt;complexContent&gt;
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                           &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *                         &lt;/restriction&gt;
                 *                       &lt;/complexContent&gt;
                 *                     &lt;/complexType&gt;
                 *                   &lt;/element&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(
                        name = "",
                        propOrder = {"serviceProvider"})
                public static class SecondaryPerformer {

                    @XmlElement(required = false, namespace = "urn:hl7-org:v3")
                    protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                    .SecondaryPerformer.ServiceProvider
                            serviceProvider;

                    /**
                     * Gets the value of the serviceProvider property.
                     *
                     * @return possible object is {@link AlertProtocolState.Study.Component1
                     *     .StudyActivitiesAtSite.SecondaryPerformer.ServiceProvider }
                     */
                    public AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                    .SecondaryPerformer.ServiceProvider
                            getServiceProvider() {
                        return serviceProvider;
                    }

                    /**
                     * Sets the value of the serviceProvider property.
                     *
                     * @param value allowed object is {@link AlertProtocolState.Study.Component1
                     *     .StudyActivitiesAtSite.SecondaryPerformer.ServiceProvider }
                     */
                    public void setServiceProvider(
                            AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                            .SecondaryPerformer.ServiceProvider
                                    value) {
                        this.serviceProvider = value;
                    }

                    /**
                     * Java class for anonymous complex type.
                     *
                     * <p>The following schema fragment specifies the expected content contained
                     * within this class.
                     *
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;sequence&gt;
                     *         &lt;element name="id"&gt;
                     *           &lt;complexType&gt;
                     *             &lt;complexContent&gt;
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                     *                 &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                     *               &lt;/restriction&gt;
                     *             &lt;/complexContent&gt;
                     *           &lt;/complexType&gt;
                     *         &lt;/element&gt;
                     *         &lt;element name="code"&gt;
                     *           &lt;complexType&gt;
                     *             &lt;complexContent&gt;
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                     *               &lt;/restriction&gt;
                     *             &lt;/complexContent&gt;
                     *           &lt;/complexType&gt;
                     *         &lt;/element&gt;
                     *       &lt;/sequence&gt;
                     *     &lt;/restriction&gt;
                     *   &lt;/complexContent&gt;
                     * &lt;/complexType&gt;
                     * </pre>
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(
                            name = "",
                            propOrder = {"id", "code"})
                    public static class ServiceProvider {

                        @XmlElement(required = false, namespace = "urn:hl7-org:v3")
                        protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                        .SecondaryPerformer.ServiceProvider.Id
                                id;

                        @XmlElement(required = false, namespace = "urn:hl7-org:v3")
                        protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                        .SecondaryPerformer.ServiceProvider.Code
                                code;

                        /**
                         * Gets the value of the id property.
                         *
                         * @return possible object is {@link AlertProtocolState.Study.Component1
                         *     .StudyActivitiesAtSite.SecondaryPerformer.ServiceProvider.Id }
                         */
                        public AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                        .SecondaryPerformer.ServiceProvider.Id
                                getId() {
                            return id;
                        }

                        /**
                         * Sets the value of the id property.
                         *
                         * @param value allowed object is {@link AlertProtocolState.Study.Component1
                         *     .StudyActivitiesAtSite.SecondaryPerformer.ServiceProvider.Id }
                         */
                        public void setId(
                                AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                                .SecondaryPerformer.ServiceProvider.Id
                                        value) {
                            this.id = value;
                        }

                        /**
                         * Gets the value of the code property.
                         *
                         * @return possible object is {@link AlertProtocolState.Study.Component1
                         *     .StudyActivitiesAtSite.SecondaryPerformer.ServiceProvider.Code }
                         */
                        public AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                        .SecondaryPerformer.ServiceProvider.Code
                                getCode() {
                            return code;
                        }

                        /**
                         * Sets the value of the code property.
                         *
                         * @param value allowed object is {@link AlertProtocolState.Study.Component1
                         *     .StudyActivitiesAtSite.SecondaryPerformer.ServiceProvider.Code }
                         */
                        public void setCode(
                                AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                                .SecondaryPerformer.ServiceProvider.Code
                                        value) {
                            this.code = value;
                        }

                        /**
                         * Java class for anonymous complex type.
                         *
                         * <p>The following schema fragment specifies the expected content contained
                         * within this class.
                         *
                         * <pre>
                         * &lt;complexType&gt;
                         *   &lt;complexContent&gt;
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                         *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                         *     &lt;/restriction&gt;
                         *   &lt;/complexContent&gt;
                         * &lt;/complexType&gt;
                         * </pre>
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", namespace = "urn:ihe:qrph:rpe:2009")
                        public static class Code {

                            @XmlAttribute(name = "code")
                            @XmlSchemaType(name = "anySimpleType")
                            protected String code;

                            /**
                             * Gets the value of the code property.
                             *
                             * @return possible object is {@link String }
                             */
                            public String getCode() {
                                return code;
                            }

                            /**
                             * Sets the value of the code property.
                             *
                             * @param value allowed object is {@link String }
                             */
                            public void setCode(String value) {
                                this.code = value;
                            }
                        }

                        /**
                         * Java class for anonymous complex type.
                         *
                         * <p>The following schema fragment specifies the expected content contained
                         * within this class.
                         *
                         * <pre>
                         * &lt;complexType&gt;
                         *   &lt;complexContent&gt;
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                         *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                         *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                         *     &lt;/restriction&gt;
                         *   &lt;/complexContent&gt;
                         * &lt;/complexType&gt;
                         * </pre>
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", namespace = "urn:ihe:qrph:rpe:2009")
                        public static class Id {

                            @XmlAttribute(name = "root")
                            @XmlSchemaType(name = "anySimpleType")
                            protected String root;

                            @XmlAttribute(name = "extension")
                            @XmlSchemaType(name = "anySimpleType")
                            protected String extension;

                            /**
                             * Gets the value of the root property.
                             *
                             * @return possible object is {@link String }
                             */
                            public String getRoot() {
                                return root;
                            }

                            /**
                             * Sets the value of the root property.
                             *
                             * @param value allowed object is {@link String }
                             */
                            public void setRoot(String value) {
                                this.root = value;
                            }

                            /**
                             * Gets the value of the extension property.
                             *
                             * @return possible object is {@link String }
                             */
                            public String getExtension() {
                                return extension;
                            }

                            /**
                             * Sets the value of the extension property.
                             *
                             * @param value allowed object is {@link String }
                             */
                            public void setExtension(String value) {
                                this.extension = value;
                            }
                        }
                    }
                }

                /**
                 * Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within
                 * this class.
                 *
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="experimentalUnit"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="effectiveTime"&gt;
                 *                     &lt;complexType&gt;
                 *                       &lt;complexContent&gt;
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                           &lt;sequence&gt;
                 *                             &lt;element name="low"&gt;
                 *                               &lt;complexType&gt;
                 *                                 &lt;complexContent&gt;
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                                     &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *                                   &lt;/restriction&gt;
                 *                                 &lt;/complexContent&gt;
                 *                               &lt;/complexType&gt;
                 *                             &lt;/element&gt;
                 *                             &lt;element name="high"&gt;
                 *                               &lt;complexType&gt;
                 *                                 &lt;complexContent&gt;
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                                     &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *                                   &lt;/restriction&gt;
                 *                                 &lt;/complexContent&gt;
                 *                               &lt;/complexType&gt;
                 *                             &lt;/element&gt;
                 *                           &lt;/sequence&gt;
                 *                         &lt;/restriction&gt;
                 *                       &lt;/complexContent&gt;
                 *                     &lt;/complexType&gt;
                 *                   &lt;/element&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(
                        name = "",
                        propOrder = {"experimentalUnit"})
                public static class Subject1 {

                    @XmlElement(required = true, namespace = "urn:hl7-org:v3")
                    protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                                    .ExperimentalUnit
                            experimentalUnit;

                    /**
                     * Gets the value of the experimentalUnit property.
                     *
                     * @return possible object is {@link AlertProtocolState.Study.Component1
                     *     .StudyActivitiesAtSite.Subject1 .ExperimentalUnit }
                     */
                    public AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                                    .ExperimentalUnit
                            getExperimentalUnit() {
                        return experimentalUnit;
                    }

                    /**
                     * Sets the value of the experimentalUnit property.
                     *
                     * @param value allowed object is {@link AlertProtocolState.Study.Component1
                     *     .StudyActivitiesAtSite.Subject1 .ExperimentalUnit }
                     */
                    public void setExperimentalUnit(
                            AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                                            .ExperimentalUnit
                                    value) {
                        this.experimentalUnit = value;
                    }

                    /**
                     * Java class for anonymous complex type.
                     *
                     * <p>The following schema fragment specifies the expected content contained
                     * within this class.
                     *
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;sequence&gt;
                     *         &lt;element name="effectiveTime"&gt;
                     *           &lt;complexType&gt;
                     *             &lt;complexContent&gt;
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                 &lt;sequence&gt;
                     *                   &lt;element name="low"&gt;
                     *                     &lt;complexType&gt;
                     *                       &lt;complexContent&gt;
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                     *                         &lt;/restriction&gt;
                     *                       &lt;/complexContent&gt;
                     *                     &lt;/complexType&gt;
                     *                   &lt;/element&gt;
                     *                   &lt;element name="high"&gt;
                     *                     &lt;complexType&gt;
                     *                       &lt;complexContent&gt;
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                     *                         &lt;/restriction&gt;
                     *                       &lt;/complexContent&gt;
                     *                     &lt;/complexType&gt;
                     *                   &lt;/element&gt;
                     *                 &lt;/sequence&gt;
                     *               &lt;/restriction&gt;
                     *             &lt;/complexContent&gt;
                     *           &lt;/complexType&gt;
                     *         &lt;/element&gt;
                     *       &lt;/sequence&gt;
                     *     &lt;/restriction&gt;
                     *   &lt;/complexContent&gt;
                     * &lt;/complexType&gt;
                     * </pre>
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(
                            name = "",
                            propOrder = {"effectiveTime"})
                    public static class ExperimentalUnit {

                        @XmlElement(required = true, namespace = "urn:hl7-org:v3")
                        protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                                        .ExperimentalUnit.EffectiveTime
                                effectiveTime;

                        /**
                         * Gets the value of the effectiveTime property.
                         *
                         * @return possible object is {@link AlertProtocolState.Study.Component1
                         *     .StudyActivitiesAtSite.Subject1 .ExperimentalUnit.EffectiveTime }
                         */
                        public AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                                        .ExperimentalUnit.EffectiveTime
                                getEffectiveTime() {
                            return effectiveTime;
                        }

                        /**
                         * Sets the value of the effectiveTime property.
                         *
                         * @param value allowed object is {@link AlertProtocolState.Study.Component1
                         *     .StudyActivitiesAtSite.Subject1 .ExperimentalUnit.EffectiveTime }
                         */
                        public void setEffectiveTime(
                                AlertProtocolState.Study.Component1.StudyActivitiesAtSite.Subject1
                                                .ExperimentalUnit.EffectiveTime
                                        value) {
                            this.effectiveTime = value;
                        }

                        /**
                         * Java class for anonymous complex type.
                         *
                         * <p>The following schema fragment specifies the expected content contained
                         * within this class.
                         *
                         * <pre>
                         * &lt;complexType&gt;
                         *   &lt;complexContent&gt;
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                         *       &lt;sequence&gt;
                         *         &lt;element name="low"&gt;
                         *           &lt;complexType&gt;
                         *             &lt;complexContent&gt;
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                         *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                         *               &lt;/restriction&gt;
                         *             &lt;/complexContent&gt;
                         *           &lt;/complexType&gt;
                         *         &lt;/element&gt;
                         *         &lt;element name="high"&gt;
                         *           &lt;complexType&gt;
                         *             &lt;complexContent&gt;
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                         *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                         *               &lt;/restriction&gt;
                         *             &lt;/complexContent&gt;
                         *           &lt;/complexType&gt;
                         *         &lt;/element&gt;
                         *       &lt;/sequence&gt;
                         *     &lt;/restriction&gt;
                         *   &lt;/complexContent&gt;
                         * &lt;/complexType&gt;
                         * </pre>
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(
                                name = "",
                                propOrder = {"low", "high"},
                                namespace = "")
                        public static class EffectiveTime {

                            @XmlElement(required = true, namespace = "urn:hl7-org:v3")
                            protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                            .Subject1.ExperimentalUnit.EffectiveTime.Low
                                    low;

                            @XmlElement(required = true, namespace = "urn:hl7-org:v3")
                            protected AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                            .Subject1.ExperimentalUnit.EffectiveTime.High
                                    high;

                            /**
                             * Gets the value of the low property.
                             *
                             * @return possible object is {@link AlertProtocolState.Study.Component1
                             *     .StudyActivitiesAtSite.Subject1
                             *     .ExperimentalUnit.EffectiveTime.Low }
                             */
                            public AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                            .Subject1.ExperimentalUnit.EffectiveTime.Low
                                    getLow() {
                                return low;
                            }

                            /**
                             * Sets the value of the low property.
                             *
                             * @param value allowed object is {@link
                             *     AlertProtocolState.Study.Component1
                             *     .StudyActivitiesAtSite.Subject1
                             *     .ExperimentalUnit.EffectiveTime.Low }
                             */
                            public void setLow(
                                    AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                                    .Subject1.ExperimentalUnit.EffectiveTime.Low
                                            value) {
                                this.low = value;
                            }

                            /**
                             * Gets the value of the high property.
                             *
                             * @return possible object is {@link AlertProtocolState.Study.Component1
                             *     .StudyActivitiesAtSite.Subject1
                             *     .ExperimentalUnit.EffectiveTime.High }
                             */
                            public AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                            .Subject1.ExperimentalUnit.EffectiveTime.High
                                    getHigh() {
                                return high;
                            }

                            /**
                             * Sets the value of the high property.
                             *
                             * @param value allowed object is {@link
                             *     AlertProtocolState.Study.Component1
                             *     .StudyActivitiesAtSite.Subject1
                             *     .ExperimentalUnit.EffectiveTime.High }
                             */
                            public void setHigh(
                                    AlertProtocolState.Study.Component1.StudyActivitiesAtSite
                                                    .Subject1.ExperimentalUnit.EffectiveTime.High
                                            value) {
                                this.high = value;
                            }

                            /**
                             * Java class for anonymous complex type.
                             *
                             * <p>The following schema fragment specifies the expected content
                             * contained within this class.
                             *
                             * <pre>
                             * &lt;complexType&gt;
                             *   &lt;complexContent&gt;
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                             *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                             *     &lt;/restriction&gt;
                             *   &lt;/complexContent&gt;
                             * &lt;/complexType&gt;
                             * </pre>
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", namespace = "urn:ihe:qrph:rpe:2009")
                            public static class High {

                                @XmlAttribute(name = "value")
                                @XmlSchemaType(name = "anySimpleType")
                                protected String value;

                                /**
                                 * Gets the value of the value property.
                                 *
                                 * @return possible object is {@link String }
                                 */
                                public String getValue() {
                                    return value;
                                }

                                /**
                                 * Sets the value of the value property.
                                 *
                                 * @param value allowed object is {@link String }
                                 */
                                public void setValue(String value) {
                                    this.value = value;
                                }
                            }

                            /**
                             * Java class for anonymous complex type.
                             *
                             * <p>The following schema fragment specifies the expected content
                             * contained within this class.
                             *
                             * <pre>
                             * &lt;complexType&gt;
                             *   &lt;complexContent&gt;
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                             *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                             *     &lt;/restriction&gt;
                             *   &lt;/complexContent&gt;
                             * &lt;/complexType&gt;
                             * </pre>
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "")
                            public static class Low {

                                @XmlAttribute(name = "value")
                                @XmlSchemaType(name = "anySimpleType")
                                protected String value;

                                /**
                                 * Gets the value of the value property.
                                 *
                                 * @return possible object is {@link String }
                                 */
                                public String getValue() {
                                    return value;
                                }

                                /**
                                 * Sets the value of the value property.
                                 *
                                 * @param value allowed object is {@link String }
                                 */
                                public void setValue(String value) {
                                    this.value = value;
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this
         * class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="plannedStudy"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="id"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                           &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(
                name = "",
                propOrder = {"plannedStudy"})
        public static class Instantiation {

            @XmlElement(required = true, namespace = "urn:hl7-org:v3")
            protected AlertProtocolState.Study.Instantiation.PlannedStudy plannedStudy;

            /**
             * Gets the value of the plannedStudy property.
             *
             * @return possible object is {@link AlertProtocolState.Study.Instantiation.PlannedStudy
             *     }
             */
            public AlertProtocolState.Study.Instantiation.PlannedStudy getPlannedStudy() {
                return plannedStudy;
            }

            /**
             * Sets the value of the plannedStudy property.
             *
             * @param value allowed object is {@link
             *     AlertProtocolState.Study.Instantiation.PlannedStudy }
             */
            public void setPlannedStudy(AlertProtocolState.Study.Instantiation.PlannedStudy value) {
                this.plannedStudy = value;
            }

            /**
             * Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this
             * class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="id"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *                 &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(
                    name = "",
                    propOrder = {"id"})
            public static class PlannedStudy {

                @XmlElement(required = true, namespace = "urn:hl7-org:v3")
                protected AlertProtocolState.Study.Instantiation.PlannedStudy.Id id;

                /**
                 * Gets the value of the id property.
                 *
                 * @return possible object is {@link
                 *     AlertProtocolState.Study.Instantiation.PlannedStudy.Id }
                 */
                public AlertProtocolState.Study.Instantiation.PlannedStudy.Id getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 *
                 * @param value allowed object is {@link
                 *     AlertProtocolState.Study.Instantiation.PlannedStudy.Id }
                 */
                public void setId(AlertProtocolState.Study.Instantiation.PlannedStudy.Id value) {
                    this.id = value;
                }

                /**
                 * Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within
                 * this class.
                 *
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Id {

                    @XmlAttribute(name = "root")
                    @XmlSchemaType(name = "anySimpleType")
                    protected String root;

                    @XmlAttribute(name = "extension")
                    @XmlSchemaType(name = "anySimpleType")
                    protected String extension;

                    /**
                     * Gets the value of the root property.
                     *
                     * @return possible object is {@link String }
                     */
                    public String getRoot() {
                        return root;
                    }

                    /**
                     * Sets the value of the root property.
                     *
                     * @param value allowed object is {@link String }
                     */
                    public void setRoot(String value) {
                        this.root = value;
                    }

                    /**
                     * Gets the value of the extension property.
                     *
                     * @return possible object is {@link String }
                     */
                    public String getExtension() {
                        return extension;
                    }

                    /**
                     * Sets the value of the extension property.
                     *
                     * @param value allowed object is {@link String }
                     */
                    public void setExtension(String value) {
                        this.extension = value;
                    }
                }
            }
        }
    }
}
