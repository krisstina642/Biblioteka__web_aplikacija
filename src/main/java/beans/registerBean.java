
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Knjiga;
import entities.Korisnik;
import hibernate.book;
import hibernate.login;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;


/**
 *
 * @author Kristina
 */
@Named
@RequestScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class registerBean {

    private Korisnik k;
    private UploadedFile image;
    private UploadedFile imageKnjiga;
    
    

    public UploadedFile getImageKnjiga() {
        return imageKnjiga;
    }

    public void setImageKnjiga(UploadedFile imageKnjiga) {
        this.imageKnjiga = imageKnjiga;
    }
    private String password;
    private java.util.Date date = new Date();
    private ArrayList<String> zanrovi;
    private Knjiga novaknjiga;
    private String autori;

    public ArrayList<String> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(ArrayList<String> zanrovi) {
        this.zanrovi = zanrovi;
    }

    public Knjiga getNovaknjiga() {
        return novaknjiga;
    }

    public void setNovaknjiga(Knjiga novaknjiga) {
        this.novaknjiga = novaknjiga;
    }

    public String getAutori() {
        return autori;
    }

    public void setAutori(String autori) {
        this.autori = autori;
    }

    public void dodajKnjigu(boolean admin) throws IOException {
        if (autori.equals("") || autori == null || novaknjiga.getNaziv().equals("") || zanrovi.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska!",
                    "Niste uneli sve podatke"));
            return;
        }
        String[] tokens = autori.split("\n");
        List<String> list = Arrays.asList(tokens);
        novaknjiga.setStatus((admin) ? "odobrena" : "na cekanju");
        novaknjiga.setOcena(0);
        Date dd = new Date(System.currentTimeMillis());
        java.sql.Date date2 = new java.sql.Date(dd.getTime());
        novaknjiga.setDatum(date2);

        savefile2();
        book.dodajKnjigu(novaknjiga, zanrovi, list);
        FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Ok!",
                "Knjiga je dodata"));
        novaknjiga = new Knjiga();
        autori = null;
        zanrovi=new ArrayList<>();
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public registerBean() {
        zanrovi = new ArrayList<>();
        novaknjiga = new Knjiga();
        k = new Korisnik();
        date = new java.util.Date();
        k.setTip("korisnik");
        k.setSlika("anonymous.jpg");
        novaknjiga.setSlika("book.jpg");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Korisnik getK() {
        return k;
    }

    public void setK(Korisnik k) {
        this.k = k;
    }

    public String register(boolean b) throws IOException {

        if (k.getDrzava().equals("") || k.getEmail().equals("") || k.getGrad().equals("") || k.getIme().equals("") || k.getLozinka().equals("")
                || k.getPrezime().equals("") || k.getUsername().equals("")) {
            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska",
                    "Popunite sva polja!"));
            return "register";
        }

        if (login.user(k.getUsername()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska",
                    "Korisnicko ime vec postoji!"));
            return "register";
        }
        if (check() == false) {
            return "register";
        }

        java.sql.Date date2 = new java.sql.Date(date.getTime());

        k.setDatumrodjenja(date2);

        if (login.checkEmail(k.getEmail()) == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska",
                    "Nalog sa e-mailom vec postoji!"));
            return "register";
        }
        savefile();
        if(b) k.setPotvrda("potvrdjen");              
        else k.setPotvrda("zahtev");

        if (login.register(k)) {
          if(b){
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Uspesno Registrovan",
                    "Novi korisnik je dodat!"));
            return "korisnik";
          }
          
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Uspesno Registrovan",
                    "Zahtev je poslat na obradu!"));
            return "index";
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Greska!",
                "Pokusaj ponovo!"));
        return "register";

    }
    
   

    public boolean check() {
        if (!password.equals(k.getLozinka())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska",
                    "Lozinke se ne poklapaju!"));
            return false;
        }
        return true;
    }

    public void email() {
        if (!password.equals(k.getLozinka())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Greska",
                    "email pattern!"));
        }
    }
    
    

    public void savefile() throws IOException {
      
        if (image == null || image.getSize() == 0) {
            return;
        }
        System.out.println("Uploaded File Name Is :: " + image.getFileName() + " :: Uploaded File Size :: " + image.getSize());

        Path folder = Paths.get("C:\\Users\\Kristina\\Documents\\NetBeansProjects\\mavenproject3\\src\\main\\webapp\\resources\\images");
        System.out.println("path");
        String extension = FilenameUtils.getExtension(image.getFileName());
        Path file = Files.createTempFile(folder, k.getUsername(), "." + extension);

        try (InputStream input = image.getInputStream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            k.setSlika(file.toString().substring(92));
        } catch (IOException ex) {
            Logger.getLogger(registerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Uploaded file successfully saved in " + file);

    }

    public void savefile2() throws IOException {
       
        if (imageKnjiga == null || imageKnjiga.getSize() == 0) {
            return;
        }
        System.out.println("Uploaded File Name Is :: " + imageKnjiga.getFileName() + " :: Uploaded File Size :: " + imageKnjiga.getSize());

        Path folder = Paths.get("C:\\Users\\Kristina\\Documents\\NetBeansProjects\\mavenproject3\\src\\main\\webapp\\resources\\images");
        System.out.println("path");
        String extension = FilenameUtils.getExtension(imageKnjiga.getFileName());
        Path file = Files.createTempFile(folder, novaknjiga.getNaziv(), "." + extension);

        try (InputStream input = imageKnjiga.getInputStream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            novaknjiga.setSlika(file.toString().substring(92));
        } catch (IOException ex) {
            Logger.getLogger(registerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Uploaded file successfully saved in " + file);

    }

}
