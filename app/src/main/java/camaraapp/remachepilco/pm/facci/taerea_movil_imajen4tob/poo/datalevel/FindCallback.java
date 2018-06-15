package camaraapp.remachepilco.pm.facci.taerea_movil_imajen4tob.poo.datalevel;

import java.util.ArrayList;


public interface FindCallback<DataObject> {
    public void done(ArrayList<DataObject> objects, DataException e);
}
