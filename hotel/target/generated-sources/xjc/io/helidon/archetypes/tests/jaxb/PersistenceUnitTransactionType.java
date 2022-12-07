//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.3.0によって生成されました 
// <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2022.12.07 時間 02:10:44 PM JST 
//


package io.helidon.archetypes.tests.jaxb;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>persistence-unit-transaction-typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * <pre>
 * &lt;simpleType name="persistence-unit-transaction-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="JTA"/&gt;
 *     &lt;enumeration value="RESOURCE_LOCAL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "persistence-unit-transaction-type")
@XmlEnum
@Generated(value = "com.sun.tools.xjc.Driver", date = "2022-12-07T02:10:44+09:00", comments = "JAXB RI v2.3.0")
public enum PersistenceUnitTransactionType {

    JTA,
    RESOURCE_LOCAL;

    public String value() {
        return name();
    }

    public static PersistenceUnitTransactionType fromValue(String v) {
        return valueOf(v);
    }

}
