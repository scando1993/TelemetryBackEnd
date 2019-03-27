package net.pacificsoft.microservices.favorita.models.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "formato")
public class Formato{

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
        
        @Column(name = "name", nullable = false)
	private String name;
        
        @Column(name = "code", nullable = false)
        private String code;

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
}
