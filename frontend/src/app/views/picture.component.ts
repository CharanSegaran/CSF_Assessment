import { Component, OnInit } from '@angular/core';
import { UploadService } from '../upload.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { dataToImage } from '../utils';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent implements OnInit{

  // TODO: Task 2
  // TODO: Task 3
  imageData = ""
  blob!:Blob
  uploadForm!:FormGroup
  backClicked!:string
  constructor(private uploadService:UploadService, private router:Router, private http:HttpClient){
    this.uploadForm = new FormGroup({
      title: new FormControl("", Validators.required),
      comments: new FormControl("")  
    })
  }


  ngOnInit(): void {
    if(!this.uploadService.imageData){
      this.router.navigate(['/'])
      return;
    }
    this.uploadForm = new FormGroup({
      title: new FormControl("", Validators.required),
      comments: new FormControl("")  
    })
    this.imageData = this.uploadService.imageData
    this.blob = dataToImage(this.imageData)
    // console.log(this.blob)
  }

  backButtonClicked(){
    this.backClicked="back"
  }

  save(){
    const formData = new FormData()
    formData.append("picture",this.blob)
    formData.append("title",this.uploadForm.get("title")?.value)
    formData.append("comments",this.uploadForm.get("comments")?.value)
    formData.append("now", Date())
    this.uploadService.upload(formData)
  }
  
}
