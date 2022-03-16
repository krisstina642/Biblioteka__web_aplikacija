/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import entities.Desavanje;
import entities.Komentaridogadjaja;
import entities.Korisnik;
import entities.Ucesce;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Kristina
 */
public class event {

    public static ArrayList<Desavanje> getDesavanja() {

        Session s=DBSession.openSession();

        ArrayList<Desavanje> a = (ArrayList<Desavanje>) s.createCriteria(Desavanje.class).list();

        DBSession.closeSession(s);
        return a;

    }
    
    public static ArrayList<Komentaridogadjaja> komentaridogadjaja(Desavanje d) {

        Session s=DBSession.openSession();

        ArrayList<Komentaridogadjaja> a = (ArrayList<Komentaridogadjaja>) s.createCriteria(Komentaridogadjaja.class).add(Restrictions.eq("idDesavanja", d.getId())).list();

        DBSession.closeSession(s);
        return a;

    }
    
    public static void dodajKomentarDogadjaja(Komentaridogadjaja k){
        Session s=DBSession.openSession();
        int i=1;
          
           if((Komentaridogadjaja)s.createSQLQuery("SELECT * FROM komentaridogadjaja WHERE id = 1").setResultTransformer(Transformers.aliasToBean(Komentaridogadjaja.class)).uniqueResult()!=null)
           { BigInteger min = (BigInteger)s.createSQLQuery("SELECT * FROM ( SELECT MIN(t1.id)+1 as unused FROM komentaridogadjaja AS t1 WHERE NOT EXISTS (SELECT * FROM komentaridogadjaja AS t2 WHERE t2.id = t1.id+1)) AS subquery").uniqueResult();
            i=min.intValue();
           }         
           k.setId(i);
        s.save(k);

        DBSession.closeSession(s);
    }
    
    public static Desavanje dodajDesavanje(Desavanje d) {

        Session s=DBSession.openSession();
        int i=1;
          
           if((Desavanje)s.createSQLQuery("SELECT * FROM desavanje WHERE id = 1").setResultTransformer(Transformers.aliasToBean(Desavanje.class)).uniqueResult()!=null)
           { BigInteger min = (BigInteger)s.createSQLQuery("SELECT * FROM ( SELECT MIN(t1.id)+1 as unused FROM desavanje AS t1 WHERE NOT EXISTS (SELECT * FROM desavanje AS t2 WHERE t2.id = t1.id+1)) AS subquery").uniqueResult();
            i=min.intValue();
           }         
           d.setId(i);
        s.save(d);

        DBSession.closeSession(s);
        return d;
        
    }
    
    public static void dodajUcesce(Ucesce u) {

        Session s=DBSession.openSession();
        int i=1;
          
           if((Ucesce)s.createSQLQuery("SELECT * FROM ucesce WHERE id = 1").setResultTransformer(Transformers.aliasToBean(Ucesce.class)).uniqueResult()!=null)
           { BigInteger min = (BigInteger)s.createSQLQuery("SELECT * FROM ( SELECT MIN(t1.id)+1 as unused FROM ucesce AS t1 WHERE NOT EXISTS (SELECT * FROM ucesce AS t2 WHERE t2.id = t1.id+1)) AS subquery").uniqueResult();
            i=min.intValue();
           }         
           u.setId(i);
        s.save(u);

        DBSession.closeSession(s);
        
    }
    
    public static Ucesce nadjiUcesce(Desavanje d, Korisnik k) {

        Session s=DBSession.openSession();     
          Ucesce u=(Ucesce) s.createCriteria(Ucesce.class).add(Restrictions.eq("idDesavanja", d.getId())).add(Restrictions.eq("ko", k.getUsername())).uniqueResult();
        DBSession.closeSession(s);
        return u;
        
    }
    
    public static void azurirajDesavanje(Desavanje d) {

        Session s=DBSession.openSession();     
          s.update(d);
        DBSession.closeSession(s);
            
    }
    
     public static void azurirajUcesce(Ucesce u) {

        Session s=DBSession.openSession();     
          s.update(u);
        DBSession.closeSession(s);
    
    }
     
     public static void obrisiUcesce(Ucesce u) {

        Session s=DBSession.openSession();     
          s.delete(u);
        DBSession.closeSession(s);
    
    }
    
     public static HashMap<Ucesce,String> ucescaKorisnikaSaImenimaUcesnika(Korisnik k) {

        Session s=DBSession.openSession();     
        HashMap<Ucesce,String> mapa=new HashMap<>();
        ArrayList<Desavanje> d= (ArrayList<Desavanje>) s.createCriteria(Desavanje.class).add(Restrictions.eq("korisnik", k.getUsername())).list();
        
        d.forEach((t)->{
            ArrayList<Ucesce> u=(ArrayList<Ucesce>) s.createCriteria(Ucesce.class).add(Restrictions.eq("idDesavanja", t.getId())).add(Restrictions.eq("zahtev", "prati")).list();
             u.forEach((i)->{      
            mapa.put(i, t.getNaziv());
        });       
        });
        
        DBSession.closeSession(s);
        return mapa;            
    }
    
    
}
