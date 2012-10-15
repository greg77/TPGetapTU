package org.ldv.sio.getap;

public class Example {
	
	public static void main (String[] args){
		Classe c = new Classe(1, "SIO 22");
		User prof = new User((long) 2, "Olivier", "Capuozzo", null, "prof", "okpu", "okpu", "okpu@okpu.org");
		User eleve = new User((long) 1, "Nizar", "Ben Ragdel", c, "eleve", "nbenragdel", "nizar", "nizar@vinci.org");
		AccPersonalise accPers = new AccPersonalise();
		accPers.setId(1);
		accPers.setIdEleve(eleve.getId().intValue());
		accPers.setIdUser(prof.getId());
		accPers.setNom("Salon du libre");
		DemandeValidationConsoTempsAccPers dvctap = new DemandeValidationConsoTempsAccPers();
		String date = "2012-10-07";
		java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
		dvctap.setId((long)1);
		dvctap.setEleve(eleve);
		dvctap.setProf(prof);
		dvctap.setAccPers(accPers);
		dvctap.setDateAction(javaSqlDate);
		dvctap.setAnneeScolaire("2012-2013");
		dvctap.setMinutes(240);
		dvctap.setEtat(0);
		System.out.println("etat 0 (crée par l'eleve): "+dvctap.toString());
		dvctap.setEtat(4);
		System.out.println("etat 4 ( modifiée par l'eleve): "+dvctap.toString());
		dvctap.setEtat(32);
		System.out.println("etat 32 ( validée par le professeur): "+dvctap.toString());
		dvctap.setEtat(4);
		System.out.println("etat 4 ( modifiée par l'eleve): "+dvctap.toString());
		dvctap.setEtat(2048);
		System.out.println("etat 2048 ( durée modifiée par le prof): "+dvctap.toString());
		dvctap.setEtat(2);
		System.out.println("etat 2 ( rejetée par l'eleve): "+dvctap.toString());
		dvctap.setEtat(32);
		System.out.println("etat 32 (validée  par le prof): "+dvctap.toString());
	
	}

}
