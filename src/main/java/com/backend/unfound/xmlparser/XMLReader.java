package com.backend.unfound.xmlparser;

import com.backend.unfound.report.Report;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumit on 20/12/17.
 */
public class XMLReader {

    List<Report> reports;

    public XMLReader() {
    }

    // uses timestamp to locate file in upload directory, unmarshals using JAXB
    public List<Report> read(String timeStampId){
        try {

            File file = new File("upload-dir/"+timeStampId+".xml"); //TODO: fileNotFoundException
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            JAXBElement<VehiclesType> unmarshalledObject =
                    (JAXBElement<VehiclesType>)jaxbUnmarshaller.unmarshal(file);

            VehiclesType myVehicles = unmarshalledObject.getValue();
            List<VehicleType> vehicleTypes = myVehicles.getVehicle();
            reports = new ArrayList<>();


            StringBuilder sb = new StringBuilder(timeStampId.toString());
            String vhTimestamp = sb.insert(6,"::").insert(4,":").insert(2,":").toString();

            for (VehicleType vehicle : vehicleTypes){
                Report report = new Report(vhTimestamp);
                report.setId(vehicle.getId());
                report.setUrlTimestamp(timeStampId);
                report.setFrame(vehicle.getFrame().getMaterial());
                report.setPower(vehicle.getPowertrain().getHuman());
                ArrayList<ArrayList<String>> wheels = new ArrayList<ArrayList<String>>();
                for(WheelType wheelType : vehicle.getWheels().getWheel()){
                    ArrayList<String> wheel = new ArrayList<>();
                    wheel.add(0,wheelType.getMaterial());
                    wheel.add(1,wheelType.getPosition());
                    wheels.add(wheel);
                }

                report.setWheels(wheels);

                //try catch to determine powertrain
                try {
                    System.out.println(vehicle.getPowertrain().getHuman().toString());
                    report.setPower("Human");
                }
                catch (NullPointerException e){
                        try {
                            System.out.println(vehicle.getPowertrain().getBernoulli().toString());
                            report.setPower("Bernoulli");
                        }
                        catch (NullPointerException ee){
                                report.setPower("Internal Combustion");
                        }
                }

                if (report.getPower() == "Human"){
                    report.setName("Hang Glider");
                }
                else if (vehicle.getWheels().getWheel().size() == 4){
                    report.setName("Car");
                }
                else if (vehicle.getWheels().getWheel().size() == 3){
                    report.setName("Big Wheel");
                }
                else if (vehicle.getPowertrain().toString() == "Human"){
                    report.setName("Bicycle");
                }
                else {
                    report.setName("Motorcycle");
                }
                reports.add(report);
            }


        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return reports;
    }

}
