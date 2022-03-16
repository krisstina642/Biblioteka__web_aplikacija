/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Kristina
 */
@Entity(name = "komentaridogadjaja")
public class Komentaridogadjaja {
    
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ko")
    private String ko;

    @Column(name = "idDesavanja")
    private Integer idDesavanja;
    
    @Column(name = "komentar")
    private String komentar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public Integer getIdDesavanja() {
        return idDesavanja;
    }

    public void setIdDesavanja(Integer idDesavanja) {
        this.idDesavanja = idDesavanja;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
    
}
