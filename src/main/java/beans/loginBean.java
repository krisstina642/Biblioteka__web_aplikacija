/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Korisnik;
import hibernate.login;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import util.SendEmail;
import util.Util;

/**
 *
 * @author Kristina
 */
@Named
@RequestScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class loginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String password;
    private String uname;
    private String new1;
    private String new2;
    private String statusMessage;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
     public String zaboravljenalozinka(){
         if (uname==null || uname.equals("")){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Niste uneli korisnicko ime!"));
             return null;
     }
          
         Korisnik result = login.user(uname);
         if (result==null){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Korisnik ne postoji!"));
             return null;
         }
         SendEmail.main(result.getEmail(),result.getLozinka()); 
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Ok!",
                    "Lozinka je poslata na e-mail adresu!"));           
        return "index";
    }

    public String getNew1() {
        return new1;
    }

    public void setNew1(String new1) {
        this.new1 = new1;
    }

    public String getNew2() {
        return new2;
    }

    public void setNew2(String new2) {
        this.new2 = new2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String login() {
        if (uname == null || uname.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Unesi korisnicko ime!"));
            return "index";
        }
        if (password == null || password.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Unesi lozinku!"));
            return "index";
        }

        Korisnik result = login.user(uname);

        if (result == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Korisnik ne postoji!"));
            return "index";
        }

        if (!result.getLozinka().equals(password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Pogresna Lozinka!"));
            return "index";
        }

        if (result.getPotvrda().equals("potvrdjen") == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Obavestenje!",
                    "Nalog je i dalje u fazi obrade!"));
            return "index";
        }

        if (result.getTip().equals("korisnik") || result.getTip().equals("admin") || result.getTip().equals("moderator")) {
            util.Util.setKorisnik(result);          
            return "korisnik";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Pokusaj ponovo!"));

            // invalidate session, and redirect to other pages
            //message = "Greska. Please Try Again!";
            return "index";
        }
    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "index";
    }

    public String promenalozinke() {
        if (uname == null || uname.equals("") || password == null || password.equals("") || new1 == null || new1.equals("")
                || new2 == null || new2.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Sva polja moraju biti popunjena!"));
            return "lozinka";
        }

        if (!new2.equals(new1)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Lozinke se ne poklapaju!"));
            return "lozinka";
        }
        if (password.equals(new1)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Prethodna i nova lozinka se poklapaju!"));
            return "lozinka";
        }

        Korisnik result = login.user(uname);
        if (result == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Korisnik ne postoji!"));
            return "lozinka";
        }

        if (!result.getLozinka().equals(password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Greska!",
                    "Pogresna Lozinka!"));
            return "lozinka";
        }

        result.setLozinka(new1);
        login.change(result);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Success!",
                "Uspesno promenjena lozinka!"));
        logout();

        return "index";

    }

}
