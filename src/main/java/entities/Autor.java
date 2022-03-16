/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Kristina
 */
@Entity(name = "autor")
public class Autor implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "idknjige")
    private Integer idknjige;
    
    @Column(name = "ime")
    private String ime;

    public Integer getId() {
        return id;
    }

    public Autor(Integer id, Integer idknjige, String ime) {
        this.id = id;
        this.idknjige = idknjige;
        this.ime = ime;
    }

    public Autor() {}
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
    
      public Integer getIdknjige() {
        return idknjige;
    }

    public void setIdknjige(Integer idknjige) {
        this.idknjige = idknjige;
    }
    
    
}
