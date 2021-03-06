package org.library.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "metadata")
@Data // equivalent to getter/setter/equalsTo/hashCode
@Builder // design pattern to instantiate an object (here its used in the test cases)
@NoArgsConstructor // for DB serialize/deserialize
@AllArgsConstructor
public class MetaData {

    @Id
    @Column(name = "meta_key")
    private String metaKey;

    @Column(name = "meta_value")
    private String metaValue;
}
