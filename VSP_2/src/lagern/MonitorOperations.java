package lagern;


/**
* lagern/MonitorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lager.idl
* Mittwoch, 3. April 2013 16:12 Uhr MESZ
*/

public interface MonitorOperations 
{
  void meldung (String msg);

  //Damit kann das Lager den Monitor beenden.
  void exit ();
} // interface MonitorOperations