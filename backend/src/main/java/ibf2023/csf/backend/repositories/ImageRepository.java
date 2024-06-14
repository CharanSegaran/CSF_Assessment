package ibf2023.csf.backend.repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ImageRepository {

	// TODO Task 4.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception

	@Autowired
	AmazonS3 s3;


	public String save(MultipartFile file,String title, String comments) throws IOException{
		Map<String, String> userData = new HashMap<>();
        userData.put("uploadTime", new Date().toString());
        userData.put("originalFilename", file.getOriginalFilename());
		userData.put("comments", comments);
		userData.put("title",title);


		// Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);
        String key = UUID.randomUUID().toString()
            .substring(0, 8);
        System.out.println("CSF2024/%s".formatted(key));
        System.out.println(s3.doesBucketExistV2("charan-csf-2023"));
        System.out.println(file.getOriginalFilename());
        StringTokenizer tk = new StringTokenizer(file.getOriginalFilename(), ".");
        int count = 0;
        String filenameExt = "";
        while(tk.hasMoreTokens()){
            if(count == 1){
                    filenameExt = tk.nextToken();
                break;
            }else{
                filenameExt = tk.nextToken();
            }
            count++;
        }
        System.out.println("CSF2024/%s.%s".formatted(key, filenameExt));
        if(filenameExt.equals("blob"))
            filenameExt = filenameExt + ".png";
        PutObjectRequest putRequest = 
            new PutObjectRequest(
                "charan-csf-2023", 
                "CSF2024/%s.%s".formatted(key, filenameExt), 
                file.getInputStream(), 
                metadata);
        putRequest.withCannedAcl(
                CannedAccessControlList.PublicRead);
        s3.putObject(putRequest);
        return "CSF2024/%s.%s".formatted(key, filenameExt);
	}
}

