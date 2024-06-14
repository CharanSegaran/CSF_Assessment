package ibf2023.csf.backend.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.joda.time.Instant;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.services.PictureService;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path="/api")
public class UploadController {

	@Autowired
	PictureService pictureService;


	// TODO Task 5.2
	// You may change the method signature by adding additional parameters and annotations.
	// You cannot remove any any existing annotations and parameters from postUpload()
	@PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(@RequestPart MultipartFile picture,
											 @RequestPart String title,
											 @RequestPart String comments,
											 @RequestPart String now) throws IOException {
												
		now = now.substring(0,33);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d u H:m:s 'GMT'Z", Locale.ENGLISH);
		LocalDateTime dateTime = LocalDateTime.parse(now, formatter);
		
		pictureService.save(picture, comments, title, dateTime);

		if(pictureService.getSize(picture).equals(true)){
			String uploadStatus = pictureService.save(picture, comments, title, dateTime);
			if(!uploadStatus.startsWith("Cannot upload")){
				JsonObjectBuilder builder = Json.createObjectBuilder();
				builder.add("id", uploadStatus);
				return ResponseEntity.ok().body(builder.build().toString());
			}else{
				JsonObjectBuilder builder = Json.createObjectBuilder();
				builder.add("message", uploadStatus);
				return ResponseEntity.status(500).body(builder.build().toString());
			}
		}else{
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("message", "The upload has exceeded your monthly upload quota of ${monthly.size.threshold} MB");
			return ResponseEntity.status(413).body(builder.build().toString());
		}
		
	}
}
