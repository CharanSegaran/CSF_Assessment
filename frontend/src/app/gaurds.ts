import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { PictureComponent } from "./views/picture.component";

export const leavePictureView: CanDeactivateFn<PictureComponent> =
  (comp: PictureComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
      : boolean | UrlTree | Promise<boolean | UrlTree> | Observable<boolean | UrlTree>  => {

    const router = inject(Router)


    if (comp.backClicked==="back"){
      return confirm('Are you sure you want to discard image?')}
 
    return router.navigate(["/"])
  }