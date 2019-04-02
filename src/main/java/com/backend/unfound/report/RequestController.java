package com.backend.unfound.report;

import com.backend.unfound.storage.StorageException;
import com.backend.unfound.xmlparser.XMLReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.backend.unfound.storage.StorageFileNotFoundException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

import org.joda.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RequestController {

    @Autowired
    ReportRepository reportsRepository;

    @Autowired
    public RequestController() {
    }

    // handles upload request
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        LocalTime localTime = new LocalTime();

        StringBuilder sb = new StringBuilder(localTime.toString());
        String timeStamp = sb.deleteCharAt(2).deleteCharAt(4).deleteCharAt(6).toString();

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Files.copy(file.getInputStream(), Paths.get("upload-dir").resolve(timeStamp+".xml"),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename + e.toString(), e);
        }

        XMLReader reader = new XMLReader();
        try {
            List<Report> reports = reader.read(timeStamp);
            reports.forEach(report -> reportsRepository.save(report));
        }catch (NullPointerException e){
            System.out.println("Caught : Cant read xml");
            return null;
        }
        return this.timestampData(timeStamp);
    }

    //handles get request for list of timestamps which correspond to each uploaded file
    @RequestMapping(method = RequestMethod.GET, value = "/{timestamp}")
    public String timestampData(@PathVariable String timestamp){

        XMLReader reader = new XMLReader();
        List<Report> reports = reader.read(timestamp);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Report>>() {}.getType();
        String json = gson.toJson(reports, type);
        return json;
    }

    //returns original file as list of string for each line
    @RequestMapping(method = RequestMethod.GET, value = "/files/{timestamp}")
    public String originalData(@PathVariable String timestamp){
        List<String> streamOfStrings = new ArrayList<String>();
        try {
            Path path = Paths.get("upload-dir/"+timestamp+".xml");
            Files.lines(path).forEach(streamOfStrings::add);
        }catch(IOException ioer){
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(streamOfStrings, type);
        return json;
    }

    // returns report for each uploaded file,
    @RequestMapping(method = RequestMethod.GET, value = "/timestamps")
    public String getTimestamps (){

        List<String> distinctTimestamps = new ArrayList<>();

        List<String> timestamps = new ArrayList<>();
        List<Report> reports = new ArrayList<Report>();
        reportsRepository.findAll().forEach(reports::add);

        for (Report report:reports){
            timestamps.add(report.getTimeStamp().toString());
        }

        for(int i=0;i<timestamps.size();i++){
            boolean isDistinct = false;
            for(int j=0;j<i;j++){
                if(timestamps.get(i) == timestamps.get(j)){
                    isDistinct = true;
                    break;
                }
            }
            if(!isDistinct){
                distinctTimestamps.add(timestamps.get(i));
            }
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(distinctTimestamps,type);
        return json;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
