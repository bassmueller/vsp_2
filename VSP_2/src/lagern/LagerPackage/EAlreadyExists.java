package lagern.LagerPackage;


/**
* lagern/LagerPackage/EAlreadyExists.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lager.idl
* Mittwoch, 3. April 2013 16:12 Uhr MESZ
*/

public final class EAlreadyExists extends org.omg.CORBA.UserException
{
  public String s = null;

  public EAlreadyExists ()
  {
    super(EAlreadyExistsHelper.id());
  } // ctor

  public EAlreadyExists (String _s)
  {
    super(EAlreadyExistsHelper.id());
    s = _s;
  } // ctor


  public EAlreadyExists (String $reason, String _s)
  {
    super(EAlreadyExistsHelper.id() + "  " + $reason);
    s = _s;
  } // ctor

} // class EAlreadyExists
