/**
 * 
 */
package org.ldv.sio.getap.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.ldv.sio.getap.AccPersonalise;
import org.ldv.sio.getap.Classe;
import org.ldv.sio.getap.DVCTAPException;
import org.ldv.sio.getap.DemandeValidationConsoTempsAccPers;
import org.ldv.sio.getap.User;

/**
 * @author greg
 * 
 */
public class DemandeValidationConsoTempsAccPersTest {
	private DemandeValidationConsoTempsAccPers dvctap;

	@Before
	public void setUp() throws Exception {

		try {
			Classe c = new Classe(1, "SIO 22");
			User prof = new User((long) 2, "Olivier", "Capuozzo", null, "prof",
					"okpu", "okpu", "okpu@okpu.org");
			User eleve = new User((long) 1, "Nizar", "Ben Ragdel", c, "eleve",
					"nbenragdel", "nizar", "nizar@vinci.org");
			AccPersonalise accPers = new AccPersonalise();
			accPers.setId(1);
			accPers.setIdEleve(eleve.getId().intValue());
			accPers.setIdUser(prof.getId());
			accPers.setNom("Salon du libre");
			this.dvctap = new DemandeValidationConsoTempsAccPers();
			String date = "2012-10-07";
			java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
			dvctap.setId((long) 1);
			dvctap.setEleve(eleve);
			dvctap.setProf(prof);
			dvctap.setAccPers(accPers);
			dvctap.setDateAction(javaSqlDate);
			dvctap.setAnneeScolaire("2012-2013");
			dvctap.setMinutes(240);
			
			
		} catch (Exception e) {
			fail("Création de l'OUT impossible !");
		}
	}

	@Test
	public void testEtatInitial() {
		dvctap.setCreatedByEleve();
		assertTrue("Etat: initial", dvctap.isEtatInitial());
	}

	@Test
	public void testUpdateByEleveAfterProfUpdate() {
	try {
		dvctap.setCreatedByEleve();
		dvctap.setDateUpdatedByProf();
		dvctap.setDureeUpdatedByProf();
		dvctap.setAPUpdatedByProf();
		dvctap.setUpdateByEleve();		
		assertFalse("Etat: modifié par l'eleve apres modif prof", dvctap.isUpdateByEleve());
	} catch (DVCTAPException e) {
			System.out.println(e);
	}
	}
	
	
	
