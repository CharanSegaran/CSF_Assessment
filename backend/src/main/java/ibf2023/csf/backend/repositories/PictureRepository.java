package ibf2023.csf.backend.repositories;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PictureRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// TODO Task 4.2
	// You may change the method signature by adding parameters and/or the  return type
	// You may throw any exception 
	//db.archives.insert
	public String save(LocalDateTime date, String title, String comments,Long size, String id) {
		// IMPORTANT: Write the native mongo query in the comments above this method
		try{
            Document doc = new Document();
            doc.put("date",date);
            doc.put("title", title);
            doc.put("coments", comments);
			doc.put("size",size);
            doc.put("urls", "https://charan-csf-2023.sgp1.digitaloceanspaces.com/"+id);
            mongoTemplate.insert(doc,"archives");
			String objectId = doc.get("_id").toString();
			return objectId;
        }catch(Exception e){
            e.printStackTrace();
			return "Cannot upload" + e.getMessage();
        }
    } 

}
