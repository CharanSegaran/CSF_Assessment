package ibf2023.csf.backend.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.DateOperators.Month;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.repositories.ImageRepository;
import ibf2023.csf.backend.repositories.PictureRepository;

@Service
public class PictureService {

	// TODO Task 5.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 

	@Autowired
	MongoTemplate template;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	PictureRepository pictureRepository;

	@Value("${monthly.size.threshold}")
	private long monthlySizeThreshold;

	public String save(MultipartFile file, String comments, String title, LocalDateTime date) throws IOException {
		String id = imageRepository.save(file, title, comments);
		String uploadStatus = pictureRepository.save(date, title, comments, file.getSize(), id);
		return uploadStatus;
	

	}

	public Boolean getSize(MultipartFile file){
		LocalDate todaydate = LocalDate.now();
		Query query = Query.query(Criteria.where("date").gt(todaydate.withDayOfMonth(1))
															.lt(todaydate.withDayOfMonth(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH))));
		List<Document> result = template.find(query, Document.class, "archives");
		long totalSize = 0;
		for(Document doc:result){
			totalSize+=Long.parseLong(doc.get("size").toString());
		}
		if(totalSize+file.getSize() > monthlySizeThreshold){
			return false;
		}else return true;

	}
}
