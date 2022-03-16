/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Kristina
 */
public class DBSession {
    
    public static Session openSession() {        
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        return s;
        
    }

   public static void closeSession(Session s) {    
        s.getTransaction().commit();
        s.close();
    }
    
}
