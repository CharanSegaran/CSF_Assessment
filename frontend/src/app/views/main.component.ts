import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { WebcamComponent, WebcamImage } from 'ngx-webcam'
import { Subject, Subscription } from 'rxjs';
import { UploadService } from '../upload.service';
@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit,OnDestroy,AfterViewInit{

  // TODO: Task 1
  @ViewChild(WebcamComponent)
  webcam!: WebcamComponent;
  width =  400;
  height = 400
  pics: string[] = []
  sub$!: Subscription
  trigger = new  Subject<void>;
  constructor(private router: Router, private uploadSvc: UploadService){
  }

  ngOnInit(): void {
      console.log("init ... " + this.webcam);
  }

  ngOnDestroy(): void {
      this.sub$.unsubscribe();
  }

  ngAfterViewInit(): void {
      this.webcam.trigger = this.trigger;
      this.sub$ = this.webcam.imageCapture.subscribe(
        this.snapshot.bind(this)
      )
  }

  snap(){
    this.trigger.next();
    this.router.navigate(["/picture"])
  }

  snapshot(webcamImg: WebcamImage){
    this.uploadSvc.imageData = webcamImg.imageAsDataUrl;
    this.pics.push(webcamImg.imageAsDataUrl);
  }

}
