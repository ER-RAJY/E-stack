import { Component } from '@angular/core';
import { StorageService } from "./auth-services/storage-service/storage.service";
import {NavigationEnd, Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'E_stack_front';
  isUserLoggedIn: boolean = false; // Declare as boolean with an initial value

  constructor(private router:Router) { } // Inject StorageService

  ngOnInit() {
    this.updateUserLoggedInStatus(); // Call the function on init
    this.router.events.subscribe(event=>{
      if(event instanceof  NavigationEnd){
        this.updateUserLoggedInStatus();
      }
    })
  }

  private updateUserLoggedInStatus(): void { // Fix the method signature
    // this.isUserLoggedIn = this.storageService.isUserLoggedIn();
    this.isUserLoggedIn = StorageService.isUserLoggedIn(); // Access static method from the class

  }

  logout(){
    StorageService.logout();
    this.router.navigateByUrl("/login")
  }
}
