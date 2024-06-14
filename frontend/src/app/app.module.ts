import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { MainComponent } from './views/main.component';
import { PictureComponent } from './views/picture.component';
import { RouterModule, Routes } from '@angular/router';
import { WebcamModule } from 'ngx-webcam';
import { UploadService } from './upload.service';
import { HttpClientModule } from '@angular/common/http';
import { leavePictureView } from './gaurds';
const routes:Routes = [
  {path:"", component:MainComponent},
  {path:"picture", component:PictureComponent},
  {path:"**",redirectTo:"/",pathMatch:"full"}
]

@NgModule({
  declarations: [
    AppComponent, MainComponent, PictureComponent
  ],
  imports: [
    BrowserModule, RouterModule.forRoot(routes),FormsModule,ReactiveFormsModule,WebcamModule,HttpClientModule
  ],
  providers: [UploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
