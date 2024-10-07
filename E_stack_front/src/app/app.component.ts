import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'] // Fix for styleUrls
})
export class AppComponent implements OnInit {
  title = 'frontend-angular';
  isUserLoggedIn: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.updateUserLoggedInStatus();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.updateUserLoggedInStatus();
      }
    });
  }

  private updateUserLoggedInStatus(): void {
    this.isUserLoggedIn = !!localStorage.getItem('access_token');
  }

  logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('character_id');
    this.router.navigate(['/login']);
  }
}
