/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Desavanje;
import entities.Komentaridogadjaja;
import entities.Pracenje;
import entities.Ucesce;
import hibernate.event;
import hibernate.login;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Kristina
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class desavanjeBean implements Serializable {
    private Desavanje desavanje;
       private Desavanje novodesavanje;
       private int trenutno=0;
       private Date pocetak;
       private Date kraj;
       private ArrayList<Pracenje> korisnikPrati;
       private ArrayList<String> ucesce;
       private Ucesce korisnikUcestvuje;
       private boolean organizator;
       private ArrayList<Komentaridogadjaja> komentari;
       private String novikomentar;

    public String getNovikomentar() {
        return novikomentar;
    }

    public void setNovikomentar(String novikomentar) {
        this.novikomentar = novikomentar;
    }

    public ArrayList<Komentaridogadjaja> getKomentari() {
        return komentari;
    }

    public void setKomentari(ArrayList<Komentaridogadjaja> komentari) {
        this.komentari = komentari;
    }

    public boolean isOrganizator() {
        return organizator;
    }

    public void setOrganizator(boolean organizator) {
        this.organizator = organizator;
    }

    public Ucesce getKorisnikUcestvuje() {
        return korisnikUcestvuje;
    }

    public void setKorisnikUcestvuje(Ucesce korisnikUcestvuje) {
        this.korisnikUcestvuje = korisnikUcestvuje;
    }

    public ArrayList<Pracenje> getKorisnikPrati() {
        return korisnikPrati;
    }

    public void setKorisnikPrati(ArrayList<Pracenje> korisnikPrati) {
        this.korisnikPrati = korisnikPrati;
    }

    public ArrayList<String> getUcesce() {
        return ucesce;
    }

    public void setUcesce(ArrayList<String> ucesce) {
        this.ucesce = ucesce;
    }
    
    public Date getPocetak() {
        return pocetak;
    }

    public void setPocetak(Date pocetak) {
        this.pocetak = pocetak;
    }

    public Date getKraj() {
        return kraj;
    }

    public void setKraj(Date kraj) {
        this.kraj = kraj;
    }

    public desavanjeBean() {     
    }
    
    public boolean vreme() {  
        if (pocetak.compareTo(kraj)<0) return true;
        return false;
    }
       
    public int getTrenutno() {
        return trenutno;
    }

    public void setTrenutno(int trenutno) {
        this.trenutno = trenutno;
    }
       
       public String dalje(){
           
             if (trenutno==2 && !vreme()){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Greska!",
                    "Vreme kraja mora biti nakon pocetka!"));
               return null;
       }
            
           if (trenutno<3 && util.Util.getKorisnik().getTip().equals("moderator")) { 
               trenutno=trenutno+1; 
               return null;
           }
           else if(trenutno<4 && util.Util.getKorisnik().getTip().equals("korisnik")) { 
               trenutno=trenutno+1; 
               return null;
           }
           
           novodesavanje.setDatumDo(new java.sql.Date(kraj.getTime()));
           novodesavanje.setDatumOd(new java.sql.Date(pocetak.getTime()));
           novodesavanje.setKorisnik(util.Util.getKorisnik().getUsername());
           novodesavanje.setTip(util.Util.getKorisnik().getTip().equals("moderator")? "javno":"privatno");
           novodesavanje.setZavrseno(0);
           Desavanje d =event.dodajDesavanje(novodesavanje);  
           if (util.Util.getKorisnik().getTip().equals("korisnik")){
               ucesce.forEach((t)->{
               Ucesce u=new Ucesce();
               u.setIdDesavanja(d.getId());
               u.setKo(t);
               u.setZahtev("prihvacen");
               event.dodajUcesce(u);
               });             
           }   
            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Ok!",
                    "Novo Desavanje je kreirano!"));
        
           return "korisnik";        
       }
       
       public String otvorinovodesavanje(){        
            trenutno=0;
            pocetak =new Date();
            kraj=new Date();
            novodesavanje=new Desavanje();
            korisnikPrati=login.pratim(util.Util.getKorisnik());
            ucesce=new ArrayList<>();
            return "novodesavanje";
        }
       
       public boolean zavrseno(){
           java.util.Date d=new Date();
           java.util.Date d2=new java.util.Date(desavanje.getDatumDo().getTime());
           return (d2.compareTo(d)<0 || desavanje.getZavrseno()==1);
       }
       
       public void zatvori(){
           java.util.Date d=new Date();
           java.sql.Date date2 = new java.sql.Date(d.getTime());
           desavanje.setDatumDo(date2);
           desavanje.setZavrseno(1);
           event.azurirajDesavanje(desavanje);
       }
       public void pokreni() throws ParseException{
           java.util.Date d=new Date();
           java.sql.Date date1 = new java.sql.Date(d.getTime());
           desavanje.setDatumOd(date1);
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date d2 = sdf.parse("3000-01-01");
           java.sql.Date date2 = new java.sql.Date(d2.getTime());
           desavanje.setDatumDo(date2);
           desavanje.setZavrseno(0);
           event.azurirajDesavanje(desavanje);
       }
            

    public Desavanje getNovodesavanje() {
        return novodesavanje;
    }

    public void setNovodesavanje(Desavanje novodesavanje) {
        this.novodesavanje = novodesavanje;
    }
    
    public String otvori(Desavanje d){   
        desavanje=d;
        korisnikUcestvuje=event.nadjiUcesce(d,util.Util.getKorisnik());
        organizator= (desavanje.getKorisnik().equals(util.Util.getKorisnik().getUsername()));
        komentari=event.komentaridogadjaja(d);
        novikomentar="";
        return "desavanje";
        
    }
    
    public void dodajKomentar(){
        if(novikomentar.equals("")) { 
          FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Greska!",
                    "Morate napisati nesto!"));
          return;
      }
        Komentaridogadjaja k= new Komentaridogadjaja();
        k.setIdDesavanja(desavanje.getId());
        k.setKo(util.Util.getKorisnik().getUsername());
        k.setKomentar(novikomentar);
        komentari.add(k);
        event.dodajKomentarDogadjaja(k);
    }
    
    public void novoUcesce(String s){   
        Ucesce u=new Ucesce();
        u.setKo(util.Util.getKorisnik().getUsername());
        u.setIdDesavanja(desavanje.getId());
        u.setZahtev(s);
        event.dodajUcesce(u);
        korisnikUcestvuje=u;
    }
    
    public Desavanje getDesavanje() {
        return desavanje;
    }

    public void setDesavanje(Desavanje desavanje) {
        this.desavanje = desavanje;
    }

}
