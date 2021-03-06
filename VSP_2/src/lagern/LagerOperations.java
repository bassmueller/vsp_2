package lagern;


/**
* lagern/LagerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lager.idl
* Mittwoch, 3. April 2013 16:12 Uhr MESZ
*/

public interface LagerOperations 
{

  //holt die aktuelle Fachliste, R�ckgabewert soll die Gesamtzahl der Faecher angeben
  int getFachliste (lagern.TFachlisteHolder fachliste);
  lagern.Fach neu (String name) throws lagern.LagerPackage.EAlreadyExists;
  void loeschen (String name) throws lagern.LagerPackage.ENotFound;
  lagern.Fach hole (String name) throws lagern.LagerPackage.ENotFound;
  void monitorHinzufuegen (lagern.Monitor theMonitor);
  void monitorEntfernen (lagern.Monitor theMonitor);

  //Dient zum Beenden der Lageranwendung. Sorgt dafuer, dass das Lager und alle registrierten Monitore beendet werden.
  void exit ();
} // interface LagerOperations
