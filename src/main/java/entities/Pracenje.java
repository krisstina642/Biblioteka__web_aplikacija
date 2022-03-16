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
@Entity(name = "pracenje")
public class Pracenje implements Serializable{
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "kojiprati")
    private String kojiprati;

    @Column(name = "kogaprati")
    private String kogaprati;

    public Pracenje() {
    }

    public Pracenje(Integer id, String kojiprati, String kogaprati) {
        this.id = id;
        this.kojiprati = kojiprati;
        this.kogaprati = kogaprati;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKojiprati() {
        return kojiprati;
    }

    public void setKojiprati(String kojiprati) {
        this.kojiprati = kojiprati;
    }

   

    public String getKogaprati() {
        return kogaprati;
    }

    public void setKogaprati(String kogaprati) {
        this.kogaprati = kogaprati;
    }
    
}


   
