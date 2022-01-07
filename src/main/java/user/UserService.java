package user;

import com.example.javafxproj.User;
import com.example.javafxproj.userGrpc;
import io.grpc.stub.StreamObserver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


//class Body{
// private String name;
// private String type;
// private int inittime;
// public Body(String name , String type,int inittime){
//     this.name = name;
//     this.type= type;
//     this.inittime=inittime;
// }
//}

public class UserService extends userGrpc.userImplBase {
    @Override
    public void addInstrument(User.instrument request, StreamObserver<User.APIResponse> responseObserver) {
        //super.addInstrument(request, responseObserver);
        String name = request.getName();
        String type = request.getType();
        int inittime = request.getInittime();
        User.APIResponse.Builder response =  User.APIResponse.newBuilder();
//        Body inputBody = new Body(name,type,inittime);
        JSONParser jsonParser = new JSONParser();
        // System.out.println(name+type+inittime);
        String currentPath = null;
        try {
            currentPath = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(currentPath);
        try {
            Object obj = jsonParser.parse(new FileReader("./src/main/java/user/data.json"));
            JSONArray jsonArray = (JSONArray)obj;
            JSONObject instrument = new JSONObject();
            instrument.put("name", name);
            instrument.put("inittime",inittime);
            instrument.put("type",type);
            jsonArray.add(instrument);
            System.out.println(jsonArray);
            FileWriter file = new FileWriter("./src/main/java/user/data.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
            response.setResponseCode(200).setResponseMessage("Successful");

        } catch (FileNotFoundException  e) {
            response.setResponseCode(404).setResponseMessage("failed");
            e.printStackTrace();
        } catch (IOException e) {
            response.setResponseCode(404).setResponseMessage("failed");
            e.printStackTrace();
        } catch (ParseException e) {
            response.setResponseCode(404).setResponseMessage("failed");
            e.printStackTrace();
        } finally {
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void burnData(User.inputParameters request, StreamObserver<User.APIResponse> responseObserver){
        int sn = request.getSitesNo();
        String fp = request.getFilePath();
        User.APIResponse.Builder response =  User.APIResponse.newBuilder();
        System.out.println(sn);
        System.out.println(fp);
        response.setResponseCode(200).setResponseMessage("Successful");
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void validateData(User.inputParameters request, StreamObserver<User.APIResponse> responseObserver) {
        int sn = request.getSitesNo();
        String fp = request.getFilePath();
        User.APIResponse.Builder response =  User.APIResponse.newBuilder();
        System.out.println(sn);
        System.out.println(fp);
        response.setResponseCode(200).setResponseMessage("Successful");
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }
}
