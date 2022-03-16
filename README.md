# Biblioteka__web_aplikacija
 
 Projekat sam radila u netbeans verziji 11.2
latest-glassfish https://download.oracle.com/glassfish/4.1.1/promoted/index.html
u pitanju je 4.1.1 verzija

Prilikom testiranja projekta neophodno je promeniti putanju cuvanja fotografija na liniji 281: korisnikBean klase
  Path folder = Paths.get("C:\\Users\\Kristina\\Documents\\NetBeansProjects\\mavenproject3\\src\\main\\webapp\\resources\\images");
		kao i na 237. i 259. linijama registerBean klase

u bazi je upisana ista email adresa za sve korisnike da ne bih spamovala prave email adrese a sifre su pojednostavljene 
zbog lakseg testiranja, novi korisnici svakako treba da koriste pattern. obavestenja kada korisnik prokomentarise 
knjigu se salju njegovim pratiocima na mail

jar fajlove sam uvozila pomocu pom.xml-a
