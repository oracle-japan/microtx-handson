//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.3.0によって生成されました 
// <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2022.12.07 時間 02:36:05 PM JST 
//


package io.helidon.archetypes.tests.jaxb;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>persistence-unit-caching-typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * <pre>
 * &lt;simpleType name="persistence-unit-caching-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="ALL"/&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *     &lt;enumeration value="ENABLE_SELECTIVE"/&gt;
 *     &lt;enumeration value="DISABLE_SELECTIVE"/&gt;
 *     &lt;enumeration value="UNSPECIFIED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "persistence-unit-caching-type")
@XmlEnum
@Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:36:05+09:00", comments = "JAXB RI v2.3.0")
public enum PersistenceUnitCachingType {

    ALL,
    NONE,
    ENABLE_SELECTIVE,
    DISABLE_SELECTIVE,
    UNSPECIFIED;

    public String value() {
        return name();
    }

    public static PersistenceUnitCachingType fromValue(String v) {
        return valueOf(v);
    }

}
