import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  imageData = ""
  // TODO: Task 3.
  // You may add add parameters to the method

  constructor(private http: HttpClient, private router:Router) { }
  upload(formData:FormData) {
    this.http.post("/api/image/upload", formData).subscribe({
      next:(value:any)=>{
        console.log(value)
        this.router.navigate(["/"])
      },
      error:(err:any)=>{
        console.log(err)
      }
    })
  }
}
