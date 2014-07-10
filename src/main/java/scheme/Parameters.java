package scheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
//Here goes all the Lists of Paramters;
public class Parameters {

    public final List<Parameter> satecList1;
    public final List<Parameter> satecList2;
    public final List<Parameter> elnetList1;


/*
    public Parameters() {

        satecList1 = Arrays.asList(new Parameter("Volt"));

    }*/

    public Parameters(List<Parameter> satecList1, List<Parameter> satecList2, List<Parameter> elnetList1) {
        this.satecList1 = satecList1;
        this.satecList2 = satecList2;
        this.elnetList1 = elnetList1;
    }
}
