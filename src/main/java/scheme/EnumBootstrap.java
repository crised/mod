package scheme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AppException;

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
    private final List<GroupMessage> groupMessagesList;

    public EnumBootstrap() throws Exception {

        gatewayList = new ArrayList<>();
        parameterList1 = new ArrayList<>();
        parameterList2 = new ArrayList<>();
        parameterListExtremeMonitor = new ArrayList<>();
        meterList = new ArrayList<>();
        groupMessagesList = new ArrayList<>();

        // Gateway List

        for (scheme.enums.Gateway gateway : scheme.enums.Gateway.values()) {
            gatewayList.add(new Gateway(gateway.name(), gateway.getHost(), gateway.getPort()));

        }

        // Parameter List

        for (scheme.enums.Parameter parameter : scheme.enums.Parameter.values()) {

            if (parameter.getStartReg() >= MODBUS_MAX_ADDRESS) throw new AppException();
            switch (parameter.getMeterModel()) {

                case ELNET:
                    parameterList1.add(new Parameter(parameter.name(), parameter.getStartReg(), parameter.getEndReg(), parameter.getFunctionCode(), parameter.getFrequency()));
                    parameterListExtremeMonitor.add(new Parameter(parameter.name(), parameter.getStartReg(), parameter.getEndReg(), parameter.getFunctionCode(), EVERY_1_MIN));
                    break;

                case SATEC:
                    parameterList2.add(new Parameter(parameter.name(), parameter.getStartReg(), parameter.getEndReg(), parameter.getFunctionCode(), parameter.getFrequency()));
                    break;

                default:
                    throw new AppException("Wrong data set");
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
                    throw new AppException("Wrong data set");
            }

        }

        //ModBusMessage List
        //ModbusMessage are grouped by MeterList
        for (Meter meter : meterList) {
            getGroupParameterList(meter);
        }

        //fill in GroupMessageMap
        for (GroupMessage groupMessage : groupMessagesList) {
            groupMessageMap.put(groupMessage.hashCode(), groupMessage);
        }
    }


    private void getGroupParameterList(Meter meter) throws Exception {

        List<Parameter> auxList = new ArrayList<>();

        Integer lastRegister = null;
        Integer lastFrequency = null;
        Byte lastFunctionType = null;
        Integer lastQuantityOfRegister = null;

        List<Parameter> parameterList = meter.getParameters();

        for (ListIterator<Parameter> iter = parameterList.listIterator(); iter.hasNext(); ) {

            Parameter parameter = iter.next();

            //First Element
            if (null == lastRegister) auxList.add(parameter);
            else {
                if ((lastRegister + 1) == parameter.getStartRegister()
                        && lastFrequency == parameter.getFrequency()
                        && lastFunctionType == parameter.getFunctionType()
                        && lastQuantityOfRegister == parameter.getSize()) {
                    auxList.add(parameter);
                } else {
                    groupMessagesList.add(new GroupMessage(meter, new ArrayList<>(auxList)));
                    auxList.clear();
                    auxList.add(parameter);
                }
            }
            lastRegister = parameter.getEndRegister();
            lastFrequency = parameter.getFrequency();
            lastFunctionType = parameter.getFunctionType();
            lastQuantityOfRegister = parameter.getSize();


            if (auxList.size() > 120) throw new AppException("Maximum Size of Modbus Frame Exceed");

            if (!iter.hasNext()) groupMessagesList.add(new GroupMessage(meter, auxList));
        }


    }

    private Gateway findGatewayByName(String name) throws Exception {
        for (Gateway gateway : gatewayList) {
            if (gateway.getName() == name) return gateway;
        }
        throw new AppException("Can't find Gateway");
    }


    public List<Gateway> getGatewayList() {
        return gatewayList;
    }

    public List<Meter> getMeterList() {
        return meterList;
    }


    public List<GroupMessage> getGroupMessagesList() {
        return groupMessagesList;
    }
}