	@Test
	public void testUpdateByEleveAfterCancelByEleve() {		
		try {
			dvctap.setCreatedByEleve();
			dvctap.setCancelledByEleve();
			dvctap.setUpdateByEleve();
			assertFalse("Etat: modifié par l'eleve apres annul eleve", dvctap.isUpdateByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}			
		
	}
	
	@Test
	public void testUpdateByEleveAfterProfValidation() {		
		try {
			dvctap.setCreatedByEleve();
			dvctap.setValidatedByProf();
			dvctap.setUpdateByEleve();
			assertFalse("Etat: modifié par l'eleve apres valid prof", dvctap.isUpdateByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}			
		
	}
	
	@Test
	public void testUpdateByEleve() {		
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			assertTrue("Etat: modifié par l'eleve", dvctap.isUpdateByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}			
		
	}
	
	@Test
	public void testUpdateByEleveAfterMultipleEleveUpdate() {		
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			dvctap.setUpdateByEleve();
			dvctap.setUpdateByEleve();
			dvctap.setUpdateByEleve();
			assertTrue("Etat: modifié par l'eleve", dvctap.isUpdateByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}			
		
	}
	
	@Test
	public void testCancelledByEleveAfterProfUpdating() {
		
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			dvctap.setDateUpdatedByProf();
			dvctap.setDureeUpdatedByProf();
			dvctap.setAPUpdatedByProf();
			dvctap.setCancelledByEleve();
			assertFalse("Etat: annulée par l'eleve", dvctap.isCancelledByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}
	
	@Test
	public void testCancelledByEleveAfterProfValidating() {
		
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setValidatedByProf();
			dvctap.setCancelledByEleve();
			assertFalse("Etat: annulée par l'eleve", dvctap.isCancelledByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}

	@Test
	public void testCancelledByEleve() {
		
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			dvctap.setCancelledByEleve();
			assertTrue("Etat: annulée par l'eleve", dvctap.isCancelledByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}

	@Test
	public void testCancelledByProfAfterValidation() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			dvctap.setValidatedByProf();
			dvctap.setRefusedByProf();
			assertFalse("Etat: refusée par le prof", dvctap.isRefusedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}

	@Test
	public void testCancelledByProf() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			dvctap.setRefusedByProf();
			assertTrue("Etat: refusée par le prof", dvctap.isRefusedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}
	
	
	

	@Test
	public void testValidatedByProf() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setUpdateByEleve();
			dvctap.setValidatedByProf();
			assertTrue("Etat: validée par le prof", dvctap.isValidatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	@Test
	public void testValidatedByProfAfterCancelByEleve() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setCancelledByEleve();
			dvctap.setValidatedByProf();
			assertFalse("Etat: validée par le prof", dvctap.isValidatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	@Test
	public void testValidatedByProfAfterProfUpdate() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setDateUpdatedByProf();			
			dvctap.setValidatedByProf();
			assertFalse("Etat: validée par le prof", dvctap.isValidatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	

	@Test
	public void testDateUpdatedByProf() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setDateUpdatedByProf();				
			assertTrue("Etat: maj date par le prof", dvctap.isDateUpdatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	@Test
	public void testDateUpdatedByProfAfterValidatingProf() {
		try {
			dvctap.setCreatedByEleve();	
			dvctap.setValidatedByProf();
			dvctap.setDateUpdatedByProf();				
			assertFalse("Etat: maj date par le prof", dvctap.isDateUpdatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	@Test
	public void testDureeUpdatedByProf() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setDureeUpdatedByProf();				
			assertTrue("Etat: maj duree par le prof", dvctap.isDureeUpdatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	@Test
	public void testDureeUpdatedByProfAfterValidatingProf() {
		try {
			dvctap.setCreatedByEleve();	
			dvctap.setValidatedByProf();
			dvctap.setDureeUpdatedByProf();				
			assertFalse("Etat: maj duree par le prof", dvctap.isDureeUpdatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}
	
	@Test
	public void testAPUpdatedByProf() {
		try {
			dvctap.setCreatedByEleve();			
			dvctap.setAPUpdatedByProf();				
			assertTrue("Etat: maj AP par le prof", dvctap.isAPUpdatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}

	@Test
	public void testAPUpdatedByProfAfterValidatingProf() {
		try {
			dvctap.setCreatedByEleve();	
			dvctap.setValidatedByProf();
			dvctap.setAPUpdatedByProf();				
			assertFalse("Etat: maj AP par le prof", dvctap.isAPUpdatedByProf());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
	}


	@Test
	public void testRefusedByEleveAfterUpdatingProf() {
		
		try {
			dvctap.setCreatedByEleve();	
			dvctap.setDateUpdatedByProf();
			dvctap.setRefusedByEleve();						
			assertTrue("Etat:refusée par eleve", dvctap.isRefusedByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}
	
	@Test
	public void testRefusedByEleveWithoutUpdatingProf() {
		
		try {
			dvctap.setCreatedByEleve();				
			dvctap.setRefusedByEleve();						
			assertFalse("Etat:refusée par eleve", dvctap.isRefusedByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}
	
	@Test
	public void testValidateByEleveAfterUpdatingProf() {
		
		try {
			dvctap.setCreatedByEleve();	
			dvctap.setDateUpdatedByProf();
			dvctap.setValidatedByEleve();						
			assertTrue("Etat:refusée par eleve", dvctap.isValidatedByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}
	
	@Test
	public void testValidateByEleveWithoutUpdatingProf() {
		
		try {
			dvctap.setCreatedByEleve();				
			dvctap.setValidatedByEleve();						
			assertFalse("Etat:refusée par eleve", dvctap.isValidatedByEleve());
		} catch (DVCTAPException e) {
				System.out.println(e);
		}	
		
	}




}
