package scheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by crised on 7/9/14.
 */
//Here goes all the Lists of Paramters;
public final class Parameters {

    private final List<Parameter> satecList1 = new ArrayList<>();
    private final List<Parameter> satecList2 = new ArrayList<>();
    private final List<Parameter> elnetList1 = new ArrayList<>();

    public Parameters() {
        initSatecList1();
        initSatecList2();
        initElnetList1();
    }

    private void initSatecList1() {

        satecList1.add(new Parameter("volt_1_satec", 0x01, 0x02));

    }

    private void initSatecList2() {
        satecList2.add(new Parameter("volt_1_satec2", 0x01, 0x02));
    }

    private void initElnetList1() {
        elnetList1.add(new Parameter("volt_1_elnet1", 0x01, 0x02));
    }

    public List<Parameter> getSatecList1() {
        return satecList1;
    }

    public List<Parameter> getSatecList2() {
        return satecList2;
    }

    public List<Parameter> getElnetList1() {
        return elnetList1;
    }
}
