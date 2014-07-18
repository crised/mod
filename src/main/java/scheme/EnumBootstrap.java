package scheme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ModException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static utils.Consts.*;

/**
 * Created by crised on 7/17/14.
 */
public class EnumBootstrap {

    static final Logger LOG = LoggerFactory.getLogger("DataBootstrap");

    //Data coming from Enums
    private final List<Gateway> gatewayList;
    private final List<Parameter> parameterList1;
    private final List<Parameter> parameterList2;
    private final List<Parameter> parameterListExtremeMonitor;
    private final List<Meter> meterList;

    //List of ModbusMessages
    private final List<ModbusMessage> modbusMessagesList;


    public EnumBootstrap() throws Exception {

        gatewayList = new ArrayList<>();
        parameterList1 = new ArrayList<>();
        parameterList2 = new ArrayList<>();
        parameterListExtremeMonitor = new ArrayList<>();
        meterList = new ArrayList<>();
        modbusMessagesList = new ArrayList<>();

        // Gateway List

        for (scheme.enums.Gateway gateway : scheme.enums.Gateway.values()) {
            gatewayList.add(new Gateway(gateway.name(), gateway.getHost(), gateway.getPort()));

        }

        // Parameter List

        for (scheme.enums.Parameter parameter : scheme.enums.Parameter.values()) {
            switch (parameter.getMeterModel()) {

                case ELNET:
                    parameterList1.add(new Parameter(parameter.name(), parameter.getStartReg(), parameter.getEndReg(), parameter.getFunctionCode(), parameter.getFrequency()));
                    parameterListExtremeMonitor.add(new Parameter(parameter.name(), parameter.getStartReg(), parameter.getEndReg(), parameter.getFunctionCode(), EVERY_1_MIN));
                    break;

                case SATEC:
                    parameterList2.add(new Parameter(parameter.name(), parameter.getStartReg(), parameter.getEndReg(), parameter.getFunctionCode(), parameter.getFrequency()));
                    break;

                default:
                    throw new ModException("Wrong data set");
            }
        }

        // Meter List

        for (scheme.enums.Meter meter : scheme.enums.Meter.values()) {

            switch (meter.getMeterModel()) {

                case ELNET:
                    meterList.add(new Meter(meter.name(), findGatewayByName(meter.getGateway().name()), meter.getModbusRtuAddress(), parameterList1));
                    break;
                case SATEC:
                    meterList.add(new Meter(meter.name(), findGatewayByName(meter.getGateway().name()), meter.getModbusRtuAddress(), parameterList2));
                    break;
                default:
                    throw new ModException("Wrong data set");
            }

        }

        //ModBusMessage List
        //ModbusMessage are grouped by MeterList
        for (Meter meter : meterList) {
            getGroupParameterList(meter);
        }




    }


    private void getGroupParameterList(Meter meter) throws Exception {

        List<Parameter> auxList = new ArrayList<>();

        Integer lastRegister = null;
        Integer lastFrequency = null;
        Byte lastFunctionType = null;

        List<Parameter> parameterList = meter.getParameters();

        for (ListIterator<Parameter> iter = parameterList.listIterator(); iter.hasNext(); ) {

            Parameter parameter = iter.next();

            //First Element
            if (null == lastRegister) auxList.add(parameter);
            else {
                if ((lastRegister + 1) == parameter.getStartRegister()
                        && lastFrequency == parameter.getFrequency()
                        && lastFunctionType == parameter.getFunctionType()) {
                    auxList.add(parameter);
                } else {
                    modbusMessagesList.add(new ModbusMessage(meter, new ArrayList<>(auxList)));
                    auxList.clear();
                    auxList.add(parameter);
                }
            }
            lastRegister = parameter.getEndRegister();
            lastFrequency = parameter.getFrequency();
            lastFunctionType = parameter.getFunctionType();

            if (!iter.hasNext()) modbusMessagesList.add(new ModbusMessage(meter, auxList));
        }


    }

    private Gateway findGatewayByName(String name) throws Exception {
        for (Gateway gateway : gatewayList) {
            if (gateway.getName() == name) return gateway;
        }
        throw new ModException("Can't find Gateway");
    }

    public List<ModbusMessage> getModbusMessagesList() {
        return modbusMessagesList;
    }
}
