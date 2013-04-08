package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import lagern.Fach;
import lagern.FachHelper;
import lagern.FachHolder;
import lagern.LagerPOA;
import lagern.Monitor;
import lagern.TFachlisteHolder;
import lagern.LagerPackage.EAlreadyExists;
import lagern.LagerPackage.ENotFound;

public class LagerImpl extends LagerPOA {

	private Map<String, Fach> lager = new HashMap<String, Fach>();
	private List<Monitor>monitore;

	@Override
	public int getFachliste(TFachlisteHolder fachliste) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized Fach neu(String name) throws EAlreadyExists {
		Fach neuesFach = null;
		if(this.lager.containsKey(name)){
			String msg = String.format("Fach: %s  bereits vorhanden!", name);
			this.informiereMonitore(msg);
			throw new EAlreadyExists(msg);
		}else{
			LagerFachImpl lagerfach = new LagerFachImpl(name, this);
			
			org.omg.CORBA.Object ref = null;
			try{
				ref = _poa().servant_to_reference(lagerfach);
			}catch(ServantNotActive e){
				this.informiereMonitore(String.format("Servant von %s nicht aktiv!", name));
				e.printStackTrace();
			}catch (WrongPolicy e){
				this.informiereMonitore(String.format("%s  besitzt falsche Policy!", name));
				e.printStackTrace();
			}
			
			neuesFach = FachHelper.narrow(ref);
			this.lager.put(name, neuesFach);

			this.informiereMonitore(String.format("Fach: %s  erfolgreich angelegt!", name));
		}
		return neuesFach;
	}

	@Override
	public synchronized void loeschen(String name) throws ENotFound {
		if(this.lager.containsKey(name)){
			this.lager.remove(name);
			this.informiereMonitore(String.format("Fach: %s  erfolgreich entfernt!", name));
		}else{
			String msg = String.format("Fach: %s  konnte nicht entfernt werden, da das Fach im Lager nicht existiert!", name);
			this.informiereMonitore(msg);
			throw new ENotFound(msg);
		}
	}

	@Override
	public Fach hole(String name) throws ENotFound {
		Fach fach = this.lager.get(name);
		if(fach == null){
			String msg = String.format("Fach: %s konnte nicht geholt werden, da das Fach im Lager nicht existiert!", name);
			this.informiereMonitore(msg);
			throw new ENotFound(msg);
		}
		return fach;
	}

	@Override
	public void monitorHinzufuegen(Monitor theMonitor) {
		this.monitore.add(theMonitor);
		this.informiereMonitore(theMonitor + "Monitor hinzugefügt");
	}

	@Override
	public void monitorEntfernen(Monitor theMonitor) {
		this.monitore.remove(this.monitore.indexOf(theMonitor));
	}

	@Override
	public void exit() {
		while(!this.monitore.isEmpty()){
			this.monitore.get(0).exit();
			this.monitore.remove(0);
		}
	}
	
	public void informiereMonitore(String msg){
		for(Monitor monitor: monitore){
			monitor.meldung(msg);
		}
	}
}
