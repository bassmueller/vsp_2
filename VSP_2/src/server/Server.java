/**
 * 
 */
package server;


import lagern.Lager;
import lagern.LagerHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;



/**
 * 
 * Verteilte Systeme Praktikum: "Aufgabe 1: Lager"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * Server Anwendung.
 * -----------------
 * 
 * server -ORBInitialHost <IP> -ORBInitialPort <Port> Lager
 * 
 * 
 * @author Sebastian Mueller 2008588, Martin Schindler 2022759
 *
 */
public class Server {
	
	public static void main(String args[]){
			
		try {
			//Properties props = new Properties();
			//props.put("org.omg.CORBA.ORBInitialPort", "1049");
			//props.put("org.omg.CORBA.ORBInitialHost", "localhost");
			ORB orb = ORB.init(args, null);
			System.out.println("Server: ORB initialisiert");
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			System.out.println("Server: RootPOA aktiviert");
			
			LagerImpl servant = new LagerImpl(orb);
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
			Lager href = LagerHelper.narrow(ref);
			System.out.println("Server: Lager-Object referenziert");
			
			org.omg.CORBA.Object objref = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objref);
			servant.setNcRef(ncRef);
			System.out.println("Server: NameService erhalten");
			
			String name = args[4];
			NameComponent path[] = ncRef.to_name(name);
			servant.setPath(path);
			ncRef.rebind(path, href);
			System.out.println("Server: Lager-Object im NameService registriert");
			
			orb.run();
		} catch (InvalidName e) {
			System.err.println("Server: ERROR InvalidName");
		} catch (AdapterInactive e) {
			System.err.println("Server: ERROR AdapterInteractive");
		} catch (ServantNotActive e) {
			System.err.println("Server: ERROR ServantNotActive");
		} catch (WrongPolicy e) {
			System.err.println("Server: ERROR WrongPolicy");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			System.err.println("Server: ERROR InvalidName");
		} catch (NotFound e) {
			System.err.println("Server: ERROR NotFound");
		} catch (CannotProceed e) {
			System.err.println("Server: ERROR CannotProceed");
		}//try
		
	}//main

}//Server
