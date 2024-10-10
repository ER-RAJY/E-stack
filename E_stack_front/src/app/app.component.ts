import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend-angular';
  isUserLoggedIn: boolean = false;
  userRole: string | null = null;
  showNavbar: boolean = true;

  // Access the drawer using ViewChild
  @ViewChild('drawer') drawer!: MatSidenav;

  constructor(private router: Router) {}

  ngOnInit() {
    this.updateUserLoggedInStatus();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.updateUserLoggedInStatus();
      }
    });
  }  checkCurrentRoute() {
    this.router.events.subscribe(() => {
      // Hide the navbar if the current route is 'login'
      this.showNavbar = this.router.url !== '/login';
    });
  }

  private updateUserLoggedInStatus(): void {
    const token = localStorage.getItem('access_token');
    this.isUserLoggedIn = !!token;
    if (token) {
      this.userRole = this.getRoleFromToken(token);
    }
  }

  private getRoleFromToken(token: string | null): string | null {
    if (!token) {
      return null;
    }
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    return decodedToken.role;
  }

  // Toggle the sidenav (drawer)
  toggleDrawer(): void {
    if (this.drawer) {
      this.drawer.toggle();
    }
  }

  logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('character_id');
    this.router.navigate(['/login']);
  }
}
