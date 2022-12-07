//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.3.0によって生成されました 
// <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2022.12.07 時間 02:10:44 PM JST 
//


package io.helidon.archetypes.tests.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>anonymous complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="persistence-unit" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="non-jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="mapping-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="jar-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="class" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="exclude-unlisted-classes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                   &lt;element name="shared-cache-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-caching-type" minOccurs="0"/&gt;
 *                   &lt;element name="validation-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-validation-mode-type" minOccurs="0"/&gt;
 *                   &lt;element name="properties" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="property" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="transaction-type" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-transaction-type" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" use="required" type="{http://xmlns.jcp.org/xml/ns/persistence}versionType" fixed="2.2" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "persistenceUnit"
})
@XmlRootElement(name = "persistence")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
public class Persistence {

    @XmlElement(name = "persistence-unit", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
    protected List<Persistence.PersistenceUnit> persistenceUnit;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
    protected String version;

    /**
     * Gets the value of the persistenceUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the persistenceUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersistenceUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Persistence.PersistenceUnit }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
    public List<Persistence.PersistenceUnit> getPersistenceUnit() {
        if (persistenceUnit == null) {
            persistenceUnit = new ArrayList<Persistence.PersistenceUnit>();
        }
        return this.persistenceUnit;
    }

    /**
     * versionプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
    public String getVersion() {
        if (version == null) {
            return "2.2";
        } else {
            return version;
        }
    }

    /**
     * versionプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
    public void setVersion(String value) {
        this.version = value;
    }


    /**
     * 
     * 
     *                 Configuration of a persistence unit.
     * 
     *               
     * 
     * <p>anonymous complex typeのJavaクラス。
     * 
     * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="non-jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="mapping-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="jar-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="class" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="exclude-unlisted-classes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *         &lt;element name="shared-cache-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-caching-type" minOccurs="0"/&gt;
     *         &lt;element name="validation-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-validation-mode-type" minOccurs="0"/&gt;
     *         &lt;element name="properties" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="property" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="transaction-type" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-transaction-type" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "description",
        "provider",
        "jtaDataSource",
        "nonJtaDataSource",
        "mappingFile",
        "jarFile",
        "clazz",
        "excludeUnlistedClasses",
        "sharedCacheMode",
        "validationMode",
        "properties"
    })
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
    public static class PersistenceUnit {

        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected String description;
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected String provider;
        @XmlElement(name = "jta-data-source")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected String jtaDataSource;
        @XmlElement(name = "non-jta-data-source")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected String nonJtaDataSource;
        @XmlElement(name = "mapping-file")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected List<String> mappingFile;
        @XmlElement(name = "jar-file")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected List<String> jarFile;
        @XmlElement(name = "class")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected List<String> clazz;
        @XmlElement(name = "exclude-unlisted-classes", defaultValue = "true")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected Boolean excludeUnlistedClasses;
        @XmlElement(name = "shared-cache-mode")
        @XmlSchemaType(name = "token")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected PersistenceUnitCachingType sharedCacheMode;
        @XmlElement(name = "validation-mode")
        @XmlSchemaType(name = "token")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected PersistenceUnitValidationModeType validationMode;
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected Persistence.PersistenceUnit.Properties properties;
        @XmlAttribute(name = "name", required = true)
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected String name;
        @XmlAttribute(name = "transaction-type")
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        protected PersistenceUnitTransactionType transactionType;

        /**
         * descriptionプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public String getDescription() {
            return description;
        }

        /**
         * descriptionプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * providerプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public String getProvider() {
            return provider;
        }

        /**
         * providerプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setProvider(String value) {
            this.provider = value;
        }

        /**
         * jtaDataSourceプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public String getJtaDataSource() {
            return jtaDataSource;
        }

        /**
         * jtaDataSourceプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setJtaDataSource(String value) {
            this.jtaDataSource = value;
        }

        /**
         * nonJtaDataSourceプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public String getNonJtaDataSource() {
            return nonJtaDataSource;
        }

        /**
         * nonJtaDataSourceプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setNonJtaDataSource(String value) {
            this.nonJtaDataSource = value;
        }

        /**
         * Gets the value of the mappingFile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mappingFile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMappingFile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public List<String> getMappingFile() {
            if (mappingFile == null) {
                mappingFile = new ArrayList<String>();
            }
            return this.mappingFile;
        }

        /**
         * Gets the value of the jarFile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the jarFile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getJarFile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public List<String> getJarFile() {
            if (jarFile == null) {
                jarFile = new ArrayList<String>();
            }
            return this.jarFile;
        }

        /**
         * Gets the value of the clazz property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the clazz property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClazz().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public List<String> getClazz() {
            if (clazz == null) {
                clazz = new ArrayList<String>();
            }
            return this.clazz;
        }

        /**
         * excludeUnlistedClassesプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public Boolean isExcludeUnlistedClasses() {
            return excludeUnlistedClasses;
        }

        /**
         * excludeUnlistedClassesプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setExcludeUnlistedClasses(Boolean value) {
            this.excludeUnlistedClasses = value;
        }

        /**
         * sharedCacheModeプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link PersistenceUnitCachingType }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public PersistenceUnitCachingType getSharedCacheMode() {
            return sharedCacheMode;
        }

        /**
         * sharedCacheModeプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link PersistenceUnitCachingType }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setSharedCacheMode(PersistenceUnitCachingType value) {
            this.sharedCacheMode = value;
        }

        /**
         * validationModeプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link PersistenceUnitValidationModeType }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public PersistenceUnitValidationModeType getValidationMode() {
            return validationMode;
        }

        /**
         * validationModeプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link PersistenceUnitValidationModeType }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setValidationMode(PersistenceUnitValidationModeType value) {
            this.validationMode = value;
        }

        /**
         * propertiesプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link Persistence.PersistenceUnit.Properties }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public Persistence.PersistenceUnit.Properties getProperties() {
            return properties;
        }

        /**
         * propertiesプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link Persistence.PersistenceUnit.Properties }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setProperties(Persistence.PersistenceUnit.Properties value) {
            this.properties = value;
        }

        /**
         * nameプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public String getName() {
            return name;
        }

        /**
         * nameプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setName(String value) {
            this.name = value;
        }

        /**
         * transactionTypeプロパティの値を取得します。
         * 
         * @return
         *     possible object is
         *     {@link PersistenceUnitTransactionType }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public PersistenceUnitTransactionType getTransactionType() {
            return transactionType;
        }

        /**
         * transactionTypeプロパティの値を設定します。
         * 
         * @param value
         *     allowed object is
         *     {@link PersistenceUnitTransactionType }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public void setTransactionType(PersistenceUnitTransactionType value) {
            this.transactionType = value;
        }


        /**
         * <p>anonymous complex typeのJavaクラス。
         * 
         * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="property" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "property"
        })
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
        public static class Properties {

            @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
            protected List<Persistence.PersistenceUnit.Properties.Property> property;

            /**
             * Gets the value of the property property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the property property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getProperty().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Persistence.PersistenceUnit.Properties.Property }
             * 
             * 
             */
            @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
            public List<Persistence.PersistenceUnit.Properties.Property> getProperty() {
                if (property == null) {
                    property = new ArrayList<Persistence.PersistenceUnit.Properties.Property>();
                }
                return this.property;
            }


            /**
             * <p>anonymous complex typeのJavaクラス。
             * 
             * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
            public static class Property {

                @XmlAttribute(name = "name", required = true)
                @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
                protected String name;
                @XmlAttribute(name = "value", required = true)
                @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
                protected String value;

                /**
                 * nameプロパティの値を取得します。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
                public String getName() {
                    return name;
                }

                /**
                 * nameプロパティの値を設定します。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * valueプロパティの値を取得します。
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
                public String getValue() {
                    return value;
                }

                /**
                 * valueプロパティの値を設定します。
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
                public void setValue(String value) {
                    this.value = value;
                }

            }

        }

    }

}
