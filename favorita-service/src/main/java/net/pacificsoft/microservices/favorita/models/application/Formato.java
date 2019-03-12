package net.pacificsoft.springbootcrudrest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table (name = "formato")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Formato implements Serializable{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        @Column(name = "name", nullable = false)
	private String name;
        @Column(name = "code", nullable = false)
        private String code;

        @JsonIgnore
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "localID")
        private Locales locales;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Locales getLocales() {
            return locales;
        }

        public void setLocales(Locales locales) {
            this.locales = locales;
        }

        @Override
        public String toString(){
            return "Formato: "+id+" "+name+" "+code;
        }
}
